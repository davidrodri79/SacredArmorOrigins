package com.activeminds.sao;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    private Texture image;
    public Texture scr;

    float[][] palette;

    public static final int VIEWPORT_WIDTH = 540;
    public static final int VIEWPORT_HEIGHT = 200;
    public static final int GAME_SCREEN_WIDTH = 320;
    public static final int GAME_SCREEN_HEIGHT = 200;
    public static final int GAME_SCREEN_START_X = (VIEWPORT_WIDTH - GAME_SCREEN_WIDTH) /2;




    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        //camera.translate(-176, -100);


        loadPalette();

        Loadfont("ARMOR.FNT");

        setScreen(new MainMenuScreen(this));

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

    private void Loadfont(String s) {
        font = new Texture[256];

        FileHandle file = Gdx.files.internal(s);
        InputStream inputStream = file.read();

        for(int i = 0; i < 256; i++)
        {
            font[i] = loadBinaryImage(inputStream, 11, 12);
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        image.dispose();
    }

    Texture loadBinaryImage(String fileName, int width, int height)
    {
        FileHandle file = Gdx.files.internal(fileName);
        InputStream inputStream = file.read();
        return loadBinaryImage(inputStream, width, height);
    }

    Texture loadBinaryImage(InputStream inputStream, int width, int height)
    {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

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

    public void load_scr(String file)
    {
        scr = loadBinaryImage(file, 320, 200);
    }

    Texture[] font;
    public void Copytext(SpriteBatch batch, int x, int y, String text) {

            int d=0, i = 0;
            do{
                char c = text.charAt(i);
                Copy_Buffer(batch, x+d,y,11,12,font[(int)c],1,0);
                d+=11;
                i++;
            }while(i < text.length());
        }

    private void Copy_Buffer(SpriteBatch batch, int xx, int yy, int sx, int sy, Texture bitmap, int type, int dir)
    {
        batch.draw(bitmap, xx + GAME_SCREEN_START_X, VIEWPORT_HEIGHT - yy - sy);
    }

}
