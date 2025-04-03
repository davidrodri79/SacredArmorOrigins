package com.activeminds.sao;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    public static final int cx = 160;
    public static final int cy = 40;


    class CHARSET {

        Texture tile;
        Texture[] bad_tile = new Texture[3];
        Texture[] mor_tile = new Texture[3];
        Texture wall1;
        Texture wall2;
        Texture[] wall3 = new Texture[3];
        Texture[] door = new Texture[2];
        Texture epi_end;
        Texture fas_end;
        Texture teletrans;
        Texture secret_trans;
        Texture[] boton = new Texture[2];
        Texture[] clos_door = new Texture[2];
        Texture[][] key_door1 = new Texture[2][2];
        Texture[][] key_door2 = new Texture[2][2];
        Texture[][] key_door3 = new Texture[2][2];
        Texture decor1;
        Texture decor2;
        Texture[] decor3 = new Texture[3];
        Texture column;
    };

    class SPRITE {
        Texture []b_stand = new Texture[2];
        Texture []b_pain = new Texture[2];
        Texture []b_punch = new Texture[2];
        Texture [][]l_walk = new Texture[2][3];
        Texture []l_stand = new Texture[2];
        Texture []w_falling = new Texture[4];
        Texture []w_ground  = new Texture[2];
    };

    class ENEMY {
        int []e_xy = new int[4];
        int e_t;
        int e_l;
        int e_d;
        int e_w;
	};

    class T_ENEMY {
        float []xy = new float[4];
        float est;
        float pos;
        int life;
	};

class TELEPORT {
	int []origen_xy = new int[4];          //Un teleport puede estar en un bloque
    int []destino_xy = new int[4];         // convencional
};

class SWITCH {
    int []activador_xy = new int[4];        // Un activador puede estar en un lugar
    int []activable_xy = new int[4];              // convencional
    int estado;
};


    class LEVEL {
        int t_n;
        int e_n;
        int s_n;
        int []start_xy = new int[4];
        String map_name;
        int [][][][]map = new int[10][10][8][8];
        ENEMY []enemies = new ENEMY[100];
        TELEPORT []teleport = new TELEPORT[20];
        SWITCH []switches = new SWITCH[20];

        LEVEL()
        {
            for(int i = 0; i < enemies.length; i++)
                enemies[i] = new ENEMY();
            for(int i = 0; i < teleport.length; i++)
                teleport[i] = new TELEPORT();
            for(int i = 0; i < switches.length; i++)
                switches[i] = new SWITCH();
        }
    };


class EPISODE {
    String epi_name;
    String charset_file;
    String enemy_file;
    LEVEL []nivel = new LEVEL[8];
    };

class BALA {
    float est;
    int fuerza;
    int tipo;
    int dir;
    float vel;
    float []b_xy = new float[4];
    };



    int lev, epi_actual, p_l, x_map, y_map, llave[] = new int[3], n_secrets,
        t_secrets, visto[][] = new int[10][10];
    int[] armas = new int[6];
    int[] completed = new int[5];
    String epi_file, epi_name, file, enemy_file, p_name = "Sigma";
    CHARSET chr = new CHARSET();
    LEVEL fase = new LEVEL();
    T_ENEMY []ene_datos = new T_ENEMY[100];
    SPRITE sol, trp, ene1, ene2;
    boolean MAP = false;
    float frame = 0f, invi = 0f, vari, px, py, escudo;
    char p_d = 0, p_p = 0, p_e = 0, p_w = 0, desp = 0, x_room, y_room;
    int DIF = 1;

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

    String lee_disco(InputStream inputStream, int length)
    {
        StringBuilder result = new StringBuilder();
        boolean endReached = false;
        try {
            for(int i = 0; i < length; i++)
            {
                char c = (char) inputStream.read();
                if(c == 0) endReached = true;
                if(!endReached) result.append(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }

    void carga_nivel() throws IOException {
        int g,i,h,l,j,k;
        char n;
        String path;

        path = "EPISODE/"+epi_file;
        FileHandle f = Gdx.files.internal(path);
        InputStream inputStream = f.read();
        if(inputStream == null) return;
        epi_name = lee_disco(inputStream,25);
        file = lee_disco(inputStream,12);
        enemy_file = lee_disco(inputStream,12);
        for(i=0; i<=lev; ++i){

            fase.t_n = inputStream.read();
            fase.e_n = inputStream.read();
            fase.s_n = inputStream.read();

            for(h = 0; h < 4; h++)
                fase.start_xy[h] = inputStream.read();
            fase.map_name = lee_disco(inputStream,25);

            for(h=0; h<10; ++h){
                for(l=0; l<10; ++l){
                    for(j=0; j<8; ++j){
                        for(k=0; k<8; ++k){
                            fase.map[h][l][j][k] = inputStream.read();
                        };
                    };
                };
            };

            for(g=0; g<100; ++g){
                fase.enemies[g].e_xy[0] = inputStream.read();
                fase.enemies[g].e_xy[1] = inputStream.read();
                fase.enemies[g].e_xy[2] = inputStream.read();
                fase.enemies[g].e_xy[3] = inputStream.read();
                fase.enemies[g].e_t = inputStream.read();
                fase.enemies[g].e_l = inputStream.read();
                fase.enemies[g].e_d = inputStream.read();
                fase.enemies[g].e_w = inputStream.read();
            };
            for(g=0; g<20; g++){
                for(h = 0; h < 4; h++)
                    fase.teleport[g].origen_xy[h] = inputStream.read();
                for(h = 0; h < 4; h++)
                    fase.teleport[g].destino_xy[h] = inputStream.read();
            };
            for(g=0; g<20; g++){
                for(h = 0; h < 4; h++)
                    fase.switches[g].activador_xy[h] = inputStream.read();
                for(h = 0; h < 4; h++)
                    fase.switches[g].activable_xy[h] = inputStream.read();
                fase.switches[g].estado = inputStream.read();
            };
        };
        inputStream.close();

        path = "WARRIOR/"+enemy_file.toUpperCase();
        f = Gdx.files.internal(path);
        if(f.exists() && !f.isDirectory()) {
            inputStream = f.read();

            ene1 = load_warrior(inputStream);
            ene2 = load_warrior(inputStream);
            inputStream.close();
        }

        for(n=0; n<fase.e_n; ++n){
            ene_datos[n] = new T_ENEMY();
            ene_datos[n].xy[0]=fase.enemies[n].e_xy[0];
            ene_datos[n].xy[1]=fase.enemies[n].e_xy[1];
            ene_datos[n].xy[2]=fase.enemies[n].e_xy[2];
            ene_datos[n].xy[3]=fase.enemies[n].e_xy[3];
            ene_datos[n].pos=0;
            ene_datos[n].est=0;
            ene_datos[n].life=fase.enemies[n].e_l;
        };
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

    void load_charset()
    {
        int i,g,j,k,h;

        FileHandle f = Gdx.files.internal("CHARSET/"+file.toUpperCase());
        InputStream inputStream = f.read();

        chr.tile = loadBinaryImage(inputStream, 40, 23);

        for(g=0; g<3; ++g)
            chr.bad_tile[g] = loadBinaryImage(inputStream, 40, 23);

        for(g=0; g<3; ++g)
            chr.mor_tile[g] = loadBinaryImage(inputStream, 40, 23);

        chr.wall1 = loadBinaryImage(inputStream, 20, 70);

        chr.wall2 = loadBinaryImage(inputStream, 20, 70);

        for(g=0; g<3; ++g)
            chr.wall3[g] = loadBinaryImage(inputStream, 20, 70);

        for(g=0; g<2; ++g)
            chr.door[g] = loadBinaryImage(inputStream, 20, 70);

        chr.epi_end = loadBinaryImage(inputStream, 40, 39);

        chr.fas_end = loadBinaryImage(inputStream, 40, 39);

        chr.teletrans = loadBinaryImage(inputStream, 40, 39);

        chr.secret_trans = loadBinaryImage(inputStream, 40, 39);

        for(g=0; g<2; ++g)
            chr.boton[g] = loadBinaryImage(inputStream, 40, 39);

        for(g=0; g<2; ++g)
            chr.clos_door[g] = loadBinaryImage(inputStream, 20, 70);

        for(h=0; h<2; ++h)
            for(g=0; g<2; ++g)
                chr.key_door1[h][g] = loadBinaryImage(inputStream, 20, 70);

        for(h=0; h<2; ++h)
            for(g=0; g<2; ++g)
                chr.key_door2[h][g] = loadBinaryImage(inputStream, 20, 70);

        for(h=0; h<2; ++h)
            for(g=0; g<2; ++g)
                chr.key_door3[h][g] = loadBinaryImage(inputStream, 20, 70);

        chr.decor1 = loadBinaryImage(inputStream, 40, 39);

        chr.decor2 = loadBinaryImage(inputStream, 40, 39);

        for(g=0; g<3; ++g)
            chr.decor3[g] = loadBinaryImage(inputStream, 40, 39);

        chr.column = loadBinaryImage(inputStream, 40, 79);

        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void load_player()
    {
        FileHandle f = Gdx.files.internal("WARRIOR/PLAYER.WAR");
        InputStream inputStream = f.read();
        sol = load_warrior(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    SPRITE load_warrior(InputStream inputStream)
    {
        SPRITE spr = new SPRITE();
        int i,g,h;
        for(i=0; i<2; ++i)
            spr.b_stand[i] = loadBinaryImage(inputStream, 40, 39);
        for(i=0; i<2; ++i)
            spr.b_pain[i] = loadBinaryImage(inputStream, 40, 39);
        for(i=0; i<2; ++i)
            spr.b_punch[i] = loadBinaryImage(inputStream, 40, 39);
        for(h=0; h<2; ++h)
            for(i=0; i<3; ++i)
                spr.l_walk[h][i] = loadBinaryImage(inputStream, 40, 39);
        for(i=0; i<2; ++i)
            spr.l_stand[i] = loadBinaryImage(inputStream, 40, 39);
        for(i=0; i<4; ++i)
            spr.w_falling[i] = loadBinaryImage(inputStream, 40, 59);
        for(i=0; i<2; ++i)
            spr.w_ground[i] = loadBinaryImage(inputStream, 60, 30);

        return spr;
    }

    public void show_mes(String mapName) {
    }

    public int total_secrets() {
        return 0;
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
