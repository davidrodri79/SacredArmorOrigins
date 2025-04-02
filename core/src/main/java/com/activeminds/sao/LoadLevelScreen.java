package com.activeminds.sao;

import com.badlogic.gdx.Screen;

import java.io.IOException;

public class LoadLevelScreen implements Screen {
    Main game;
    public LoadLevelScreen(Main game)
    {
        this.game = game;

        try {
            game.carga_nivel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
