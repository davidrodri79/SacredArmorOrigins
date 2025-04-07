package com.activeminds.sao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;

import java.nio.ByteBuffer;

public class LoadGameScreen implements Screen {

    Main game;
    ButtonLayout joypad;
    int op = 0;
    public LoadGameScreen(Main game)
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

        String[] epis={"Intro  ","Clasico","Mediev.","Futuro ","Cristal","Volcan ","Infier."},
            difs={"norm.","difi.","extr."};

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(game.scr, game.GAME_SCREEN_START_X, 0);
        game.Copytext(game.batch,80,20,"CARGAR UNA PARTIDA");
        game.Copytext(game.batch,30,170,"Pulsa ESCAPE para abortar");
        game.Copytext(game.batch,65,75-16,"Éµµµµµµµµµµµµµµµµ»");
        String frase;
        for(int j=0; j<5; ++j){
            if(game.gamesaves[j].saved==1)
                frase = "³"+epis[game.gamesaves[j].epi]+" L"+(int)(game.gamesaves[j].level+1)+" "+difs[game.gamesaves[j].dif]+"´";
            else frase = "³  Vacio         ´";
            game.Copytext(game.batch,65,65+16*j,frase);
            if(j<4)game.Copytext(game.batch,65,75+16*j,"©µµµµµµµµµµµµµµµµª");
        };
        game.Copytext(game.batch,65,70+16*4,"È¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¼");
        game.COPY_BUFFER_1(game.batch,30,60+(16*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,268,60+(16*op),22,18,game.helmet[1]);
        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Up")) {op--; };
        if(joypad.consumePush("Down")) {op++;};
        if(joypad.consumePush("Accept"))
        {
            if(game.gamesaves[op].saved != 0)
            {
                game.epi_actual=game.gamesaves[op].epi;
                game.lev=game.gamesaves[op].level;
                game.DIF=game.gamesaves[op].dif;
                for(int j=0; j<3; ++j)
                    game.municion[j]=game.gamesaves[op].ammo[j];
                for(int j=0; j<6; ++j)
                    game.armas[j]=game.gamesaves[op].armas[j];
                for(int j=0; j<6; ++j)
                    game.completed[j]=game.gamesaves[op].completed[j];

                game.setScreen(new LoadLevelScreen(game));
            }
        }

        if(op<0) op=4;
        if(op>4) op=0;

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
