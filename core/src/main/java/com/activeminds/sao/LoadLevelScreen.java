package com.activeminds.sao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;
import java.io.InputStream;

public class LoadLevelScreen implements Screen {
    Main game;
    long startTime;
    boolean loaded;
    public LoadLevelScreen(Main game)
    {
        this.game = game;

        if(game.epi_actual == 0) game.epi_file = "INTRO.EPI";
        if(game.epi_actual == 1) game.epi_file = "CLASSIC.EPI";
        if(game.epi_actual == 2) game.epi_file = "MEDIEV.EPI";
        if(game.epi_actual == 3) game.epi_file = "FUTURE.EPI";
        if(game.epi_actual == 4) game.epi_file = "ICE.EPI";
        if(game.epi_actual == 5) game.epi_file = "FIRE.EPI";
        if(game.epi_actual == 6) game.epi_file = "HELL.EPI";

        try {
            game.carga_nivel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Enemigos inicialmente muertos
        for(int i=0; i<game.fase.e_n; ++i)
            if(game.fase.enemies[i].e_l==0) game.ene_datos[i].est=7;

        game.load_player();

        FileHandle f = Gdx.files.internal("WARRIOR/GAMMA.WAR");
        if(f.exists() && !f.isDirectory()) {
            InputStream inputStream = f.read();
            game.trp = game.load_warrior(inputStream);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //cambia_color(32,p_col);

        //Puerta a Hell abierta si 5 reinos completados
        if(game.epi_actual==0){
            if(game.completed[0]==1 && game.completed[1]==1 && game.completed[2]==1 && game.completed[3]==1 &&
                game.completed[4]==1)
            {game.fase.map[4][3][3][0]=8; game.fase.map[4][3][4][0]=9;};

            //Caso que se hayan completado los 5 reinos iniciales

            if(game.completed[0]==1){ game.fase.map[4][4][3][7]=47; game.fase.map[4][4][4][7]=48;};
            if(game.completed[1]==1){ game.fase.map[4][4][0][4]=49; game.fase.map[4][4][0][5]=50;};
            if(game.completed[2]==1){ game.fase.map[4][4][7][4]=49; game.fase.map[4][4][7][5]=50;};
            if(game.completed[3]==1){ game.fase.map[4][3][0][3]=49; game.fase.map[4][3][0][4]=50;};
            if(game.completed[4]==1){ game.fase.map[4][3][7][3]=49; game.fase.map[4][3][7][4]=50;};
        };

        game.load_scr_color_swap("SOLDIER.SCR", (char)32, game.p_col);
        startTime = System.currentTimeMillis();
        loaded = false;

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
        game.Copytext(game.batch,50,20,game.epi_name);
        game.Copytext(game.batch,90,80,game.loc.get("entering"));
        game.Copytext(game.batch,20,100,game.fase.map_name);
        game.Copytext(game.batch,25,150,game.loc.get("getReady"));
        game.Copytext(game.batch,235,150,game.p_name);
        game.batch.end();

        // Update =====
        if(!loaded) {
            game.load_charset();
            game.p_l = 100;
            game.p_e = 0;
            game.x_map = game.fase.start_xy[0];
            game.y_map = game.fase.start_xy[1];
            game.px = game.fase.start_xy[2];
            game.py = game.fase.start_xy[3];
            game.llave[0] = 0;
            game.llave[1] = 0;
            game.llave[2] = 0;
            game.municion[0] = 0; // PESAO
            game.municion[1] = 0;
            game.municion[2] = 0;
            /*game.armas[0] = 1;
            game.armas[1] = 1;
            game.armas[2] = 1;
            game.armas[3] = 1;
            game.armas[4] = 1;
            game.armas[5] = 1;*/
            game.n_secrets = 0;
            game.t_secrets = game.total_secrets();
            //game.K=0;
            //game.m3=0;

            game.t_secrets=game.total_secrets();


            // Mapa inicialmente desconocido
            for (int i = 0; i < 10; ++i)
                for (int j = 0; j < 10; ++j)
                    game.visto[i][j] = 0;

            loaded = true;
        }

        if(System.currentTimeMillis() > startTime + 3000) {
            //game.tiem_i=time(NULL);
            game.setScreen(new GameScreen(game));
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
