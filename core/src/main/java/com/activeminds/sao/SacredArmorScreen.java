package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class SacredArmorScreen implements Screen {
    Main game;
    ButtonLayout joypad;
    public SacredArmorScreen(Main game)
    {
        this.game = game;

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

        game.load_scr("SACRED.SCR");
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
        game.Copytext(game.batch,20,40,game.loc.get("sacredArmor1"));
        game.Copytext(game.batch,20,60,game.loc.get("sacredArmor2"));
        game.Copytext(game.batch,20,80,game.loc.get("sacredArmor3"));
        game.Copytext(game.batch,20,100,game.loc.get("sacredArmor4"));
        game.Copytext(game.batch,20,120,game.loc.get("sacredArmor5"));
        game.Copytext(game.batch,20,140,game.loc.get("sacredArmor6"));
        game.Copytext(game.batch,20,160,game.loc.get("sacredArmor7"));

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept"))
        {
            {
                game.epi_actual = 0; game.lev = 0;
                game.setScreen(new SaveGameScreen(game));
                dispose();
            }
        }
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
