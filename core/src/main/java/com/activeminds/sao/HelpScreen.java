package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class HelpScreen implements Screen {
    Main game;
    ButtonLayout joypad;
    int step = 0;
    public HelpScreen(Main game)
    {
        this.game = game;

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

        game.load_scr("WALL.SCR");

        game.load_charset();
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

        if(step == 0) {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,110,10,"ESCENARIOS");
            game.COPY_BUFFER_1(game.batch,10,25,40,39,game.chr.teletrans);
            game.COPY_BUFFER_1(game.batch,10,70,40,39,game.chr.boton[0]);
            game.COPY_BUFFER_1(game.batch,10,120,40,23,game.chr.bad_tile[0]);
            game.COPY_BUFFER_1(game.batch,10,150,40,23,game.chr.mor_tile[0]);
            game.Copytext(game.batch,60,35,"Teletransporte");
            game.Copytext(game.batch,60,50,"Cambio de lugar");
            game.Copytext(game.batch,60,80,"Interruptor");
            game.Copytext(game.batch,60,95,"Activa baldosa o puerta");
            game.Copytext(game.batch,60,130,"Baldosa dañina");
            game.Copytext(game.batch,60,160,"Baldosa mortal");

            game.Copytext(game.batch,40,185,"Pulsa SPACE para seguir");
        }
        else if (step == 1)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,120,10,"OBJETOS");
            game.COPY_BUFFER_1(game.batch,10,25,24,26,game.keys[0]);
            game.COPY_BUFFER_1(game.batch,35,25,24,26,game.keys[1]);
            game.COPY_BUFFER_1(game.batch,60,25,24,26,game.keys[2]);

            game.COPY_BUFFER_1(game.batch,10,60,24,26,game.ammo[0]);
            game.COPY_BUFFER_1(game.batch,35,60,24,26,game.ammo[1]);
            game.COPY_BUFFER_1(game.batch,60,60,24,26,game.ammo[2]);

            game.COPY_BUFFER_1(game.batch,10,90,24,26,game.addings[0]);
            game.COPY_BUFFER_1(game.batch,10,120,24,26,game.addings[3]);
            game.COPY_BUFFER_1(game.batch,10,150,24,26,game.addings[6]);
            game.COPY_BUFFER_1(game.batch,150,90,24,26,game.addings[11]);
            game.COPY_BUFFER_1(game.batch,150,120,24,26,game.addings[10]);
            game.COPY_BUFFER_1(game.batch,150,150,24,26,game.addings[1]);

            game.Copytext(game.batch,95,35,"Llaves");
            game.Copytext(game.batch,95,70,"Municion");
            game.Copytext(game.batch,40,100,"Botiquin");
            game.Copytext(game.batch,40,130,"Invisible");
            game.Copytext(game.batch,40,160,"Fuerza");

            game.Copytext(game.batch,180,100,"Salud 200");
            game.Copytext(game.batch,180,130,"Mapa");
            game.Copytext(game.batch,180,160,"Invencible");

            game.Copytext(game.batch,40,185,"Pulsa SPACE para seguir");
        }
        else if (step == 2)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);

            game.Copytext(game.batch,30,60,"Active Minds 1998,2025");

            game.Copytext(game.batch,20,85,"          STAFF");
            game.Copytext(game.batch,20,110,"Idea original     NHSP");
            game.Copytext(game.batch,20,125,"Programacion      NHSP");
            game.Copytext(game.batch,20,140,"Graficos        NHSP,MTX");
            game.Copytext(game.batch,20,155,"Sonido            NHSP");
            game.Copytext(game.batch,20,170,"Mapas        NHSP,MTX,LUKE");
        }

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept"))
        {
            {
                step++;
                if(step == 2)
                {
                    game.load_scr("TITLE.SCR");
                }
                if(step == 3)
                {
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                }
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
