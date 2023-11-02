package main;

import object.SuperObject;
import object.obj_health;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage hearth,healthbg,healthborder;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    int substate = 0;

    public int commandNum = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp)
    {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        //HUD
        SuperObject health = new obj_health(gp);
        hearth = health.image;
        healthborder = health.image2;
        healthbg = health.image3;
    }

    public void showMessage(String text)
    {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //title state
        if(gp.gameState == gp.titleState)
        {
             drawTitleScreen();
        }

        //play state
        if(gp.gameState == gp.playState)
        {
            //tbd
            drawPlayerLife();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState)
        {
            //tbd
            drawPlayerLife();
            drawPauseScreen();

        }
        // options state
        if(gp.gameState == gp.optionsState)
        {
            drawOptionScreen();
        }
        //GAME OVER
        if(gp.gameState == gp.gameOverState)
        {
            gameOverScreen();
        }
    }
    public void gameOverScreen()
    {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x,y;
        String txt;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        txt = "GAME OVER";
        g2.setColor(Color.black);
        x = getXforCenteredText(txt);
        y = gp.tileSize*4;
        g2.drawString(txt,x,y);

        g2.setColor(Color.white);
        g2.drawString(txt,x-3,y-3);

        //retry
        g2.setFont(g2.getFont().deriveFont(60f));
        txt = "Try Again";
        x = getXforCenteredText(txt);
        y += gp.tileSize * 4;
        g2.drawString(txt,x-3,y-3);
        if(commandNum == 0)
        {
            g2.drawString(">", x- 40,y);
        }

        //back title scr
        txt = "Quit";
        x = getXforCenteredText(txt);
        y += 80;
        g2.drawString(txt,x-3,y-3);
        if(commandNum == 1)
        {
            g2.drawString(">", x- 40,y);
        }

    }
    public void drawPlayerLife()
    {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        //max health

        while(i<gp.player.maxHealth)
        {
            g2.drawImage(healthbg,x,y,null);
            g2.drawImage(healthborder,x,y,null);

            i++;
            x += gp.tileSize/2;
        }
        //rs
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //current health
        while (i<gp.player.Health)
        {
            g2.drawImage(hearth,x,y,null);
            g2.drawImage(healthborder,x,y,null);
            i++;
            x += gp.tileSize/2;
        }

    }

    public void drawTitleScreen()
    {
        //title name
        g2.setColor(new Color(120,80,100));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
        String text = "The DawnBringer";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        //shadow
        g2.setColor(Color.black);
        g2.drawString(text,x+3,y+3);
        //main color
        g2.setColor(Color.CYAN);
        g2.drawString(text,x,y);

        //image
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize * 2 - (gp.tileSize*2);
        g2.drawImage(gp.player.attackR[1],x,y,gp.tileSize*2, gp.tileSize*2,null );

        //menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
        text = "New Game";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);

        if(commandNum == 0)
        {
            g2.drawString(">",x - gp.tileSize,y);
            g2.drawString(text,x-1,y-1);
        }



        text = "Load Game";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);

        if(commandNum == 1)
        {
            g2.drawString(">",x - gp.tileSize,y);
            g2.drawString(text,x-1,y-1);
        }

        text = "Exit Game";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);

        if(commandNum == 2)
        {
            g2.drawString(">",x - gp.tileSize,y);
            g2.drawString(text,x-1,y-1);
        }
    }
    public void drawOptionScreen()
    {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //sub window
        int frameX = gp.tileSize * 4;
        int frameY= gp.tileSize;
        int frameW= gp.tileSize * 8;
        int frameH = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameW,frameH);

        switch (substate)
        {
            case 0:
                options_top(frameX,frameY);
                break;
            case 1:
                optionsCtrl(frameX,frameY);
                break;
            case 2:
                confirmExit(frameX,frameY);
                break;
        }
    }
    public void options_top(int fx,int fy)
    {
        int txtX;
        int txtY;
        //title
        String txt = "OPTIONS";
        txtX = getXforCenteredText(txt);
        txtY = fy + gp.tileSize;
        g2.drawString(txt,txtX,txtY);
        //Fullscreen
        txtX = fx + gp.tileSize;
        txtY += gp.tileSize ;
        g2.drawString("Display settings",txtX,txtY);
        if(commandNum == 0)
        {g2.drawString(">", txtX-25,txtY);}

        //controls
        txtX = fx + gp.tileSize;
        txtY += gp.tileSize ;
        g2.drawString("Controls",txtX,txtY);
        if(commandNum == 1)
        {g2.drawString(">", txtX-25,txtY);
        if(gp.keyH.isEnterPressed() == true)
        {
            substate = 1;
            commandNum = 1;
        }
        }

        //save game
        txtX = fx + gp.tileSize;
        txtY += gp.tileSize ;
        g2.drawString("Save Game",txtX,txtY);
        if(commandNum == 2)
        {g2.drawString(">", txtX-25,txtY);}

        //exit game
        txtX = fx + gp.tileSize;
        txtY += gp.tileSize ;
        g2.drawString("Exit Game",txtX,txtY);
        if(commandNum == 3)
        {g2.drawString(">", txtX-25,txtY);
        if(gp.keyH.isEnterPressed() == true)
        {
            substate = 2;
            commandNum = 0;
        }
        }

        //back
        txtX = fx + gp.tileSize*3;
        txtY += gp.tileSize *2;
        g2.drawString("Resume Game",txtX-55,txtY);
        if(commandNum == 4)
        {g2.drawString(">", txtX-80,txtY);
        if(gp.keyH.isEnterPressed() == true)
        {
           gp.gameState = gp.playState;
           commandNum = 0;
        }
        }
        //gp.config.saveConfig();
    }
    public void drawPauseScreen(){
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }
    public void drawSubWindow(int fx,int fy,int fw,int fh)
    {
//
        g2.setColor(new Color(0,0,0));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2.fillRoundRect(fx,fy,fw,fh,35,35);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setColor(new Color(150,120,0));
        g2.drawRoundRect(fx,fy,fw,fh,35,35);
    }
    public void optionsCtrl(int fx,int fy)
    {
        int txtx;
        int txty;

        //Title
        String txt = "Controls";
        txtx =  getXforCenteredText(txt);
        txty = fy + gp.tileSize;
        g2.drawString(txt,txtx,txty);

        txtx = fx + gp.tileSize;
        txty += gp.tileSize;
        g2.drawString("move: ", txtx,txty); txty += gp.tileSize;
        g2.drawString("slide: ", txtx,txty); txty += gp.tileSize;
        g2.drawString("attack: ", txtx,txty); txty += gp.tileSize;
        g2.drawString("interact: ", txtx,txty); txty += gp.tileSize;
        g2.drawString("pause: ", txtx,txty); txty += gp.tileSize;
        g2.drawString("options menu: ", txtx,txty); txty += gp.tileSize;


        txtx = fx + gp.tileSize*6;
        txty = fy + gp.tileSize*2;
        g2.setFont(g2.getFont().deriveFont(15F));
        g2.drawString("WASD", txtx,txty); txty += gp.tileSize;
        g2.drawString("SPACEBAR", txtx,txty); txty += gp.tileSize;
        g2.drawString("K", txtx,txty); txty += gp.tileSize;
        g2.drawString("ENTER", txtx,txty); txty += gp.tileSize;
        g2.drawString("P", txtx,txty); txty += gp.tileSize;
        g2.drawString("ESC", txtx,txty); txty += gp.tileSize;


        //back
        txtx = fx + gp.tileSize;
        txty = fy + gp.tileSize * 9;
        g2.setFont(g2.getFont().deriveFont(32F));
        g2.drawString("Back", txtx,txty);
        if(commandNum == 0)
        {
            g2.drawString(">",txtx - 25,txty);
            if(gp.keyH.isEnterPressed() == true)
            {
                substate = 0;
            }

        }


    }
    public void confirmExit(int fx,int fy)
    {
//        int txtx = fx + gp.tileSize;
//        int txty = fy + gp.tileSize*3;
//
//        String txt = "EXIT GAME / RETURN TO TILTLE SCREEN";
        System.exit(0);
    }
    public int getXforCenteredText(String text)
    {
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - lenght/2;
        return x;
    }
}
