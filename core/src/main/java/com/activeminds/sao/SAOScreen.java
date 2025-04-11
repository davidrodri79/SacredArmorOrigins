package com.activeminds.sao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SAOScreen implements Screen {

    Main game;
    SpriteBatch batch;
    boolean fadingOut;
    float fadeOutTimer, getFadeOutDuration;

    public SAOScreen(Main game)
    {
        this.game = game;
        batch = game.batch;
        fadingOut = false;
    }

    void startFadeOut(float duration)
    {
        fadingOut = true;
        fadeOutTimer = 0f;
        getFadeOutDuration = duration;
    }

    boolean fadingOut()
    {
        return fadingOut;
    }

    boolean fadeOutOver()
    {
        return fadingOut && fadeOutTimer >= getFadeOutDuration;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(fadingOut()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);  // Habilita transparencia
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            game.shapeRenderer.setColor(0f, 0f, 0f, fadeOutTimer / getFadeOutDuration);
            game.shapeRenderer.rect(Main.GAME_SCREEN_START_X, 0, Main.GAME_SCREEN_WIDTH, Main.GAME_SCREEN_HEIGHT);
            game.shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);

            fadeOutTimer += delta;
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
