package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelResultsScreen implements Screen {

    Main game;
    ButtonLayout joypad;

    LevelResultsScreen(Main game)
    {
        this.game = game;

        game.load_scr("SOLDIER.SCR");

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
        game.Copytext(game.batch,50,20,game.epi_name);
        game.Copytext(game.batch,20,50,game.fase.map_name);
        game.Copytext(game.batch,20,70,"¡Zona completada!");
        //game.Copytext(game.batch,20,90,"��������������������������");
        int j=0;
        for(int i=0; i<game.fase.e_n; ++i)
            if(game.ene_datos[i].est>=5) j++;

        String frase;
        frase="Enemigos muertos     "+j+"/"+game.fase.e_n;
        game.Copytext(game.batch,20,100,frase);
        frase="Areas secretas       "+game.n_secrets+"/"+game.t_secrets;
        game.Copytext(game.batch,20,120,frase);
        frase="Tiempo             ";//+horas+":"+mins+":"+secs;
        game.Copytext(game.batch,20,140,frase);
        //game.Copytext(game.batch,20,155,"��������������������������");

        game.Copytext(game.batch,35,180,"Pulsa SPACE para seguir");
        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept"))
        {
            game.lev++;
            game.setScreen(new LoadLevelScreen(game));
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
