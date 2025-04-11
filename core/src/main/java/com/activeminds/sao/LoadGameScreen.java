package com.activeminds.sao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;

import java.nio.ByteBuffer;

public class LoadGameScreen extends SAOScreen {


    ButtonLayout joypad;
    int op = 0;
    public LoadGameScreen(Main game)
    {
        super(game);

        game.load_scr("WALL.SCR");
        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");
    }

    @Override
    public void render(float delta) {

        String[] epis={game.loc.get("epiShort0"),
            game.loc.get("epiShort1"),
            game.loc.get("epiShort2"),
            game.loc.get("epiShort3"),
            game.loc.get("epiShort4"),
            game.loc.get("epiShort5"),
            game.loc.get("epiShort6")},
            difs={game.loc.get("difShort1"), game.loc.get("difShort2"), game.loc.get("difShort3")};

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(game.scr, game.GAME_SCREEN_START_X, 0);
        game.Copytext(game.batch,80,20,game.loc.get("loadAGame"));
        game.Copytext(game.batch,30,170,game.loc.get("pressEscapeAbort"));
        game.Copytext(game.batch,65,75-16,"Éµµµµµµµµµµµµµµµµ»");
        String frase;
        for(int j=0; j<5; ++j){
            if(game.gamesaves[j].saved==1)
                frase = "³"+epis[game.gamesaves[j].epi]+" L"+(int)(game.gamesaves[j].level+1)+" "+difs[game.gamesaves[j].dif]+"´";
            else frase = "³"+game.loc.get("emptySlot")+"´";
            game.Copytext(game.batch,65,65+16*j,frase);
            if(j<4)game.Copytext(game.batch,65,75+16*j,"©µµµµµµµµµµµµµµµµª");
        };
        game.Copytext(game.batch,65,70+16*4,"È¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¼");
        game.COPY_BUFFER_1(game.batch,30,60+(16*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,268,60+(16*op),22,18,game.helmet[1]);
        game.batch.end();

        joypad.render(game.batch, game.batch);

        super.render(delta);

        if(!fadingOut()) {
            if(joypad.consumePush("Up")) {op--; };
            if(joypad.consumePush("Down")) {op++;};
            if(joypad.consumePush("Accept"))
            {
                if(game.gamesaves[op].saved != 0)
                {
                    startFadeOut(2f);
                }
            }

            if (joypad.consumePush("Back")) {
                game.freePlay = false;
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }

            if (op < 0) op = 4;
            if (op > 4) op = 0;
        }

        if(fadeOutOver())
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
            dispose();
        }

    }
}
