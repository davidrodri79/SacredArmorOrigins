package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class FreePlaySelectScreen implements Screen {

    Main game;
    int op = 0;
    ButtonLayout joypad;
    public FreePlaySelectScreen(Main game)
    {
        this.game = game;

        game.load_scr("MAP.SCR");

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

        game.epi_actual = 1; game.lev = 0;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        String[] dif_levels={ "normal", "hard", "extreme"};
        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.setColor(0.5f,0.5f,0.5f,1f);
        game.batch.draw(game.scr, game.GAME_SCREEN_START_X, 0);
        game.batch.setColor(1f,1f,1f,1f);
        game.Copytext(game.batch,40,30,game.loc.get("difficultyLevel"));
        game.Copytext(game.batch,40,50,game.loc.get(dif_levels[game.DIF]));
        game.Copytext(game.batch,40,75,game.loc.get("episode"));
        game.Copytext(game.batch,40,95,game.loc.get("episodeName"+game.epi_actual));
        game.Copytext(game.batch,40,120,game.loc.get("level"));
        game.Copytext(game.batch,40,145,"L"+(game.lev+1)+":"+game.loc.get("levelName"+game.epi_actual+"-"+game.lev));
        game.Copytext(game.batch,40,170,game.loc.get("start"));
        //game.Copytext(game.batch,80,20,game.loc.get("freePlay"));
        game.COPY_BUFFER_1(game.batch,10,30+(45*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,280,30+(45*op),22,18,game.helmet[1]);

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Up")) {op--; };
        if(joypad.consumePush("Down")) {op++;};
        if(joypad.consumePush("Accept"))
        {
            if(op == 0)
            {
                game.DIF = (game.DIF + 1 ) % 3;
            }
            if(op == 1)
            {
                game.epi_actual++; if(game.epi_actual > 6) game.epi_actual = 1;
            }
            else if(op == 2)
            {
                game.lev = (game.lev + 1) % 8;
            }
            else if (op == 3)
            {
                game.freePlay = true;
                for(int j=0; j<3; ++j)
                    game.municion[j]=0;
                for(int j=0; j<6; ++j)
                    game.armas[j]=0;
                game.setScreen(new LoadLevelScreen(game));
                dispose();
            }
        }

        if(op < 0 ) op = 3;
        if(op > 3 ) op = 0;
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
