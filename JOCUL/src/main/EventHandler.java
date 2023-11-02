package main;

import java.awt.*;
import java.util.Random;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    int prevEvX,prevEvY;
    boolean canTouch = true;
    public EventHandler(GamePanel  gp)
    {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        int map = 0;

        while(map < gp.maxMap && col< gp.maxWorldCol && row < gp.maxWorldRow)
        {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 0;
            eventRect[map][col][row].y = 0;
            eventRect[map][col][row].width = gp.tileSize;
            eventRect[map][col][row].height = gp.tileSize;
            eventRect[map][col][row].eventRdefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRdefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;
                if(row == gp.maxWorldRow)
                {
                    row = 0;
                    map++;
                }
            }

        }

    }
    public void checkEvent()
    {
        //check if u went on ther tile
        int xDist = Math.abs(gp.player.worldX - prevEvX);
        int yDist = Math.abs(gp.player.worldY - prevEvY);
        int dist = Math.max(xDist,yDist);
        if(dist > gp.tileSize)
        {
            canTouch = true;
        }
        if(canTouch == true)
        {
            //Go to lvl 2
            if(hit(0,38,20)==true)changeMap(1,3,3);
            //Go to lvl 3
            if(hit(1,14,14)==true)changeMap(2,5,3);
            if(hit(1,24,28)==true)changeMap(1,14,3);
            if(hit(1,24,29)==true)changeMap(1,14,3);
            if(hit(1,24,30)==true)changeMap(1,14,3);

            //LVL 3
            if(hit(2,42,24)==true)changeMap(2,42,18);
            if(hit(2,43,24)==true)changeMap(2,43,18);

            //Won the game
           // if(hit(2,42,24)==true)changeMap(1,42,18);



        }
    }

    public boolean hit(int map,int col,int row)
    {
        boolean hit = false;

        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                {
                    hit = true;

                    prevEvX = gp.player.worldX;
                    prevEvY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;

            eventRect[map][col][row].x = eventRect[map][col][row].eventRdefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRdefaultY;
        }
        return hit;


    }

    public void changeMap(int map,int x,int y)
    {
        gp.currentMap = map;
        gp.player.worldX =gp.tileSize *  x;
        gp.player.worldY = gp.tileSize * y;
        prevEvY = gp.player.worldY;
        prevEvX = gp.player.worldX;
        canTouch = false;
    }
    public void damageArea(int col,int row)  {


           if(gp.player.Health > 0)
            gp.player.Health -= 1;
            //eventRect[col][row].eventDone = true;
        canTouch =false;
        //if(gp.player.Health <= 0 ) System.exit(0);


    }

    public void healingArea()
    {


        if(gp.keyH.isEnterPressed() == true && gp.player.Health < gp.player.maxHealth)
        {
            gp.player.Health += 1;
        }
       gp.keyH.setEnterPressed(false);
    }



}
