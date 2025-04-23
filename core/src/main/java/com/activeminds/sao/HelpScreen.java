package com.activeminds.sao;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StringBuilder;

public class HelpScreen implements Screen {
    Main game;
    ButtonLayout joypad;
    int step;
    public HelpScreen(Main game)
    {
        this.game = game;

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

        game.load_scr("WALL.SCR");

        game.load_charset();

        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
        {
            step = 0;
        }
        else
        {
            step = 1;
        }
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
            game.Copytext(game.batch,110,10,game.loc.get("controls"));
            StringBuilder string = new StringBuilder();
            string.append((char)30);
            string.append((char)24);
            string.append((char)28);
            game.Copytext(game.batch,30,20,string.toString());
            string = new StringBuilder();
            string.append((char)27);
            string.append((char)22);
            string.append((char)26);
            game.Copytext(game.batch,30,30,string.toString());
            game.Copytext(game.batch,30,30,game.loc.get("controls1"));
            string = new StringBuilder();
            string.append((char)31);
            string.append((char)25);
            string.append((char)29);
            game.Copytext(game.batch,30,40,string.toString());
            game.Copytext(game.batch,10,55,game.loc.get("controls2"));
            game.Copytext(game.batch,10,70,game.loc.get("controls3"));
            game.Copytext(game.batch,10,85,game.loc.get("controls4"));
            game.Copytext(game.batch,10,100,game.loc.get("controls5"));
            game.Copytext(game.batch,10,118,game.loc.get("controls6"));
            game.Copytext(game.batch,10,133,game.loc.get("controls7"));
            game.Copytext(game.batch,10,148,game.loc.get("controls8"));
            game.Copytext(game.batch,10,163,game.loc.get("controls9"));
            game.Copytext(game.batch,40,185,game.loc.get("pressSpaceContinue"));
        } else if(step == 1) {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,110,10,game.loc.get("scenery"));
            game.COPY_BUFFER_1(game.batch,10,25,40,39,game.chr.teletrans);
            game.COPY_BUFFER_1(game.batch,10,70,40,39,game.chr.boton[0]);
            game.COPY_BUFFER_1(game.batch,10,120,40,23,game.chr.bad_tile[0]);
            game.COPY_BUFFER_1(game.batch,10,150,40,23,game.chr.mor_tile[0]);
            game.Copytext(game.batch,60,35,game.loc.get("teleport"));
            game.Copytext(game.batch,60,50,game.loc.get("changeLocation"));
            game.Copytext(game.batch,60,80,game.loc.get("switch"));
            game.Copytext(game.batch,60,95,game.loc.get("activateTileDoor"));
            game.Copytext(game.batch,60,130,game.loc.get("harmingTile"));
            game.Copytext(game.batch,60,160,game.loc.get("lethalTile"));

            game.Copytext(game.batch,40,185,game.loc.get("pressSpaceContinue"));
        }
        else if (step == 2)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,120,10,game.loc.get("objects"));
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

            game.Copytext(game.batch,95,35, game.loc.get("keys"));
            game.Copytext(game.batch,95,70,game.loc.get("ammo"));
            game.Copytext(game.batch,40,100,game.loc.get("medikit"));
            game.Copytext(game.batch,40,130,game.loc.get("invisible"));
            game.Copytext(game.batch,40,160,game.loc.get("strength"));

            game.Copytext(game.batch,180,100,game.loc.get("health200"));
            game.Copytext(game.batch,180,130,game.loc.get("map"));
            game.Copytext(game.batch,180,160,game.loc.get("invincible"));

            game.Copytext(game.batch,40,185,game.loc.get("pressSpaceContinue"));
        }
        else if (step == 3)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);

            game.Copytext(game.batch,30,60,game.loc.get("activeMindsTm"));

            game.Copytext(game.batch,20,85,game.loc.get("credits1"));
            game.Copytext(game.batch,20,110,game.loc.get("credits2"));
            game.Copytext(game.batch,20,125,game.loc.get("credits3"));
            game.Copytext(game.batch,20,140,game.loc.get("credits4"));
            game.Copytext(game.batch,20,155,game.loc.get("credits5"));
            game.Copytext(game.batch,20,170,game.loc.get("credits6"));
        }

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept"))
        {
            {
                step++;
                if(step == 3)
                {
                    game.load_scr("TITLE.SCR");
                }
                if(step == 4)
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
