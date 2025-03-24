package com.activeminds.sao;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;

    byte[][] palette;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        loadPalette();
    }

    void loadPalette()
    {
        palette = new byte[256][3];

        FileHandle file = Gdx.files.internal("AMINDS.PAL");

        try (InputStream inputStream = file.read()) {
            /*int byteData;
            while ((byteData = inputStream.read()) != -1) {
                System.out.println("Byte leído: " + byteData);
            }*/
            for (int i = 0; i < 256; i++)
                for (int j = 0; j < 3; j++)
                {
                    palette[i][j] = (byte)inputStream.read();
                }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render() {
        super.render();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        image.dispose();
    }

    Texture loadBinaryImage(String fileName, int width, int height)
    {
        return new Texture(width, height, Pixmap.Format.RGBA8888);
    }
}
