package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StringBuilder;

public class GameScreen implements Screen {

    Main game;
    int x, y, teleport_state = 0;
    ButtonLayout joypad, minimap;
    float mes_c = 0f, K, player_fade;
    char[][] tel_map = new char[8][8], int_map  = new char[8][8], ene_map  = new char[8][8];
    String message;
    boolean FIN_DE_FASE, FIN_EPI, SECRET_FASE;
    long start_time;


    GameScreen(Main game)
    {
        this.game = game;
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("joypad.json");
        minimap = new ButtonLayout(game.camera, game.manager, null);
        minimap.loadFromJson("minimap.json");
        show_mes(game.fase.map_name);
        FIN_EPI = false;
        FIN_DE_FASE = false;
        SECRET_FASE = false;
        if(game.MAP) minimap.setAsActiveInputProcessor();
        else joypad.setAsActiveInputProcessor();
        start_time = System.currentTimeMillis();

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        K = delta * 3f;

        // LOGIC STEP =========================================

        /*do{
            asm{
                mov ah , 0
                int 1Ah
            };
            m1=_DX;*/
        if(FIN_EPI)
        {
            if(game.epi_actual == 0)
            {
                if((game.x_map==4) && (game.y_map==5)) game.epi_actual=1;
                if((game.x_map==2) && (game.y_map==4)) game.epi_actual=2;
                if((game.x_map==6) && (game.y_map==4)) game.epi_actual=3;
                if((game.x_map==2) && (game.y_map==2)) game.epi_actual=4;
                if((game.x_map==6) && (game.y_map==2)) game.epi_actual=5;
                if((game.x_map==4) && (game.y_map==1)) game.epi_actual=6;
                game.lev=0;

                game.setScreen(new LoadLevelScreen(game));
                dispose();
            }
            else if(game.epi_actual == 6)
            {
                game.setScreen(new FinalScreen(game));
                dispose();
            }
            else
            {
                game.setScreen(new EpisodeEndScreen(game));
                dispose();
            }
        }
        else if(FIN_DE_FASE)
        {
            if(teleport_state == 1)
            {
                player_fade += 2*K;
                if(player_fade > 10.f)
                {
                    long intervalo=(System.currentTimeMillis() - start_time)/1000;
                    game.horas=(int)intervalo/3600;
                    game.mins=(int)intervalo/60;
                    game.secs=(int)intervalo-(3600*game.horas)-(60*game.mins);
                    game.setScreen(new LevelResultsScreen(game));
                    dispose();
                }
            }
        }
        else if (SECRET_FASE)
        {
            if(teleport_state == 1)
            {
                player_fade += 2*K;
                if(player_fade > 10.f)
                {
                    game.setScreen(new SecretLevelScreen(game));
                    dispose();
                }
            }
        }
        else if (teleport_state != 0)
        {
            if(teleport_state == 1)
            {
                player_fade += 2*K;
                if(player_fade > 10.f)
                {
                    teletransporte((char)(tel_map[game.x_room][game.y_room]-1));
                    set_maps();
                    teleport_state = 2;
                }
            }
            else
            {
                player_fade -= 2*K;
                if(player_fade <= 0)
                {
                    teleport_state = 0;
                    player_fade = 0;
                }
            }
        }
        else
        {
            game.x_room = (char) game.px;
            game.y_room = (char) game.py;
            game.vari += 2 * K;
            if (game.vari >= 3) game.vari -= 3.0;

            //play_sound();

            if (game.frame < 3.0f) game.frame = game.vari;
            else game.frame = game.vari - 2.0f;

            game.visto[game.x_map][game.y_map] = 1;

            if (mes_c > 0) {
                mes_c -= K;
            }
            ;



            if(game.escudo>0) game.escudo-=K;
            if(game.pocima>0) game.pocima-=K;
            if(game.invi>0) game.invi-=K;


            // MOVER ENEMIGOS, AQUI
            for(int g=0; g<game.fase.e_n; ++g)
                if(((char)game.ene_datos[g].xy[0]==game.x_map) && ((char)game.ene_datos[g].xy[1]==game.y_map)) move_enemy((char)g);


            if(tel_map[game.x_room][game.y_room] > 0) teleport_state = 1;

            if((int_map[game.x_room][game.y_room] > 0) && (joypad.consumePush("Push"))) conecta_int((char)(int_map[game.x_room][game.y_room]-1));

            for(char i=0; i<10; ++i)
                if(game.balas[i].est > 0) move_bullet(i);



            /*if(kbhit())
                key=toupper(getch());*/

            if(((game.p_l<=0) && joypad.consumePush("Fire"))) { game.setScreen(new LoadLevelScreen(game)); dispose(); }

            if ((game.p_e >= 5) && (game.p_e < 13)) {
                //NO_MUEVE;
            } else {
                game.p_p = 0;
                if (joypad.isPressed("North")) {
                    if (!joypad.isPressed("Strafe"))
                        game.p_d = 2;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px, game.py - K);
                } else if (joypad.isPressed("South")) {
                    if (!joypad.isPressed("Strafe"))
                        game.p_d = 0;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px, game.py + K);
                } else if (joypad.isPressed("West")) {
                    if (!joypad.isPressed("Strafe"))
                        game.p_d = 1;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px - K, game.py);
                } else if (joypad.isPressed("East")) {
                    if (!joypad.isPressed("Strafe"))
                        game.p_d = 3;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px + K, game.py);
                }
                ;
                //TECLA_REC:
                if (joypad.consumePush("Fire")) p_disparo();
                if ((minimap.consumePush("Plus")) && (game.S_MAP < 5)) {
                    game.S_MAP++;
                }
                ;
                if ((minimap.consumePush("Minus")) && (game.S_MAP > 1)) {
                    game.S_MAP--;
                }
                ;
            }
            set_maps();

            if(joypad.consumePush("Weapon"))
            {
                do {
                    game.p_w = (char)(game.p_w + 1);
                    if(game.p_w >= 7) game.p_w = 0;
                } while((game.p_w != 0) && (game.armas[(game.p_w-1)] == 0));
            }
            /*
            // Cambio de armas
            if(keymap[11]) p_w=0;
            if(keymap[2]){
                if(armas[0]) p_w=1;
                else show_mes("Pistola no disponible");
            };
            if(keymap[3]){
                if(armas[1]) p_w=2;
                else show_mes("Ametralladora no disponible");
            };
            if(keymap[4]){
                if(armas[2]) p_w=3;
                else show_mes("Lanzacohetes no disponible");
            };
            if(keymap[5]){
                if(armas[3]) p_w=4;
                else show_mes("Aniquilador no disponible");
            };
            if(keymap[6]){
                if(armas[4]) p_w=5;
                else show_mes("\"Drag�n\" no disponible");
            };
            if(keymap[7]){
                if(armas[5]) p_w=6;
                else show_mes("\"Infierno\" no disponible");
            };*/
            if(joypad.consumePush("Map")) {
                game.MAP = true;
                minimap.setAsActiveInputProcessor();
            }
            if(minimap.consumePush("Map")){
                game.MAP = false;
                joypad.setAsActiveInputProcessor();
            };
            if(minimap.consumePush("Skip")){
                SECRET_FASE = true;
                player_fade = 0f;
                teleport_state = 1;
            }
            if(minimap.consumePush("EpisodeEnd")){
                FIN_EPI = true;
                player_fade = 0f;
                teleport_state = 1;
            }
            /*
            if(keymap[59]) {help();
                asm{
                    mov ah , 0
                    int 1Ah
                };
                m1=_DX;
            };
            NO_MUEVE:

            // Cheats !!!

            if((keymap[23]) && (keymap[20]) && (keymap[18]) && (keymap[50]))
            { show_mes("�Todas las armas y munici�n!");
                for(j=0; j<6; ++j) armas[j]=1;
                for(j=0; j<3; ++j) municion[j]=200;
            };
            if((keymap[17]) && (keymap[30]) && (keymap[19]) &&(keymap[25]))
			 goto FIN_DE_FASE;

            if((keymap[30]) && (keymap[38]) && (keymap[37]) && (keymap[18]) && (keymap[21]))
            { show_mes("�Todas las llaves!");
                for(j=0; j<3; ++j) llave[j]=1;
            };
            if((keymap[17]) && (keymap[30]) && (keymap[38]))
            { show_mes("�Invulnerabilidad!");
                escudo=150;
            };

            if((keymap[25]) && (keymap[24]) && (keymap[17]) && (keymap[18]) && (keymap[19]))
            { show_mes("�Hiper fuerza!");
                pocima=150;
            };
            if((keymap[35]) && (keymap[18]) && (keymap[30]) && (keymap[38]) && (keymap[20]))
            { show_mes("�Salud m�xima!");
                p_l=200;
            };

            if((keymap[17]) && (keymap[23]) && (keymap[18]) && (keymap[47]))
            { show_mes("�Mapa completo!");
                all_map();

            };
            if((keymap[19]) && (keymap[30]) && (keymap[32]))
            { show_mes("�Radar de enemigos!");
                E_MAP=1-E_MAP;
            };

            if((keymap[23]) && (keymap[49]) && (keymap[47]))
            { show_mes("�Invisibilidad!");
                invi=150;
            };

            if((keymap[34]) && (keymap[24]) && (keymap[32]))
            { show_mes("�Atravesar paredes!");
                GHOST=1-GHOST;
            };*/

            if(game.p_e==13) p_punch();

            /*if((keymap[27]) && (K>0.01)) {K-=0.03; keymap[27]=0; };
            if((keymap[53]) && (K<1.0)) {K+=0.03; keymap[53]=0; };

            vaciarbuffer();*/

            if(game.p_e>=14) game.p_e=0;
            if((game.p_e>0) && (game.p_e<5)) game.p_e-=K;
            if((game.p_e>4) && (game.p_e<7)) game.p_e+=K;
            if((game.p_e>8) && (game.p_e<11)) game.p_e+=K;
            if((game.p_e>12) && (game.p_e<14)) game.p_e+=K;

            // Da�o al jugador

            /*if(p_e==13) p_punch();
            */
            if ((game.p_e < 1) && (game.escudo <= 0) && (K >= 0.f)) {
                switch (game.fase.map[game.x_map][game.y_map][(char) game.px][(char) game.py]) {

                    case 10:
                        game.p_e = 2;
                        game.p_l -= 3;
                        break;
                    case 51:
                    case 11:
                        game.p_l = 0;
                        break;
                    case 29:
                        float ppy=game.py-0.5f;
                        float ppx=game.px-0.5f;
                        float xx=game.cx-(20*ppy)+(20*ppx);
                        float yy=game.cy+(10*ppy)+(10*ppx)+game.desp;
                        float xa=xx-20; float ya=yy-20;
                        show_mes(game.p_name+" ha salido de la zona.");
                        FIN_DE_FASE = true;
                        player_fade = 0f;
                        teleport_state = 1;
                        break;
                    case 31:
                        show_mes("¡¿Una salida secreta?!");
                        SECRET_FASE = true;
                        player_fade = 0f;
                        teleport_state = 1;
                        break;
                    case 28:
                        FIN_EPI = true;
                        break;

                }
                ;


                for(int h=0; h<10; h++){
                    if((game.balas[h].est==1) && ((char)game.balas[h].b_xy[2]==(char)game.px)
                        && ((char)game.balas[h].b_xy[3]==(char)game.py)
                        && ((char)game.balas[h].b_xy[0]==game.x_map)
                        && ((char)game.balas[h].b_xy[1]==game.y_map)){
                        game.balas[h].est=2; //start_sound(explosion);
                        game.p_e=2;
                        game.p_l-=game.balas[h].fuerza;
                    };
                };


                if(game.p_l<1) {
                    game.p_e=5;
                    show_mes(game.p_name+" ha muerto.");
                    //start_sound(dying);
                };
                if(game.p_l<-4) {
                    game.p_e=9;
                    show_mes(game.p_name+" ha sido destruido.");
                    mes_c=25;
                    //start_sound(falling);
                };

            }
            ;


            // Da�o a un enemigo
            for(int g=0; g<8; ++g){
                for(int i=0; i<8; ++i){
                    for(int h=0; h<10; ++h){
                        for(int l=0; l<game.fase.e_n; ++l){
                            if(((char)game.ene_datos[l].xy[0]==game.x_map) &&
                                ((char)game.ene_datos[l].xy[1]==game.y_map) &&
                                ((char)game.ene_datos[l].xy[2]==g) &&
                                ((char)game.ene_datos[l].xy[3]==i) &&
                                ((char)game.balas[h].b_xy[0]==game.x_map) &&
                                ((char)game.balas[h].b_xy[1]==game.y_map) &&
                                ((char)game.balas[h].b_xy[2]==g) &&
                                ((char)game.balas[h].b_xy[3]==i) &&
                                ((char)game.balas[h].est==1))
                                enemy_impact((char)l,(char)h);
                        };
                    };
                };
            };

            /*
            // COMPROBACION DEL TIEMPO TRANSCURRIDO

            asm{
                mov ah , 0
                int 1Ah
            };
            m2=_DX;
            K=(float)((m2-m1)*0.25);

        }while(key!='\x1B');
        */

            if(minimap.consumePush("Quit"))
            {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }

        // RENDER STEP ========================================

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.shapeRenderer.setProjectionMatrix(game.camera.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(!game.MAP) visualizar(game.batch, 1,1);
        else map_2d();
        if(mes_c > 0) {game.Copytext(game.batch,5,5,message); };
        game.COPY_BUFFER_1(game.batch,0,166,97,34,game.marca1);
        game.COPY_BUFFER_1(game.batch,269,150,51,50,game.marca2);
        if((game.p_e>6) && (game.p_e<13)) game.Copytext(game.batch,30,95,"PULSA DISPARO PARA REINICIAR");
        variables();
        game.batch.end();
        game.shapeRenderer.end();
        /*
        CLEAR_BUFFER(scr,0);
        if(!MAP)visualizar(1,1);
        else map_2d();
        if(mes_c) {Copytext(scr,5,5,message); mes_c--;};
        COPY_BUFFER_1(scr,0,166,97,34,marca1);
        COPY_BUFFER_1(scr,269,150,51,50,marca2);
        if((p_e>6) && (p_e<13)) Copytext(scr,30,95,"PULSA SPACE PARA REINICIAR");
        variables();*/

        //FLIP_BUFFER(scr);

        if(game.MAP)
            minimap.render(game.batch, game.batch);
        else
            joypad.render(game.batch, game.batch);

    }

    void all_map()
    {
        int i,j;
        for(i=0; i<10; ++i)
            for(j=0; j<10; ++j) game.visto[i][j]=1;
    }

    void teletransporte(char n)
    {
        game.x_map=game.fase.teleport[n].destino_xy[0];
        game.y_map=game.fase.teleport[n].destino_xy[1];
        game.px=(float)game.fase.teleport[n].destino_xy[2];
        game.py=(float)game.fase.teleport[n].destino_xy[3];
    }

    void p_disparo()
    {
        char i;
        float xx=0f, yy=0f;
        switch(game.p_d){
            case 0 : xx=game.px; yy=game.py+1.5f; break;
            case 1 : xx=game.px-1.5f; yy=game.py; break;
            case 2 : xx=game.px; yy=game.py-1.5f; break;
            case 3 : xx=game.px+1.5f; yy=game.py; break;
        };

        boolean NO_AMMO = false;
        switch(game.p_w){
            case 0 : if((char)game.p_e==0) game.p_e=13; break;
            case 1 : if(game.municion[0] == 0) NO_AMMO = true;
                     else {
                         asigna_bala(xx,yy,3,0,game.p_d);
                        game.municion[0]-=1;
                        //start_sound(weap1);
                     }
                     break;
            case 2 : if(game.municion[0]<3) NO_AMMO = true;
                    else {
                        asigna_bala(xx, yy, 3, 0, game.p_d);
                        asigna_bala(xx + 0.2f, yy, 3, 0, game.p_d);
                        asigna_bala(xx - 0.2f, yy, 3, 0, game.p_d);
                        game.municion[0] -= 3;
                        //start_sound(weap2);
                    }
                    break;
            case 3 : if(game.municion[1] == 0) NO_AMMO = true;
                    else {
                        asigna_bala(xx,yy,1.3f,1,game.p_d);
                        game.municion[1]-=1;
                        //start_sound(weap3);
                    }
                    break;
            case 4 : if(game.municion[1]<2) NO_AMMO = true;
                    else {
                        asigna_bala(xx - 0.2f, yy, 1.6f, 1, game.p_d);
                        asigna_bala(xx + 0.3f, yy, 1.6f, 1, game.p_d);
                        game.municion[1] -= 2;
                        //start_sound(weap3);
                    }
                    break;
            case 5 : if(game.municion[2]<5) NO_AMMO = true;
                    else {
                        asigna_bala(xx,yy,1.5f,2,game.p_d);
                        game.municion[2]-=5;
                        //start_sound(weap5);
                    }
                 break;
            case 6 : if(game.municion[2]<15) NO_AMMO = true;
                    else {
                        asigna_bala(xx-0.3f,yy,1,2,game.p_d);
                        asigna_bala(xx+0.3f,yy,1,2,game.p_d);
                        game.municion[2]-=15;
                        //start_sound(weap5);
                    }
                    break;
        };
        if(NO_AMMO) show_mes("No queda municion");

    }

    void p_punch()
    {
        char g,i,xy;
        xy=ene_map[(int)game.px][(int)game.py];

        if(xy > 0){
            if((char)game.ene_datos[xy-1].est>0) return;
            game.ene_datos[xy-1].est=2;
            game.ene_datos[xy-1].life-=5;
            if(game.pocima>0) game.ene_datos[xy-1].life-=15;
            //start_sound(clak);
            if(game.ene_datos[xy-1].life<=0)
                game.ene_datos[xy-1].est=5;
            if(game.ene_datos[xy-1].life<=-5)
                game.ene_datos[xy-1].est=9;
        };

    }
    void e_punch(char n)
    {
        char g,i,xy;
        //out_var(300,10,(int)game.p_e);
        if(((char)game.px==(char)game.ene_datos[n].xy[2]) && ((char)game.py==(char)game.ene_datos[n].xy[3]) && ((char)game.p_e==0) && game.escudo == 0){
            game.p_e=2;
            game.p_l-=5;
            //start_sound(clak);
        };
        if (game.p_e>4) return;
        if(game.p_l<1) {game.p_e=5; show_mes(game.p_name+" ha muerto.");};
        if(game.p_l<-4) {game.p_e=9; show_mes(game.p_name+"%s ha sido destruido.");};
    }

    void enemy_impact(char ene, char bul)
    {
        char p=1;
        if(game.pocima>0) p=4;
        if(game.ene_datos[ene].est>=5) return;
        game.balas[bul].est=2; //start_sound(explosion);
        game.ene_datos[ene].est=2;
        game.ene_datos[ene].life-=game.balas[bul].fuerza*p;
        if(game.ene_datos[ene].life<=0)
        {game.ene_datos[ene].est=5; /*start_sound(dying);*/};
        if(game.ene_datos[ene].life<=-5)
        {game.ene_datos[ene].est=9; /*start_sound(falling);*/};
    }

    void asigna_bala(float xx, float yy, float vv, int tipo, char dir)
    {
        char i=0;
        /* Buscar una bala libre para usarla */
        do{
            i++;
            if(i==10) return;

        } while(game.balas[i].est!=0);

        game.balas[i].b_xy[0]=game.x_map;
        game.balas[i].b_xy[1]=game.y_map;
        game.balas[i].b_xy[2]=xx;
        game.balas[i].b_xy[3]=yy;
        game.balas[i].tipo=tipo;
        game.balas[i].fuerza=8+(8*tipo);
        game.balas[i].est=1;
        game.balas[i].vel=vv;
        game.balas[i].dir=dir;

    }

    void conecta_int(char n)
    {
        char g,i,h,f;
        game.fase.switches[n].estado=1;
        g=(char)game.fase.switches[n].activable_xy[0];
        i=(char)game.fase.switches[n].activable_xy[1];
        h=(char)game.fase.switches[n].activable_xy[2];
        f=(char)game.fase.switches[n].activable_xy[3];

        switch(game.fase.map[g][i][h][f]){
            case 50 : game.fase.map[g][i][h][f]=37; game.fase.map[g][i][h][f-1]=36; break;
            case 48 : game.fase.map[g][i][h][f]=9; game.fase.map[g][i][h-1][f]=8; break;
            case 51 : game.fase.map[g][i][h][f]=1; break;
        };
        //start_sound(clak);
    }


    void move_enemy(char n)
    {
        float xx, yy;
        int r, est;

        r=(int)(Math.random()) % (20-(7*game.DIF));
        game.ene_datos[n].pos=0;
        est=(int)game.ene_datos[n].est;
        xx=game.ene_datos[n].xy[2];
        yy=game.ene_datos[n].xy[3];

        boolean N_DIR = false;

        if((est>=5) && (est<13))
        {
            //NO_MOVE;
        }
        else {

            if (r < 5) {
                if (r < 3) {
                    if (((char) xx == (char) game.px) && ((char) yy < (char) game.py))
                        game.fase.enemies[n].e_d = 0;
                    if (((char) xx == (char) game.px) && ((char) yy > (char) game.py))
                        game.fase.enemies[n].e_d = 2;
                    if (((char) yy == (char) game.py) && ((char) xx > (char) game.px))
                        game.fase.enemies[n].e_d = 1;
                    if (((char) yy == (char) game.py) && ((char) xx < (char) game.px))
                        game.fase.enemies[n].e_d = 3;
                    if (game.invi > 0) game.fase.enemies[n].e_d = (char)Math.random() % 4;
                }
                ;
                game.ene_datos[n].pos = game.frame + 1;

                switch (game.fase.enemies[n].e_d) {
                    case 0:
                        if (!ene_acceso(xx, yy + K, n)) N_DIR = true;
                        break;
                    case 1:
                        if (!ene_acceso(xx - K, yy, n)) N_DIR = true;
                        break;
                    case 2:
                        if (!ene_acceso(xx, yy - K, n)) N_DIR = true;
                        break;
                    case 3:
                        if (!ene_acceso(xx + K, yy, n)) N_DIR = true;
                        break;
                } ;
            } ;

            if((int)(100f*Math.random()) % (20-(4*game.DIF))==0) e_disparo(n);

        }

	    if(N_DIR)
        {
            game.fase.enemies[n].e_d=(char)Math.random() % 4;
        }
        //NO_MOVE:
        if(est==13) e_punch(n);
        if(est>=14) game.ene_datos[n].est=0;
        if((est>0) && (est<5)) game.ene_datos[n].est-=K;
        if((est>4) && (est<7)) game.ene_datos[n].est+=K;
        if((est>8) && (est<11)) game.ene_datos[n].est+=K;
        if((est>12) && (est<14)) game.ene_datos[n].est+=K;

    }

    void e_disparo(char n)
    {
        char i, dir;
        float xx = 0f, yy = 0f, ex, ey;

        dir=(char)game.fase.enemies[n].e_d;
        ex=game.ene_datos[n].xy[2];
        ey=game.ene_datos[n].xy[3];


        switch(dir){
            case 0 : xx=ex; yy=ey+1.5f; break;
            case 1 : xx=ex-1.5f; yy=ey; break;
            case 2 : xx=ex; yy=ey-1.5f; break;
            case 3 : xx=ex+1.5f; yy=ey; break;
        };

        switch(game.fase.enemies[n].e_w){
            case 0 : if((char)game.ene_datos[n].est==0) game.ene_datos[n].est=13; break;
            case 1 : asigna_bala(xx,yy,3,0,dir); /*start_sound(weap1);*/ break;
            case 2 : asigna_bala(xx,yy,3,0,dir); asigna_bala(xx+0.2f,yy,3,0,dir);
                asigna_bala(xx-0.2f,yy,3,0,dir);
                //start_sound(weap2);
                break;
            case 3 : asigna_bala(xx,yy,1.3f,1,dir);
                //start_sound(weap3);
                break;
            case 4 : asigna_bala(xx-0.2f,yy,1.6f,1,dir); asigna_bala(xx+0.3f,yy,1.6f,1,dir);
                //start_sound(weap3);
                break;
            case 5 : asigna_bala(xx,yy,1.5f,2,dir);
                //start_sound(weap5);
                break;
            case 6 : asigna_bala(xx-0.3f,yy,1,2,dir); asigna_bala(xx+0.3f,yy,1,2,dir);
                //start_sound(weap5);
                break;

        };

    }


    void move_bullet(char n)
    {
        if((game.balas[n].b_xy[0]!=game.x_map) || (game.balas[n].b_xy[1]!=game.y_map)){
            game.balas[n].est=0; return;};

        if(game.balas[n].est==1){
            switch(game.balas[n].dir){
                case 0 : game.balas[n].b_xy[3]+=game.balas[n].vel*K; break;
                case 1 : game.balas[n].b_xy[2]-=game.balas[n].vel*K; break;
                case 2 : game.balas[n].b_xy[3]-=game.balas[n].vel*K; break;
                case 3 : game.balas[n].b_xy[2]+=game.balas[n].vel*K; break;
            };
        };

        if(game.balas[n].est >= 2.0) game.balas[n].est+=2.7*K;
        if(game.balas[n].est >= 5.5) game.balas[n].est=0;

        if(((game.balas[n].b_xy[2]>=8.0) || (game.balas[n].b_xy[2]<0.1) ||
            (game.balas[n].b_xy[3]>=8.0) || (game.balas[n].b_xy[3]<0.1))
            &&(game.balas[n].est==1)){
            game.balas[n].est=0; return;};

        if((char)game.balas[n].est==1)
            switch(game.fase.map[game.x_map][game.y_map][(char)game.balas[n].b_xy[2]][(char)game.balas[n].b_xy[3]]){
                case 2 :
                case 3 :
                case 4 :
                case 5 :
                case 6 :
                case 7 :
                case 47:
                case 48:
                case 49:
                case 50:
                case 73: if(game.balas[n].est==1) game.balas[n].est=2;
                        //start_sound(explosion);
                        return;
            };



    }

    void show_mes(String text)
    {
        message = text;
        mes_c=15;
    }

    void acceso(float nx, float ny)
    {

        char acc=0, ba, mx=(char)game.x_map, my=(char)game.y_map;


        if(nx>=8.0){ nx-=8.0; mx+=1; };
        if(nx<0.0){ nx+=8.0; mx-=1; };
        if(ny<0.0){ ny+=8.0; my-=1; };
        if(ny>=8.0){ ny-=8.0; my+=1; };

        if(mx >= 10 || my >= 10) return;

        game.desp=0;
        ba=(char)game.fase.map[mx][my][(char)nx][(char)ny];

        if(ba==1) acc=1;
        if((ba>7) && (ba<47)) acc=1;

        if((ba==10) || (ba==11)) game.desp=4;

        if((ba>51) && (ba<56) && (game.llave[0] != 0)) acc=1;
        if((ba>55) && (ba<60) && (game.llave[1] != 0)) acc=1;
        if((ba>59) && (ba<64) && (game.llave[2] != 0)) acc=1;

        //if(ene_map[(char)nx][(char)ny]>0) acc=0;

        //if(game.GHOST) acc=1;

        if(acc==0) return;

        if(is_item(ba)) {pick_item(ba);
            game.fase.map[game.x_map][game.y_map][(char)nx][(char)ny]=1;};

        game.px=nx; game.py=ny; game.x_map=mx; game.y_map=my;


    }

    boolean is_item(char bb)
    {
        if(((bb>11) && (bb<28)) || ((bb>=38) && (bb<=40))) return(true);
        return(false);
    }
    void pick_item(char object)
    {
        switch(object){
            case 12 : game.armas[0]=1; show_mes("Pistola"); break;
            case 13 : game.armas[1]=1; show_mes("Ametralladora"); break;
            case 14 : game.armas[2]=1; show_mes("Lanzacohetes"); break;
            case 15 : game.armas[3]=1; show_mes("\"Aniquilador\""); break;
            case 16 : game.armas[4]=1; show_mes("\"Dragon\""); break;
            case 17 : game.armas[5]=1; show_mes("\"Infierno\"!"); break;
            case 18 : game.llave[0]=1; show_mes("Llave roja"); break;
            case 19 : game.llave[1]=1; show_mes("Llave amarilla"); break;
            case 20 : game.llave[2]=1; show_mes("Llave azul"); break;
            case 21 : game.p_l+=30; show_mes("Botiquin +30"); if(game.p_l>100) game.p_l=100; break;
            case 22 : game.municion[0]+=20; show_mes("Caja de balas"); break;
            case 23 : game.municion[1]+=4; show_mes("Cohetes"); break;
            case 24 : game.municion[2]+=10; show_mes("Frasco de napalm"); break;
            case 25 : game.p_l=200; show_mes("POCIMA VITAL"); break;
            case 26 : game.escudo=150; show_mes("ESCUDO PROTECTOR"); break;
            case 27 : game.pocima=150; show_mes("FUERZA MISTICA"); break;

            case 39 : game.invi=150; show_mes("INVISIBILIDAD"); break;
            case 38 : all_map(); show_mes("Mapa de la zona"); break;
            case 40 : game.n_secrets++; show_mes("Un area secreta!"); break;

        };

        if (game.municion[0]>199) game.municion[0]=199;
        if (game.municion[1]>99) game.municion[1]=99;
        if (game.municion[2]>299) game.municion[2]=299;
        //start_sound(pickup);

    }

    boolean ene_acceso(float nx, float ny, char n)
    {

        char acc=0, ba, mx=(char)game.x_map, my=(char)game.y_map;

        game.desp=0;
        if(nx >= 8) nx = 7;
        if(ny >= 8) ny = 7;
        ba=(char)game.fase.map[mx][my][(char)nx][(char)ny];

        if(ba==1) acc=1;
        if((ba>7) && (ba<47)) acc=1;

        if((ba==10) || (ba==11)) acc=0;

        if((ba==32) || (ba==35)) acc=0;

        if((ba>51) && (ba<56) && (game.llave[0] != 0)) acc=1;
        if((ba>55) && (ba<60) && (game.llave[1] != 0)) acc=1;
        if((ba>59) && (ba<64) && (game.llave[2] != 0)) acc=1;

        if(nx>=8.0) acc=0;
        if(nx<0.0) acc=0;
        if(ny<0.0) acc=0;
        if(ny>=8.0) acc=0;

        if(acc==0) return false;

        game.ene_datos[n].xy[2]=nx; game.ene_datos[n].xy[3]=ny;

        return true;
    }

    void map_2d()
    {

        int xa, ya, xb, yb, orx=40, ory=20, sx=3*game.S_MAP, sy=2*game.S_MAP;

        orx=(int)(160-((8*sx*game.x_map)+(sx*game.px)));
        ory=(int)(100-((8*sy*game.y_map)+(sy*game.py)));

        //game.shapeRenderer.setProjectionMatrix(game.camera.combined);
        //game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for(xa=0; xa<10; ++xa)
            for(ya=0; ya<10; ++ya)
                for(xb=0; xb<8; ++xb)
                    for(yb=0; yb<8; ++yb)
                        if(game.visto[xa][ya]==1){
                            switch(game.fase.map[xa][ya][xb][yb]){
                                case 0: break;
                                case 10:cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)1); break;
                                case 51 :
                                case 11:cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)56); break;
                                case 8:
                                case 9:
                                case 36:
                                case 37:
                                case 1: cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)17); break;
                                case 30:cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)2); break;
                                case 52:
                                case 53:
                                case 54:
                                case 55:cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)32); break;
                                case 56:
                                case 57:
                                case 58:
                                case 59:cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)71); break;
                                case 60:
                                case 61:
                                case 62:
                                case 63:cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)142); break;

                                default: cuadro(orx+(8*sx*xa)+(sx*xb),ory+(8*sy*ya)+(sy*yb),(char)21); break;
                            };
                        };

        cuadro(orx+(8*sx*game.x_map)+(sx*(char)game.px),ory+(8*sy*game.y_map)+(sy*(char)game.py),game.p_col);

        //if(!game.E_MAP) return;
        for(int j=0; j<game.fase.e_n; ++j)
            cuadro(orx+(8*sx*(char)game.ene_datos[j].xy[0])+(sy*(char)game.ene_datos[j].xy[2]),ory+(8*sy*(char)game.ene_datos[j].xy[1])+(sy*(char)game.ene_datos[j].xy[3]),(char)4);

        //game.shapeRenderer.end();
    }
    void cuadro(int x, int y, char c)
    {
        game.shapeRenderer.setColor(game.palette[c][0], game.palette[c][1], game.palette[c][2], 1.f);
        game.shapeRenderer.rect(Main.GAME_SCREEN_START_X+ x,Main.GAME_SCREEN_HEIGHT - y,3*game.S_MAP, 2*game.S_MAP);
        /*char sx,sy;
        for(sx=0; sx<3*game.S_MAP; ++sx){
            for(sy=0; sy<2*game.S_MAP; ++sy){
                if((x+sx>0) && (x+sx<320) && (y+sy>0) && (y+sy<200))
                    scr[x+sx+(320*(y+sy))]=c;
            };
        };*/
    }

    private void visualizar(SpriteBatch scr, int player, int enemy)
    {

            int xx, yy;
            float ppx=game.px, ppy=game.py;
            char i,g,n;

            int frame = (int)game.frame;

            // Primer barrido : S�lo baldosas

            for(i=0; i<8; ++i){
                for(g=0; g<8; ++g){
                    x = Main.cx -(20*i)+(20*g);
                    y = Main.cy +(10*i)+(10*g);

                    switch(game.fase.map[game.x_map][game.y_map][g][i]){

                        case 1 : tile(scr); break;
                        case 10: game.COPY_BUFFER_1(scr,x-20,y,40,23,game.chr.bad_tile[frame]); break;
                        case 51:
                        case 11: game.COPY_BUFFER_1(scr,x-20,y,40,23,game.chr.mor_tile[frame]); break;

                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23: tile_s(scr,0); break;
                        case 24:
                        case 25:
                        case 26:
                        case 27: tile_s(scr,1); break;

                        case 8 :
                        case 9 :

                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 33:

                        case 36 :
                        case 37 :

                        case 38 :
                        case 39 :

                        case 40 :

                        case 47 :
                        case 48 :
                        case 49 :
                        case 50 :

                        case 52 :
                        case 53 :
                        case 54 :
                        case 55 :
                        case 56 :
                        case 57 :
                        case 58 :
                        case 59 :
                        case 60 :
                        case 61 :
                        case 62 :
                        case 63 :

                        case 70 :
                        case 71 :
                        case 72 :
                        case 73 : tile(scr); break;
                    };
                };
            };

            // Segundo barrido : Objetos y personajes

            for(i=0; i<8; ++i){
                for(g=0; g<8; ++g){
                    x=Main.cx-(20*i)+(20*g);
                    y=Main.cy+(10*i)+(10*g);

                    switch(game.fase.map[game.x_map][game.y_map][g][i]){

                        case 32:
                        case 2 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.wall1); break;
                        case 35:
                        case 3 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.wall1); break;
                        case 4 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.wall2); break;
                        case 5 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.wall2); break;
                        case 6 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.wall3[frame]); break;
                        case 7 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.wall3[frame]); break;
                        case 8 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.door[1]); break;
                        case 9 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.door[0]); break;

                        case 12: game.COPY_BUFFER_1(scr,x-20,y-8,40,27,game.weapon[0][0]); break;
                        case 13: game.COPY_BUFFER_1(scr,x-20,y-8,40,27,game.weapon[0][1]); break;
                        case 14: game.COPY_BUFFER_1(scr,x-20,y-8,40,27,game.weapon[0][2]); break;
                        case 15: game.COPY_BUFFER_1(scr,x-20,y-8,40,27,game.weapon[0][3]); break;
                        case 16: game.COPY_BUFFER_1(scr,x-20,y-8,40,27,game.weapon[0][4]); break;
                        case 17: game.COPY_BUFFER_1(scr,x-20,y-8,40,27,game.weapon[0][5]); break;

                        case 18: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.keys[0]); break;
                        case 19: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.keys[1]); break;
                        case 20: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.keys[2]); break;
                        case 21: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.addings[0]); break;
                        case 22: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.ammo[0]); break;
                        case 23: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.ammo[1]); break;
                        case 24: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.ammo[2+frame]); break;
                        case 25: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.addings[11]); break;
                        case 26: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.addings[1]); break;
                        case 27: game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.addings[5+frame]); break;

                        case 28: game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.epi_end); break;
                        case 29: game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.fas_end); break;
                        case 30: game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.teletrans); break;
                        case 31: game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.secret_trans); break;
                        case 33: if(int_map[g][i] != 0 && game.fase.switches[int_map[g][i]-1].estado==1) game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.boton[1]);
                        else game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.boton[0]); break;

                        case 36 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.door[1]); break;
                        case 37 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.door[0]); break;

                        case 38 : game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.addings[8+frame]); break;
                        case 39 : game.COPY_BUFFER_1(scr,x-12,y-11,24,26,game.addings[2+frame]); break;

                        case 47 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.clos_door[1]); break;
                        case 48 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.clos_door[0]); break;
                        case 49 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.clos_door[1]); break;
                        case 50 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.clos_door[0]); break;

                        case 52 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.key_door1[game.llave[0]][1]); break;
                        case 53 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.key_door1[game.llave[0]][0]); break;
                        case 54 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.key_door1[game.llave[0]][1]); break;
                        case 55 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.key_door1[game.llave[0]][0]); break;
                        case 56 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.key_door2[game.llave[1]][1]); break;
                        case 57 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.key_door2[game.llave[1]][0]); break;
                        case 58 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.key_door2[game.llave[1]][1]); break;
                        case 59 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.key_door2[game.llave[1]][0]); break;
                        case 60 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.key_door3[game.llave[2]][1]); break;
                        case 61 : game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.key_door3[game.llave[2]][0]); break;
                        case 62 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.key_door3[game.llave[2]][1]); break;
                        case 63 : game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.key_door3[game.llave[2]][0]); break;

                        case 70: game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.decor1); break;
                        case 71: game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.decor2); break;
                        case 72: game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.decor3[frame]); break;
                        case 73: game.COPY_BUFFER_1(scr,x-20,y-60,40,79,game.chr.column); break;
                        case 74: game.COPY_BUFFER_2(scr,x-20,y-50,20,70,game.chr.wall1);
                            game.COPY_BUFFER_1(scr,x,y-50,20,70,game.chr.wall1); break;
                    };


                    for(char t=0; t<10; ++t){
                        if((game.balas[t].est!=0) && ((char)game.balas[t].b_xy[0]==game.x_map)
                            && ((char)game.balas[t].b_xy[1]==game.y_map)
                            && ((char)game.balas[t].b_xy[2]==g) &&
                            ((char)game.balas[t].b_xy[3]==i) )
                            show_bullet(scr,t);
                    };

                    if(((char)(game.px)==g) && ((char)(game.py)==i) && (player != 0)){

                        int j=(int)(game.cx-(20*ppy)+(20*ppx));
                        int k=(int)(game.cy+(10*ppy)+(10*ppx)+game.desp-10);
                        if(game.pocima>0){game.COPY_BUFFER_1(scr,j-20,k-40,20,59,game.field[0]);
                            game.COPY_BUFFER_2(scr,j,k-40,20,59,game.field[0]);
                        };
                        if(game.escudo>0){game.COPY_BUFFER_1(scr,j-20,k-40,20,59,game.field[1]);
                            game.COPY_BUFFER_2(scr,j,k-40,20,59,game.field[1]);
                        };
                        if(teleport_state != 0) {
                            ppy=game.py-0.5f;
                            ppx=game.px-0.5f;
                            xx=(int)(game.cx-(20*ppy)+(20*ppx));
                            yy=(int)(game.cy+(10*ppy)+(10*ppx)+game.desp);
                            float xa=xx-20, ya=yy-20;
                            game.Random_Buffer(scr,xa,ya,40,39,game.sol.l_stand[0],1,1,player_fade/10f);
                            game.Random_Buffer(scr,xa,ya-20,40,39,game.sol.b_stand[0],1,1,player_fade/10f);
                        }
                        else if(game.invi > 0f) show_warrior(scr, null,game.px,game.py,game.p_d,game.p_p,(char)game.p_e,game.p_w,game.desp);
                        else show_warrior(scr, game.sol,game.px,game.py,game.p_d,game.p_p,(char)game.p_e,game.p_w,game.desp);
                    };


                    if(enemy != 0){
                        for(int t=0; t<game.fase.e_n; ++t){
                            if(((int)game.ene_datos[t].xy[0]==game.x_map) && ((int)game.ene_datos[t].xy[1]==game.y_map) && ((char)game.ene_datos[t].xy[2]==g) && ((char)game.ene_datos[t].xy[3]==i) ) show_enemy((char)t);
                        };
                };
            };

        }
    }

    void out_var(int x, int y, int var)
    {
        String s = ""+var;
        game.Copytext(game.batch,x,y,s);
    }
    void variables()
    {
        game.Copytext(game.batch,6-4,170,game.p_name);
        if(game.p_l>=0) out_var(19-4,186,game.p_l);
        else out_var(19-4,186,0);
        if(game.llave[0] > 0) game.Copytext(game.batch,60-4,185,"");
        if(game.llave[1] > 0) game.Copytext(game.batch,73-4,185,"");
        if(game.llave[2] > 0) game.Copytext(game.batch,86-4,185,"");

        out_var(288-4,154,game.municion[0]);
        out_var(288-4,170,game.municion[1]);
        out_var(288-4,186,game.municion[2]);

        StringBuilder a = new StringBuilder();
        switch(game.p_w){
            case 1 : a.append((char)202); a.append((char)203); break;
            case 2 : a.append((char)204); a.append((char)205); break;
            case 3 : a.append((char)206); a.append((char)207); break;
            case 4 : a.append((char)208); a.append((char)209); break;
            case 5 : a.append((char)210); a.append((char)211); break;
            case 6 : a.append((char)212); a.append((char)213); break;
            /*case 2 : sprintf(a,"��"); break;
            case 3 : sprintf(a,"��"); break;
            case 4 : sprintf(a,"��"); break;
            case 5 : sprintf(a,"��"); break;
            case 6 : sprintf(a,"��"); break;*/
        };
        if (game.p_w > 0) game.Copytext(game.batch,76-4,169,a.toString());

        if((game.invi>20) || ((game.invi<=20) && (game.invi>0) && ((char)game.frame!=1)))
            game.COPY_BUFFER_1(game.batch,220,0,24,26,game.addings[(int)(2+game.frame)]);

        if((game.pocima>20) || ((game.pocima<=20) && (game.pocima>0) && ((char)game.frame!=1)))
            game.COPY_BUFFER_1(game.batch,295,0,24,26,game.addings[(int)(5+game.frame)]);

        if(game.p_l>100) game.COPY_BUFFER_1(game.batch,270,0,24,26,game.addings[11]);
        if((game.escudo>20) || ((game.escudo<=20) && (game.escudo>0) && ((char)game.frame!=1)))
            game.COPY_BUFFER_1(game.batch,245,0,24,26,game.addings[1]);

    }

    void set_maps()
    {
        int g,i;
        /* Crear mapa con los teleports */
        for(g=0; g<8; ++g)
            for(i=0; i<8; ++i)
                tel_map[g][i]=0;
        for(g=0; g<game.fase.t_n; ++g){
            if((game.fase.teleport[g].origen_xy[0]==game.x_map) && (game.fase.teleport[g].origen_xy[1]==game.y_map))
                tel_map[game.fase.teleport[g].origen_xy[2]][game.fase.teleport[g].origen_xy[3]]=(char)(g+1);
        };

        /* Crear mapa con los interruptores */
        for(g=0; g<8; ++g)
            for(i=0; i<8; ++i)
                int_map[g][i]=0;
        for(g=0; g<game.fase.s_n; ++g){
            if((game.fase.switches[g].activador_xy[0]==game.x_map) && (game.fase.switches[g].activador_xy[1]==game.y_map))
                int_map[game.fase.switches[g].activador_xy[2]][game.fase.switches[g].activador_xy[3]]=(char)(g+1);
        };

        /* Crear mapa con los enemigos */
        for(g=0; g<8; ++g)
            for(i=0; i<8; ++i)
                ene_map[g][i]=0;
        for(g=0; g<game.fase.e_n; ++g){
            if(((char)game.ene_datos[g].xy[0]==game.x_map) && ((char)game.ene_datos[g].xy[1]==game.y_map)
                &&(game.ene_datos[g].life>0))
                ene_map[(char)game.ene_datos[g].xy[2]][(char)game.ene_datos[g].xy[3]]=(char)(g+1);
        };
    }

    void tile(SpriteBatch scr)
    {
        game.COPY_BUFFER_1(scr,x-20,y,40,23,game.chr.tile);
    }

    void tile_s(SpriteBatch scr, int type)
    {

        game.COPY_BUFFER_1(scr,x-20,y,40,23,game.chr.tile);
        game.Shadow_Buffer(scr,x-20,y,40,23,game.sombra,type);
    }

    void show_warrior(SpriteBatch scr, Main.SPRITE fig, float wx, float wy, char dir, char pos, char estado, char arma, char des)
    {
        int xx,yy;
        float ppy,ppx;
        ppy=wy-0.5f;
        ppx=wx-0.5f;
        int j=(int)(Main.cx-(20*ppy)+(20*ppx));
        int k=(int)(Main.cy+(10*ppy)+(10*ppx)+des);

        game.Shadow_Buffer(scr,j-20,k+des,40,23,game.sombra,0);

        if(fig==null)
        {
            INVISIBLE:
            if((fig==null)&&(estado<5)) {
                if (dir == 0) {
                    /*if (pos) Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_walk[0][pos - 1], 0);
                    else Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_stand[0], 0);
                    if ((estado > 0) && (estado < 3))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_pain[0], 0);
                    if (estado == 0) Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_stand[0], 0);
                    if ((estado > 12) && (estado < 15))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_punch[0], 0);*/
                    if (arma > 0) game.COPY_BUFFER_1(scr, j - 36, k - 20, 40, 27, game.weapon[0][arma - 1]);
                    return;
                }
                ;
                if (dir == 1) {
                    /*if (pos) Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_walk[1][pos - 1], 0);
                    else Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_stand[1], 0);*/
                    if (arma > 0) game.COPY_BUFFER_1(scr, j - 36, k - 36, 40, 27, game.weapon[1][arma - 1]);
                    /*if ((estado > 0) && (estado < 3))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_pain[1], 0);
                    if (estado == 0) Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_stand[1], 0);
                    if ((estado == 13) || (estado == 14))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_punch[1], 0);*/
                    return;
                }
                ;
                if (dir == 2) {
                    /*if (pos) Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_walk[1][pos - 1], 1);
                    else Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_stand[1], 1);*/
                    if (arma > 0) game.COPY_BUFFER_2(scr, j - 4, k - 36, 40, 27, game.weapon[1][arma - 1]);
                    /*if ((estado > 0) && (estado < 3))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_pain[1], 1);
                    if (estado == 0) Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_stand[1], 1);
                    if ((estado == 13) || (estado == 14))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_punch[1], 1);*/
                    return;
                }
                ;
                if (dir == 3) {
                    /*if (pos) Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_walk[0][pos - 1], 1);
                    else Puts_Shade(scr, j - 20, k - 20, 40, 39, sol -> l_stand[0], 1);
                    if ((estado > 0) && (estado < 3))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_pain[0], 1);
                    if (estado == 0) Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_stand[0], 1);
                    if ((estado == 13) || (estado == 14))
                        Puts_Shade(scr, j - 20, k - 40, 40, 39, sol -> b_punch[0], 1);*/
                    if (arma > 0) game.COPY_BUFFER_2(scr, j - 4, k - 20, 40, 27, game.weapon[0][arma - 1]);
                    return;
                }
            }
        }
        else if((estado>=5) && (estado<13))
        {
            //MUERTO;
            if(estado==5)
                game.COPY_BUFFER_1(scr,j-20,k-40,40,59,fig.w_falling[0]);
            if(estado==6)
                game.COPY_BUFFER_1(scr,j-20,k-40,40,59,fig.w_falling[1]);
            if(estado==7)
                game.COPY_BUFFER_1(scr,j-20,k-1,60,30,fig.w_ground[0]);
            if(estado==9)
                game.COPY_BUFFER_1(scr,j-20,k-40,40,59,fig.w_falling[2]);
            if(estado==10)
                game.COPY_BUFFER_1(scr,j-20,k-40,40,59,fig.w_falling[3]);
            if(estado==11)
                game.COPY_BUFFER_1(scr,j-20,k-1,60,30,fig.w_ground[1]);
            return;
        }
        else {
            if (dir == 0) {
                if (pos > 0) game.COPY_BUFFER_1(scr, j - 20, k - 20, 40, 39, fig.l_walk[0][pos - 1]);
                else game.COPY_BUFFER_1(scr, j - 20, k - 20, 40, 39, fig.l_stand[0]);
                if ((estado > 0) && (estado < 3))
                    game.COPY_BUFFER_1(scr, j - 20, k - 40, 40, 39, fig.b_pain[0]);
                if (estado == 0) game.COPY_BUFFER_1(scr, j - 20, k - 40, 40, 39, fig.b_stand[0]);
                if ((estado == 13) || (estado == 14))
                    game.COPY_BUFFER_1(scr, j - 20, k - 40, 40, 39, fig.b_punch[0]);
                if (arma > 0) game.COPY_BUFFER_1(scr, j - 36, k - 20, 40, 27, game.weapon[0][arma - 1]);
                return;
            }
            ;
            if (dir == 1) {
                if (pos > 0) game.COPY_BUFFER_1(scr, j - 20, k - 20, 40, 39, fig.l_walk[1][pos - 1]);
                else game.COPY_BUFFER_1(scr, j - 20, k - 20, 40, 39, fig.l_stand[1]);
                if (arma > 0) game.COPY_BUFFER_1(scr, j - 36, k - 36, 40, 27, game.weapon[1][arma - 1]);
                if ((estado > 0) && (estado < 3))
                    game.COPY_BUFFER_1(scr, j - 20, k - 40, 40, 39, fig.b_pain[1]);
                if (estado == 0) game.COPY_BUFFER_1(scr, j - 20, k - 40, 40, 39, fig.b_stand[1]);
                if ((estado == 13) || (estado == 14))
                    game.COPY_BUFFER_1(scr, j - 20, k - 40, 40, 39, fig.b_punch[1]);

                return;
            }
            ;
            if (dir == 2) {
                if (pos > 0) game.COPY_BUFFER_2(scr, j - 20, k - 20, 40, 39, fig.l_walk[1][pos - 1]);
                else game.COPY_BUFFER_2(scr, j - 20, k - 20, 40, 39, fig.l_stand[1]);
                if (arma > 0) game.COPY_BUFFER_2(scr, j - 4, k - 36, 40, 27, game.weapon[1][arma - 1]);
                if ((estado > 0) && (estado < 3))
                    game.COPY_BUFFER_2(scr, j - 20, k - 40, 40, 39, fig.b_pain[1]);
                if (estado == 0) game.COPY_BUFFER_2(scr, j - 20, k - 40, 40, 39, fig.b_stand[1]);
                if ((estado == 13) || (estado == 14))
                    game.COPY_BUFFER_2(scr, j - 20, k - 40, 40, 39, fig.b_punch[1]);
                return;
            }
            ;
            if (dir == 3) {
                if (pos > 0) game.COPY_BUFFER_2(scr, j - 20, k - 20, 40, 39, fig.l_walk[0][pos - 1]);
                else game.COPY_BUFFER_2(scr, j - 20, k - 20, 40, 39, fig.l_stand[0]);
                if ((estado > 0) && (estado < 3))
                    game.COPY_BUFFER_2(scr, j - 20, k - 40, 40, 39, fig.b_pain[0]);
                if (estado == 0) game.COPY_BUFFER_2(scr, j - 20, k - 40, 40, 39, fig.b_stand[0]);
                if ((estado == 13) || (estado == 14))
                    game.COPY_BUFFER_2(scr, j - 20, k - 40, 40, 39, fig.b_punch[0]);
                if (arma > 0) game.COPY_BUFFER_2(scr, j - 4, k - 20, 40, 27, game.weapon[0][arma - 1]);
                return;
            }
            ;
        };
    }

    void show_enemy(char n)
    {
        char dir, weapon, estado, pos, tipo;
        float xx, yy;

        tipo=(char)game.fase.enemies[n].e_t;
        estado=(char)game.ene_datos[n].est;
        pos=(char)game.ene_datos[n].pos;
        dir=(char)game.fase.enemies[n].e_d;
        weapon=(char)game.fase.enemies[n].e_w;
        xx=game.ene_datos[n].xy[2];
        yy=game.ene_datos[n].xy[3];

        if(tipo==0) show_warrior(game.batch, game.trp,xx,yy,dir,pos,estado,weapon, (char) 0);
        if(tipo==1) show_warrior(game.batch, game.ene1,xx,yy,dir,pos,estado,weapon, (char) 0);
        if(tipo==2) show_warrior(game.batch, game.ene2,xx,yy,dir,pos,estado,weapon, (char) 0);
        if(tipo==3) show_warrior(game.batch, null,xx,yy,dir,pos,estado,weapon, (char) 0);
        if(tipo==4) show_warrior(game.batch, game.sol,xx,yy,dir,pos,estado,weapon, (char) 0);

    }

    void show_bullet(SpriteBatch scr, char n)
    {
        float ppy,ppx;
        ppx=(float)game.balas[n].b_xy[2]-0.5f;
        ppy=(float)game.balas[n].b_xy[3]-0.5f;

        int j=(int)(game.cx-(20*ppy)+(20*ppx));
        int k=(int)(game.cy+(10*ppy)+(10*ppx)+game.desp);
        int l=game.balas[n].tipo-1;
        if(game.balas[n].est==1){
            if(game.balas[n].tipo==0) {game.COPY_BUFFER_1(scr,j,k-20,2,1,game.shell); return;};
            if(game.balas[n].dir==0)
                game.COPY_BUFFER_1(scr,j-20,k-26,40,27,game.bullet[l][0][(int)game.frame]);
            if(game.balas[n].dir==1)
                game.COPY_BUFFER_1(scr,j-20,k-26,40,27,game.bullet[l][1][(int)game.frame]);
            if(game.balas[n].dir==2)
                game.COPY_BUFFER_2(scr,j-20,k-26,40,27,game.bullet[l][1][(int)game.frame]);
            if(game.balas[n].dir==3)
                game.COPY_BUFFER_2(scr,j-20,k-26,40,27,game.bullet[l][0][(int)game.frame]);

            game.Shadow_Buffer(scr,j-20,k,40,23,game.sombra,0);
        };
        if((char)game.balas[n].est==2) game.COPY_BUFFER_2(scr,j-20,k-26,40,27,game.explos[game.balas[n].tipo][0]);
        if((char)game.balas[n].est==3) game.COPY_BUFFER_2(scr,j-20,k-26,40,27,game.explos[game.balas[n].tipo][1]);
        if((char)game.balas[n].est==4) game.COPY_BUFFER_2(scr,j-20,k-26,40,27,game.explos[game.balas[n].tipo][2]);

    };


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
