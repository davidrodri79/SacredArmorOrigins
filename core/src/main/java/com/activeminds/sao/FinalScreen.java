package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class FinalScreen implements Screen {

    Main game;
    ButtonLayout joypad;
    int step = 0, current_phrase = 0;
    long lastPhraseChangeTime;
    public FinalScreen(Main game)
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

        String frases[]={
            game.loc.get("finalCredits1"),
            game.loc.get("finalCredits2"),
            game.loc.get("finalCredits3"),
            game.loc.get("finalCredits4"),
            game.loc.get("finalCredits5"),
            game.loc.get("finalCredits6"),
            game.loc.get("finalCredits7"),
            game.loc.get("finalCredits8"),
            game.loc.get("finalCredits9"),
            game.loc.get("finalCredits10"),
            game.loc.get("finalCredits11"),
            game.loc.get("finalCredits12"),
            ""};

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();

        if(step == 0) {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.COPY_BUFFER_1(game.batch, 140, 40, 40, 39, game.chr.epi_end);
            game.Copytext(game.batch, 50, 20, game.epi_name);
            game.Copytext(game.batch, 10, 90, game.loc.get("endingA1"));
            game.Copytext(game.batch, 10, 110, game.loc.get("endingA2"));
            game.Copytext(game.batch, 10, 130, game.loc.get("endingA3"));
            game.Copytext(game.batch, 10, 150, game.loc.get("endingA4"));
            game.Copytext(game.batch, 10, 170, game.loc.get("endingA5"));
        }
        else if (step == 1)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,10,30, game.loc.get("endingB1"));
            game.Copytext(game.batch,10,50,game.loc.get("endingB2"));
            game.Copytext(game.batch,10,70,game.loc.get("endingB3"));
            game.Copytext(game.batch,10,90,game.loc.get("endingB4"));
            game.Copytext(game.batch,10,110,game.loc.get("endingB5"));
            game.Copytext(game.batch,10,130,game.loc.get("endingB6"));
            game.Copytext(game.batch,10,150,game.loc.get("endingB7"));
            game.Copytext(game.batch,10,170,game.loc.get("endingB8"));
        }
        else if (step == 2)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,15,185,frases[current_phrase]);
            if(current_phrase==11) game.Copytext(game.batch,130,90,game.loc.get("theEnd"));

            if(System.currentTimeMillis() > lastPhraseChangeTime + 2000)
            {
                lastPhraseChangeTime = System.currentTimeMillis();
                if(current_phrase < 11) current_phrase++;
            }
        }

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept"))
        {
            {
                step++;
                if(step == 1)
                {
                    game.load_scr("MAP.SCR");
                }
                if(step == 2)
                {
                    game.load_scr_color_swap("FINAL.SCR", (char)69, game.p_col);
                    lastPhraseChangeTime = System.currentTimeMillis();
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
