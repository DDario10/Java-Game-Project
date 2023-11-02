package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Enemy1 extends Entity{
    public Enemy1(GamePanel gp)
    {
        super(gp);
        type = 2;
        name = "demon";
        direction = "still";
        speed = 1;
        eScale = 10;
        maxHealth = 5;
        Health = maxHealth;

        solidArea = new Rectangle();
        solidArea.x = gp.tileSize*2 - 16 ;
        solidArea.y = gp.tileSize + 30 ;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 16;
        solidArea.height = gp.tileSize - 2 ;

        Dwalk = getEntityImg(2,4,128,128,"/Demon/DemonWalk.png");
        Dstill = getEntityImg(2,4,128,128,"/Demon/DemonIdle.png");
        Dattack = getEntityImg(2,4,128,128,"/Demon/DemonAttack.png");
        DDies = getEntityImg(1,4,128,128,"/Demon/DemonDeath.png");
    }


    public void setAction()
    {
        //actionCounter++;
        if(actionCounter%50 == 0 ){

            Random random = new Random();
            int i = random.nextInt(250) + 1; //nr <100 >1
            if(collisionOn == true ) {
                // i = random.nextInt(200)+1;
                direction = turnAround(direction);
            }
            if(i <= 25) {direction ="right";}
            if(i>25 && i <= 50) {direction = "left";}
            if(i>50 && i <= 75){ direction = "still";}
            if(i>75 && i<=100){direction = "up";}
            if(i>100 && i<=125){direction = "down";}


        }
        //System.out.println(actionCounter);
    }
    public String turnAround(String dir)
    {
        if(dir == "left") return "right";
        if(dir == "right") return "left";
        if(dir == "up") return "down";
        if(dir == "down") return "up";

        return "still";
    }
}
