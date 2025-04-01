package com.activeminds.sao;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    Main game;
    int op = 0;
    ButtonLayout joypad;

    public MainMenuScreen(Main game)
    {
        this.game = game;

        game.load_scr("TITLE.SCR");

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("joypad.json");
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
        game.Copytext(game.batch, 80,65,"NUEVA BATALLA");
        game.Copytext(game.batch,80,90,"CARGAR PARTIDA");
        game.Copytext(game.batch,80,115,"INSTRUCCIONES");
        game.Copytext(game.batch,80,140,"OPCIONES");
        game.Copytext(game.batch,80,165,"SALIR");
        game.Copytext(game.batch,30,185,"Active Minds 1997,98");
        game.COPY_BUFFER_1(game.batch,45,60+(25*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,253,60+(25*op),22,18,game.helmet[1]);
        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Up")) {op--; };
        if(joypad.consumePush("Down")) {op++;};

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
