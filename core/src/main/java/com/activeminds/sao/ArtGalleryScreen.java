package com.activeminds.sao;

import com.activeminds.sao.jsonloaders.ButtonLayoutJson;
import com.activeminds.sao.jsonloaders.PicturesJson;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class ArtGalleryScreen implements Screen {

    Main game;

    ButtonLayout joypad;

    //String[] pictures={"art/concept1.jpg", "art/concept2.jpg", "art/concept3.jpg"};
    PicturesJson pictures;
    Texture[] textures;
    OrthographicCamera camera;
    int current_pic = 0;

    public static final int VIEWPORT_WIDTH = 540;
    public static final int VIEWPORT_HEIGHT = 200;


    public ArtGalleryScreen(Main game)
    {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        FileHandle file = Gdx.files.internal("art/pictures.json");
        String fileText = file.readString();
        pictures = json.fromJson(PicturesJson.class, fileText);

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("gallery.json");

        textures = new Texture[pictures.pictures.size()];
        for(int i = 0; i < pictures.pictures.size(); i++)
        {
            textures[i] = new Texture(pictures.pictures.get(i));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        Texture current_tex = textures[current_pic];

        float newHeight = VIEWPORT_HEIGHT;
        float newWidth = (newHeight / current_tex.getHeight()) * current_tex.getWidth();

        game.batch.draw(current_tex, (VIEWPORT_WIDTH - newWidth) / 2 ,0, newWidth, newHeight);

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Plus"))
        {
            current_pic = current_pic + 1;
            if(current_pic >= textures.length) current_pic = 0;
        }
        if(joypad.consumePush("Minus"))
        {
            current_pic = current_pic - 1;
            if(current_pic < 0) current_pic = textures.length - 1;
        }
        if(joypad.consumePush("Quit"))
        {
            game.setScreen(new OptionsScreen(game));
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

        for(int i = 0; i < textures.length; i++)
        {
            textures[i].dispose();
        }
    }
}
