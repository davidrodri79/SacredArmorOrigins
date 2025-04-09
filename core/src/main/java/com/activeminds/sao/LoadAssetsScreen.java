package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadAssetsScreen implements Screen {

    Main game;
    float loadProgress;

    LoadAssetsScreen(Main game)
    {
        this.game = game;
        AssetManager  manager = game.manager;

        // Add assets for loading
        manager.load("gui/Button-on.png", Texture.class);
        manager.load("gui/Button-off.png", Texture.class);
        manager.load("SOUND/aniquila.wav", Sound.class);
        manager.load("SOUND/ametrall.wav", Sound.class);
        manager.load("SOUND/clak.wav", Sound.class);
        manager.load("SOUND/dragon.wav", Sound.class);
        manager.load("SOUND/inferno.wav", Sound.class);
        manager.load("SOUND/lanzacoh.wav", Sound.class);
        manager.load("SOUND/pistola.wav", Sound.class);
        manager.load("SOUND/punch.wav", Sound.class);
        manager.load("SOUND/sigma0.wav", Sound.class);
        manager.load("SOUND/sigma1.wav", Sound.class);
        manager.load("SOUND/sigma2.wav", Sound.class);
        manager.load("SOUND/agamma0.wav", Sound.class);
        manager.load("SOUND/agamma1.wav", Sound.class);
        manager.load("SOUND/agamma2.wav", Sound.class);
        manager.load("SOUND/orco0.wav", Sound.class);
        manager.load("SOUND/orco1.wav", Sound.class);
        manager.load("SOUND/orco2.wav", Sound.class);
        manager.load("SOUND/golem0.wav", Sound.class);
        manager.load("SOUND/golem1.wav", Sound.class);
        manager.load("SOUND/golem2.wav", Sound.class);
        manager.load("SOUND/tank0.wav", Sound.class);
        manager.load("SOUND/tank1.wav", Sound.class);
        manager.load("SOUND/tank2.wav", Sound.class);
        manager.load("SOUND/mujer0.wav", Sound.class);
        manager.load("SOUND/mujer1.wav", Sound.class);
        manager.load("SOUND/mujer2.wav", Sound.class);
        manager.load("SOUND/demonio0.wav", Sound.class);
        manager.load("SOUND/demonio1.wav", Sound.class);
        manager.load("SOUND/demonio2.wav", Sound.class);
        manager.load("SOUND/caballe0.wav", Sound.class);
        manager.load("SOUND/caballe1.wav", Sound.class);
        manager.load("SOUND/caballe2.wav", Sound.class);
        manager.load("SOUND/exp_bala.wav", Sound.class);
        manager.load("SOUND/exp_cohe.wav", Sound.class);
        manager.load("SOUND/exp_fueg.wav", Sound.class);
        manager.load("SOUND/itmunici.wav", Sound.class);
        manager.load("SOUND/itllave.wav", Sound.class);
        manager.load("SOUND/itenergi.wav", Sound.class);
        manager.load("SOUND/itbotiqu.wav", Sound.class);
        manager.load("SOUND/itspecia.wav", Sound.class);
        manager.load("SOUND/secret.wav", Sound.class);
        manager.load("SOUND/teleport.wav", Sound.class);
        loadProgress = 0f;

        game.Loadfont("ARMOR.FNT");

        game.load_items();

        game.file = "CLASSIC.CHR";
        game.load_charset();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Render step =============================================
        float currentLoadProgress = game.manager.getProgress();
        if(currentLoadProgress > loadProgress + 0.05f)
        {
            loadProgress = currentLoadProgress;

            game.camera.update();
            game.batch.setProjectionMatrix(game.camera.combined);
            //game.textBatch.setProjectionMatrix(game.textCamera.combined);
            game.shapeRenderer.setProjectionMatrix(game.camera.combined);

            ScreenUtils.clear(Color.BLACK);

            // Progress bar
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            game.shapeRenderer.setColor(Color.YELLOW);
            game.shapeRenderer.rect(38, 38, 464, 34);
            game.shapeRenderer.setColor(Color.BLACK);
            game.shapeRenderer.rect(40, 40, 460, 30);
            game.shapeRenderer.setColor(Color.BROWN);
            game.shapeRenderer.rect(42, 42, 456 * loadProgress, 26);
            game.shapeRenderer.end();
            game.batch.begin();
            game.Copytext(game.batch, 120, 80, game.loc.get("loading"));
            game.Copytext(game.batch, 150, 140, (int) (loadProgress * 100.f) + "%");
            game.batch.end();


        }

        // Update step ====================================
        if(game.manager.update())
        {
            game.setScreen(new MainMenuScreen(game));
            this.dispose();
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
