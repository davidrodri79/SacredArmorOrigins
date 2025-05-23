package com.activeminds.sao;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StringBuilder;

public class MainMenuScreen extends SAOScreen {

    int op = 0;
    ButtonLayout joypad;

    public MainMenuScreen(Main game)
    {
        super(game);

        game.load_scr("TITLE.SCR");

        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("menukeys.json");

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
        game.CopytextColor(game.batch, 120,55,"ORIGINS", new Color(Color.YELLOW));
        game.Copytext(game.batch, 80,75,game.loc.get("newBattle"));
        game.Copytext(game.batch,80,92,game.loc.get("loadGame"));
        game.Copytext(game.batch,80,109,game.loc.get("freePlay"));
        game.Copytext(game.batch,80,126,game.loc.get("help"));
        game.Copytext(game.batch,80,143,game.loc.get("options"));
        game.Copytext(game.batch,80,160,game.loc.get("quit"));
        StringBuilder string = new StringBuilder();
        string.append((char)252);
        string.append((char)253);
        game.Copytext(game.batch,30,185,game.loc.get("activeMindsTm")+string.toString());
        game.COPY_BUFFER_1(game.batch,45,75-3+(17*op),22,18,game.helmet[1]);
        game.COPY_BUFFER_2(game.batch,253,75-3+(17*op),22,18,game.helmet[1]);

        //show_all_font();

        game.batch.end();

        joypad.render(game.batch, game.batch);

        super.render(delta);

        if(!fadingOut()) {
            if (joypad.consumePush("Up")) {
                op--;
            }
            ;
            if (joypad.consumePush("Down")) {
                op++;
            }
            ;
            if (joypad.consumePush("Back")) {
                startFadeOut(2f);
            }
            if (joypad.consumePush("Accept")) {
                if (op == 0) {
                    game.lev = 0;
                    game.p_l = 100;
                    game.epi_actual = 0;
                    for (int j = 0; j < 6; ++j) game.armas[j] = 0;
                    for (int j = 0; j < 6; ++j) game.completed[j] = 0;
                    game.setScreen(new DifficultyScreen(game));
                    dispose();
                }
                if (op == 1) {
                    game.setScreen(new LoadGameScreen(game));
                    dispose();
                }
                if (op == 2) {
                    game.setScreen(new FreePlaySelectScreen(game));
                    dispose();
                }
                if (op == 3) {
                    game.setScreen(new HelpScreen(game));
                    dispose();
                }
                if (op == 4) {
                    game.setScreen(new OptionsScreen(game));
                    dispose();
                }
                if (op == 5) {
                  startFadeOut(2f);
                }
            }

            if (op < 0) op = 5;
            if (op > 5) op = 0;
        }
        if(fadeOutOver())
        {
            dispose();
            game.to_dos();
        }
    }

    void show_all_font()
    {   // 0 32 64 96 128 160 192 224
        ScreenUtils.clear(0, 0, 0, 1f);
        for(int i = 0; i < 256; i++)
        {
            int x = i % 32;
            int y = (int)(i /32);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((char)i);
            game.Copytext(game.batch, 16 * x - Main.GAME_SCREEN_START_X, 20*y, stringBuilder.toString());
        }
    }
}
