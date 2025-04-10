package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class DifficultyScreen implements Screen {

    Main game;
    int op = 0;
    ButtonLayout joypad;
    public DifficultyScreen(Main game)
    {
        this.game = game;

        game.load_scr("WALL.SCR");

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
        game.Copytext(game.batch,120,70,game.loc.get("normal"));
        game.Copytext(game.batch,120,100,game.loc.get("hard"));
        game.Copytext(game.batch,120,130,game.loc.get("extreme"));
        game.Copytext(game.batch,60,20,game.loc.get("difficultyLevel"));
        game.COPY_BUFFER_1(game.batch,80,65+(30*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,218,65+(30*op),22,18,game.helmet[1]);

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Up")) {op--; };
        if(joypad.consumePush("Down")) {op++;};
        if(joypad.consumePush("Accept"))
        {
            game.DIF = op;
            game.freePlay = false;
            game.setScreen(new LoadLevelScreen(game));
            dispose();
        }

        if(op<0) op=2;
        if(op>2) op=0;
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
