package com.activeminds.sao;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StringBuilder;

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
        game.Copytext(game.batch, 80,65,"NUEVA BATALLA");
        game.Copytext(game.batch,80,90,"CARGAR PARTIDA");
        game.Copytext(game.batch,80,115,"INSTRUCCIONES");
        game.Copytext(game.batch,80,140,"OPCIONES");
        game.Copytext(game.batch,80,165,"SALIR");
        StringBuilder string = new StringBuilder();
        string.append((char)252);
        string.append((char)253);
        game.Copytext(game.batch,30,185,"Active Minds 1998,2025"+string.toString());
        game.COPY_BUFFER_1(game.batch,45,60+(25*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,253,60+(25*op),22,18,game.helmet[1]);
        //show_all_font();
        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Up")) {op--; };
        if(joypad.consumePush("Down")) {op++;};
        if(joypad.consumePush("Accept"))
        {
            if(op == 0)
            {
                game.lev=0; game.p_l=100; game.epi_actual=0;
                for(int j=0; j<6; ++j) game.armas[j]=0;
                game.setScreen(new LoadLevelScreen(game));
                dispose();
            }
            if(op == 1)
            {
                game.setScreen(new LoadGameScreen(game));
                dispose();
            }
        }

        if(op<0) op=4;
        if(op>4) op=0;
    }

    void show_all_font()
    {   // 0 32 64 96 128 160 192 224
        ScreenUtils.clear(0, 0, 0, 1f);
        for(int i = 0; i < 256; i++)
        {
            int x = i % 32;
            int y = (int)(i /32);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((char)i);
            game.Copytext(game.batch, 16 * x - Main.GAME_SCREEN_START_X, 20*y, stringBuilder.toString());
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
