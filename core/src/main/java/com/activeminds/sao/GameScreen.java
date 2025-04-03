package com.activeminds.sao;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    Main game;
    int x, y;
    ButtonLayout joypad;
    float mes_c = 0f, K;
    String message;
    boolean FIN_DE_FASE, FIN_EPI, SECRET_FASE;

    GameScreen(Main game)
    {
        this.game = game;
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("joypad.json");
        show_mes(game.fase.map_name);
        FIN_EPI = false;
        FIN_DE_FASE = false;
        SECRET_FASE = false;

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // RENDER STEP ========================================

        ScreenUtils.clear(0, 0, 0, 1f);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        if(!game.MAP) visualizar(game.batch, 1,1);
        else map_2d();
        if(mes_c > 0) {game.Copytext(game.batch,5,5,message); };
        game.COPY_BUFFER_1(game.batch,0,166,97,34,game.marca1);
        game.COPY_BUFFER_1(game.batch,269,150,51,50,game.marca2);

        game.batch.end();
        /*
        CLEAR_BUFFER(scr,0);
        if(!MAP)visualizar(1,1);
        else map_2d();
        if(mes_c) {Copytext(scr,5,5,message); mes_c--;};
        COPY_BUFFER_1(scr,0,166,97,34,marca1);
        COPY_BUFFER_1(scr,269,150,51,50,marca2);
        if((p_e>6) && (p_e<13)) Copytext(scr,30,95,"PULSA SPACE PARA REINICIAR");
        variables();

        FLIP_BUFFER(scr);
        */

        joypad.render(game.batch, game.batch);

        K = delta * 2.5f;

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


            /*
            if(escudo>0) escudo-=K;
            if(pocima>0) pocima-=K;
            if(invi>0) invi-=K;

            */
            // MOVER ENEMIGOS, AQUI
            for(int g=0; g<game.fase.e_n; ++g)
                if(((char)game.ene_datos[g].xy[0]==game.x_map) && ((char)game.ene_datos[g].xy[1]==game.y_map)) move_enemy((char)g);
            //set_maps();

            /*
            if(tel_map[x_room][y_room]) teletransporte(tel_map[x_room][y_room]-1);

            if((int_map[x_room][y_room]) && (keymap[57])) conecta_int(int_map[x_room][y_room]-1);

            for(i=0; i<10; ++i)
                if(balas[i].est) move_bullet(i);



            if(kbhit())
                key=toupper(getch());

            if((keymap[57]) && (p_l<=0)) goto GAME_OVER;*/

            if ((game.p_e >= 5) && (game.p_e < 13)) {
                //NO_MUEVE;
            } else {
                game.p_p = 0;
                if (joypad.isPressed("North")) {
                    //if (!keymap[56])
                    game.p_d = 2;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px, game.py - K);
                } else if (joypad.isPressed("South")) {
                    //if (!keymap[56])
                    game.p_d = 0;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px, game.py + K);
                } else if (joypad.isPressed("West")) {
                    //if (!keymap[56])
                    game.p_d = 1;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px - K, game.py);
                } else if (joypad.isPressed("East")) {
                    //if (!keymap[56])
                    game.p_d = 3;
                    game.p_p = (char) (game.frame + 1);
                    acceso(game.px + K, game.py);
                }
                ;
                //TECLA_REC:
                /*if (keymap[29]) p_disparo();
                if ((keymap[27]) && (S_MAP < 5)) {
                    S_MAP++;
                    vaciarbuffer();
                }
                ;
                if ((keymap[53]) && (S_MAP > 1)) {
                    S_MAP--;
                    vaciarbuffer();
                }*/
                ;
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
            };
            if(keymap[15]) {MAP=1-MAP; vaciarbuffer(); keymap[15]=0;};

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
            };

            if(p_e==13) p_punch();

            if((keymap[27]) && (K>0.01)) {K-=0.03; keymap[27]=0; };
            if((keymap[53]) && (K<1.0)) {K+=0.03; keymap[53]=0; };

            vaciarbuffer();

            if(p_e>=14) p_e=0;
            if((p_e>0) && (p_e<5)) p_e-=K;
            if((p_e>4) && (p_e<7)) p_e+=K;
            if((p_e>8) && (p_e<11)) p_e+=K;
            if((p_e>12) && (p_e<14)) p_e+=K;

            // Da�o al jugador

            if(p_e==13) p_punch();
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
                        FIN_DE_FASE = true;
                    case 31:
                        SECRET_FASE = true;
                    case 28:
                        FIN_EPI = true;

                }
                ;

                /*
                for(h=0; h<10; h++){
                    if((balas[h].est==1) && ((char)balas[h].b_xy[2]==(char)px)
                        && ((char)balas[h].b_xy[3]==(char)py)
                        && ((char)balas[h].b_xy[0]==x_map)
                        && ((char)balas[h].b_xy[1]==y_map)){
                        balas[h].est=2; start_sound(explosion);
                        p_e=2;
                        p_l-=balas[h].fuerza;
                    };
                };


                if(p_l<1) {p_e=5; sprintf(message,"%s ha muerto.",p_name);
                    mes_c=25; start_sound(dying);};
                if(p_l<-4) {p_e=9; sprintf(message,"%s ha sido destruido.",p_name);
                    mes_c=25; start_sound(falling);};*/

            }
            ;

            /*
            // Da�o a un enemigo
            for(g=0; g<8; ++g){
                for(i=0; i<8; ++i){
                    for(h=0; h<10; ++h){
                        for(l=0; l<fase.e_n; ++l){
                            if(((char)ene_datos[l].xy[0]==x_map) &&
                                ((char)ene_datos[l].xy[1]==y_map) &&
                                ((char)ene_datos[l].xy[2]==g) &&
                                ((char)ene_datos[l].xy[3]==i) &&
                                ((char)balas[h].b_xy[0]==x_map) &&
                                ((char)balas[h].b_xy[1]==y_map) &&
                                ((char)balas[h].b_xy[2]==g) &&
                                ((char)balas[h].b_xy[3]==i) &&
                                ((char)balas[h].est==1))
                                enemy_impact(l,h);
                        };
                    };
                };
            };

            // COMPROBACION DEL TIEMPO TRANSCURRIDO

            asm{
                mov ah , 0
                int 1Ah
            };
            m2=_DX;
            K=(float)((m2-m1)*0.25);

        }while(key!='\x1B');
        */
        }
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

            //if(random(20-(4*DIF))==0) e_disparo(n);

        }

	    if(N_DIR)
        {
            game.fase.enemies[n].e_d=(char)Math.random() % 4;
        }
        //NO_MOVE:
        //if(est==13) e_punch(n);
        if(est>=14) game.ene_datos[n].est=0;
        if((est>0) && (est<5)) game.ene_datos[n].est-=K;
        if((est>4) && (est<7)) game.ene_datos[n].est+=K;
        if((est>8) && (est<11)) game.ene_datos[n].est+=K;
        if((est>12) && (est<14)) game.ene_datos[n].est+=K;

    }

    void show_mes(String text)
    {
        message = text;
        mes_c=35;
    }

    void acceso(float nx, float ny)
    {

        char acc=0, ba, mx=(char)game.x_map, my=(char)game.y_map;


        if(nx>=8.0){ nx-=8.0; mx+=1; };
        if(nx<0.0){ nx+=8.0; mx-=1; };
        if(ny<0.0){ ny+=8.0; my-=1; };
        if(ny>=8.0){ ny-=8.0; my+=1; };

        game.desp=0;
        ba=(char)game.fase.map[mx][my][(char)nx][(char)ny];

        if(ba==1) acc=1;
        if((ba>7) && (ba<47)) acc=1;

        if((ba==10) || (ba==11)) game.desp=4;

        if((ba>51) && (ba<56) && (game.llave[0] != 0)) acc=1;
        if((ba>55) && (ba<60) && (game.llave[1] != 0)) acc=1;
        if((ba>59) && (ba<64) && (game.llave[2] != 0)) acc=1;

        //if(game.ene_map[(char)nx][(char)ny]>0) acc=0;

        //if(game.GHOST) acc=1;

        if(acc==0) return;

        //if(is_item(ba)) {pick_item(ba);
        //    fase.map[x_map][y_map][(char)nx][(char)ny]=1;};

        game.px=nx; game.py=ny; game.x_map=mx; game.y_map=my;


    }

    boolean ene_acceso(float nx, float ny, char n)
    {

        char acc=0, ba, mx=(char)game.x_map, my=(char)game.y_map;

        game.desp=0;
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

    private void map_2d() {
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
                        /*case 33: if(game.fase.switches[game.int_map[g][i]-1].estado==1) game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.boton[1]);
                        else game.COPY_BUFFER_1(scr,x-20,y-20,40,39,game.chr.boton[0]); break;*/

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

                    /*
                    for(t=0; t<10; ++t){
                        if((balas[t].est!=0) && ((char)balas[t].b_xy[0]==x_map)
                            && ((char)balas[t].b_xy[1]==y_map)
                            && ((char)balas[t].b_xy[2]==g) &&
                            ((char)balas[t].b_xy[3]==i) )
                            show_bullet(t);
                    };*/

                    if(((char)(game.px)==g) && ((char)(game.py)==i) && (player != 0)){

                        int j=(int)(game.cx-(20*ppy)+(20*ppx));
                        int k=(int)(game.cy+(10*ppy)+(10*ppx)+game.desp-10);
                        /*if(pocima>0){COPY_BUFFER_1(scr,j-20,k-40,20,59,field[0]);
                            COPY_BUFFER_2(scr,j,k-40,20,59,field[0]);
                        };
                        if(escudo>0){COPY_BUFFER_1(scr,j-20,k-40,20,59,field[1]);
                            COPY_BUFFER_2(scr,j,k-40,20,59,field[1]);
                        };*/
                        if(game.invi > 0f) show_warrior(scr, null,game.px,game.py,game.p_d,game.p_p,game.p_e,game.p_w,game.desp);
                        else show_warrior(scr, game.sol,game.px,game.py,game.p_d,game.p_p,game.p_e,game.p_w,game.desp);
                    };


                    if(enemy != 0){
                        for(int t=0; t<game.fase.e_n; ++t){
                            if(((int)game.ene_datos[t].xy[0]==game.x_map) && ((int)game.ene_datos[t].xy[1]==game.y_map) && ((char)game.ene_datos[t].xy[2]==g) && ((char)game.ene_datos[t].xy[3]==i) ) show_enemy((char)t);
                        };
                };
            };

        }
    }

    void tile(SpriteBatch scr)
    {
        game.COPY_BUFFER_1(scr,x-20,y,40,23,game.chr.tile);
    }

    void tile_s(SpriteBatch scr, int type)
    {

        game.COPY_BUFFER_1(scr,x-20,y,40,23,game.chr.tile);
        //Shadow_Buffer(scr,x-20,y,40,23,sombra,type);
    }

    void show_warrior(SpriteBatch scr, Main.SPRITE fig, float wx, float wy, char dir, char pos, char estado, char arma, char des)
    {
        int xx,yy;
        float ppy,ppx;
        ppy=wy-0.5f;
        ppx=wx-0.5f;
        int j=(int)(Main.cx-(20*ppy)+(20*ppx));
        int k=(int)(Main.cy+(10*ppy)+(10*ppx)+des);

        //Shadow_Buffer(scr,j-20,k+des,40,23,sombra,0);

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
