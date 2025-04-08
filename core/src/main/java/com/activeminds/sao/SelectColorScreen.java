package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StringBuilder;

public class SelectColorScreen implements Screen {
    Main game;
    ButtonLayout joypad;
    int op = 0;

    char colores[]={32,69,100,145,210,181,19,165,117};
    public SelectColorScreen(Main game)
    {
        this.game = game;

        game.load_player();
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

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(game.scr, Main.GAME_SCREEN_START_X, 0);
        game.Copytext(game.batch,80,10,"COLOR DEL SOLDADO");
        StringBuilder frase = new StringBuilder();
        frase.append("  Pulsa ");
        frase.append((char)27);
        frase.append(" o ");
        frase.append((char)26);
        frase.append(" para cambiar");
        game.Copytext(game.batch,10,100,frase.toString());
        game.Copytext(game.batch,10,120,"   el color, y ENTER para  ");
        game.Copytext(game.batch,10,140,"         confirmar         ");
        game.COPY_BUFFER_1(game.batch,140,50,40,39,game.sol.l_stand[0]);
        game.COPY_BUFFER_1(game.batch,140,30,40,39,game.sol.b_stand[0]);
        game.batch.end();

        joypad.render(game.batch, game.batch);

        if(joypad.consumePush("Up")) {
            op--; if(op < 0) op = 8;
            game.p_col = colores[op];
            game.load_player();
        };
        if(joypad.consumePush("Down")) {
            op++; if(op > 8) op = 0;
            game.p_col = colores[op];
            game.load_player();
        };
        if(joypad.consumePush("Accept"))
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

    }
}
