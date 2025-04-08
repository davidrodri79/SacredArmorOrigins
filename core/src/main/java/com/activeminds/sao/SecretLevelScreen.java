package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class SecretLevelScreen implements Screen {
    Main game;

    ButtonLayout joypad;
    public SecretLevelScreen(Main game)
    {
        this.game = game;
        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

        game.load_scr("MAP.SCR");
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
        game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);

        game.Copytext(game.batch,50,20,game.epi_name);
        game.Copytext(game.batch,40,50,game.loc.get("secretLevel1"));
        game.Copytext(game.batch,10,80,game.loc.get("secretLevel2"));
        game.Copytext(game.batch,10,100,game.loc.get("secretLevel3"));
        game.Copytext(game.batch,10,120,game.loc.get("secretLevel4"));
        game.Copytext(game.batch,10,140,game.loc.get("secretLevel5"));

        game.Copytext(game.batch,35,180,game.loc.get("pressSpaceContinue"));

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept")) {

            game.lev = 7;
            game.setScreen(new SaveGameScreen(game));
            dispose();
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
