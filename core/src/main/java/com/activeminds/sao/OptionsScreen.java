package com.activeminds.sao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class OptionsScreen implements Screen {

    Main game;
    int op = 0;
    ButtonLayout joypad;
    public OptionsScreen(Main game)
    {

        this.game = game;

        game.load_scr("WALL.SCR");

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(game.scr, game.GAME_SCREEN_START_X, 0);
        game.Copytext(game.batch,120,20,game.loc.get("options"));
        game.Copytext(game.batch,80,80,game.loc.get("soldierColor"));
        game.Copytext(game.batch,80,100,game.loc.get("playerName"));
        game.Copytext(game.batch,80,120,game.loc.get("language"));
        game.Copytext(game.batch,80,140,game.loc.get("sound"));
        game.Copytext(game.batch,80,160,"GORE");
        game.Copytext(game.batch,170,120,game.loc.get("languageCurrent"));
        if(game.SND == 1) game.Copytext(game.batch,200,140,game.loc.get("yes"));
        else game.Copytext(game.batch,200,140,game.loc.get("no"));
        if(game.gore) game.Copytext(game.batch,200,160,game.loc.get("yes"));
        else game.Copytext(game.batch,200,160,game.loc.get("no"));
        game.COPY_BUFFER_1(game.batch,45,75+(20*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,253,75+(20*op),22,18,game.helmet[1]);
        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Up")) {op--; };
        if(joypad.consumePush("Down")) {op++;};
        if(joypad.consumePush("Accept"))
        {
            if(op==0) {
                game.setScreen(new SelectColorScreen(game));
                dispose();
            }
            if(op==1){
                Gdx.input.getTextInput(new Input.TextInputListener() {
                @Override
                public void input(String text) {
                    Gdx.app.log("Texto", text);
                    if(text.length() > 6) game.p_name = text.substring(0,6);
                    else game.p_name = text;
                }

                @Override
                public void canceled() {
                    Gdx.app.log("Texto", "Cancelado");
                }
            }, game.loc.get("enterYourName"), game.p_name, "");
            };
            if(op==2){game.LANG++; if (game.LANG>4) game.LANG=0; game.loc.loadLanguage(game.LANG);};
            if(op==3){if (game.SND==1) game.SND=0; else game.SND=1;};
            if(op==4){game.gore = !game.gore;};
        }
        if(joypad.consumePush("Back")) {
            game.save_options();
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        if(op<0) op=4;
        if(op>4) op=0;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
