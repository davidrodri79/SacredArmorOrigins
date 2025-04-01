package com.activeminds.sao;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
public class Main extends Game
{
    AssetManager manager;
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

        manager = new AssetManager();
        manager.load("gui/Button-on.png", Texture.class);
        manager.load("gui/Button-off.png", Texture.class);
        manager.finishLoading();

        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        //camera.translate(-176, -100);


        loadPalette();

        Loadfont("ARMOR.FNT");
        load_items();

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

    Texture[] keys;
    Texture[] ammo;
    Texture[] addings;
    Texture[][] weapon;
    Texture marca1, marca2;
    Texture helmet[];


    void load_items()
    {
        int i,g,h,j;
        FileHandle file = Gdx.files.internal("OBJECTS.CHR");
        InputStream inputStream = file.read();

        keys = new Texture[3];
        for(g=0; g<3; ++g)
            keys[g] = loadBinaryImage(inputStream,24,26);
        ammo = new Texture[5];
        for(g=0; g<5; ++g)
            ammo[g] = loadBinaryImage(inputStream,24,26);
        addings = new Texture[12];
        for(g=0; g<12; ++g)
            addings[g] = loadBinaryImage(inputStream,24,26);
        weapon = new Texture[2][6];
        for(h=0; h<2; ++h)
            for(g=0; g<6; ++g)
                weapon[h][g] = loadBinaryImage(inputStream,40,27);
        marca1 = loadBinaryImage(inputStream, 97, 34);
        marca2 = loadBinaryImage(inputStream, 51, 50);

        helmet = new Texture[2];
        helmet[0] = loadBinaryImage(inputStream, 22, 18);
        helmet[1] = helmet[0];

/*
        for(j=0; j<2; ++j)
            for(h=0; h<2; ++h)
                for(g=0; g<3; ++g)
                    for(i=0; i<1080; ++i)
                        fscanf(ptr,"%c",&bullet[j][h][g][i]);
        for(h=0; h<3; ++h)
            for(g=0; g<3; ++g)
                for(i=0; i<1080; ++i)
                    fscanf(ptr,"%c",&explos[h][g][i]);
        for(g=0; g<2; ++g)
            for(i=0; i<1180; ++i)
                fscanf(ptr,"%c",&field[g][i]);
        for(i=0; i<920; ++i)
            fscanf(ptr,"%c",&sombra[i]);

        fclose(ptr);*/
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    public void COPY_BUFFER_1(SpriteBatch batch, int x, int y, int sx, int sy, Texture texture) {
        batch.draw(texture, x + GAME_SCREEN_START_X, VIEWPORT_HEIGHT - y - sy);
    }

    public void COPY_BUFFER_2(SpriteBatch batch, int x, int y, int sx, int sy, Texture texture) {
        batch.draw(texture, x + GAME_SCREEN_START_X, VIEWPORT_HEIGHT - y - sy, sx, sy, 0, 0, sx, sy, true, false );
    }
}
