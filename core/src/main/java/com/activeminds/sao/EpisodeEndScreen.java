package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class EpisodeEndScreen implements Screen {
    Main game;
    ButtonLayout joypad;
    public EpisodeEndScreen(Main game)
    {
        this.game = game;

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

        game.load_scr("WALL.SCR");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        String sacred_item[]={
            game.loc.get("sacred1"),
            game.loc.get("sacred2"),
            game.loc.get("sacred3"),
            game.loc.get("sacred4"),
            game.loc.get("sacred5")};

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(game.scr, game.GAME_SCREEN_START_X, 0);
        game.Copytext(game.batch,50,20,game.epi_name);
        game.COPY_BUFFER_1(game.batch,140,40,40,39,game.chr.epi_end);
        game.Copytext(game.batch,10,90,game.loc.get("sacredGot1"));
        game.Copytext(game.batch,10,115,game.loc.get("sacredGot2"));
        game.Copytext(game.batch,10,140,sacred_item[game.epi_actual-1]);

        game.Copytext(game.batch,35,180,game.loc.get("pressSpaceContinue"));

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept"))
        {
            game.completed[game.epi_actual-1]=1;

            if(game.completed[0]!=0 && game.completed[1]!=0 && game.completed[2]!=0 && game.completed[3]!=0 &&
                game.completed[4]!=0)
            {
                game.setScreen(new SacredArmorScreen(game));
                dispose();
            }
            else
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
