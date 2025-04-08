package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class FinalScreen implements Screen {

    Main game;
    ButtonLayout joypad;
    int step = 0, current_phrase = 0;
    long lastPhraseChangeTime;
    public FinalScreen(Main game)
    {
        this.game = game;

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

        game.load_scr("WALL.SCR");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        String frases[]={"  Active Minds 1998,2025   ",
            " Argumento original : NHSP ",
            "  Episodio clasico : NHSP  ",
            " Episodio medieval : NHSP  ",
            " Episodio futurista : MTX  ",
            "Episodio volcan : MTX,LUKE ",
            "  Episodio cristal : NHSP  ",
            " Episodio infierno : NHSP  ",
            "   Ilustraciones : NHSP    ",
            "   Fuente y fondos : MTX   ",
            "    Menus : NHSP y MTX     ",
            "    ¡Gracias por jugar!    ",
            ""};

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();

        if(step == 0) {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.COPY_BUFFER_1(game.batch, 140, 40, 40, 39, game.chr.epi_end);
            game.Copytext(game.batch, 50, 20, game.epi_name);
            game.Copytext(game.batch, 10, 90, " Este parece ser el cerebro");
            game.Copytext(game.batch, 10, 110, " de esta fortaleza. Si lo ");
            game.Copytext(game.batch, 10, 130, " destruyes, los monstruos");
            game.Copytext(game.batch, 10, 150, " moriran y habras cumplido");
            game.Copytext(game.batch, 10, 170, "        tu mision.");
        }
        else if (step == 1)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,10,30," Ahora, tu mundo podra re-");
            game.Copytext(game.batch,10,50," cuperar su libertad.Seras");
            game.Copytext(game.batch,10,70," un heroe de leyenda, el");
            game.Copytext(game.batch,10,90," guerrero de la Armadura");
            game.Copytext(game.batch,10,110," Sagrada.");
            game.Copytext(game.batch,10,130," Pero sabes que muchos");
            game.Copytext(game.batch,10,150," otros mundos no estan");
            game.Copytext(game.batch,10,170," a salvo de esta amenaza.");
        }
        else if (step == 2)
        {
            game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
            game.Copytext(game.batch,15,185,frases[current_phrase]);
            if(current_phrase==11) game.Copytext(game.batch,130,90,"  FIN  ");

            if(System.currentTimeMillis() > lastPhraseChangeTime + 2000)
            {
                lastPhraseChangeTime = System.currentTimeMillis();
                if(current_phrase < 11) current_phrase++;
            }
        }

        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Accept"))
        {
            {
                step++;
                if(step == 1)
                {
                    game.load_scr("MAP.SCR");
                }
                if(step == 2)
                {
                    game.load_scr_color_swap("FINAL.SCR", (char)69, game.p_col);
                    lastPhraseChangeTime = System.currentTimeMillis();
                }
                if(step == 3)
                {
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                }

            }
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
