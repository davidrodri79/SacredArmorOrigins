package com.activeminds.sao;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
    private Texture image, wall;

    float[][] palette;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        loadPalette();
        wall = loadBinaryImage("SOLDIER.SCR", 320,200);

    }

    void loadPalette()
    {
        palette = new float[256][3];

        FileHandle file = Gdx.files.internal("AMINDS.PAL");

        try (InputStream inputStream = file.read()) {
            /*int byteData;
            while ((byteData = inputStream.read()) != -1) {
                System.out.println("Byte leído: " + byteData);
            }*/
            for (int i = 0; i < 256; i++)
                for (int j = 0; j < 3; j++)
                {
                    palette[i][j] = (float)inputStream.read() / 64.f;
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
        batch.draw(wall, 0, 0);
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
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        FileHandle file = Gdx.files.internal(fileName);

        InputStream inputStream = file.read();

        try {
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
            {
                int byteData = inputStream.read();
                pixmap.setColor(palette[byteData][0], palette[byteData][1], palette[byteData][2], byteData == 0 ? 0.f : 1.0f );
                //pixmap.setColor(Color.RED); // Color del píxel
                pixmap.drawPixel(j, i);   // Dibuja un píxel en (50,50)

            }
        } catch (IOException e) {
            System.out.println("Error!!! Not eneough pixels");
        }
        return new Texture(pixmap);
    }
}
