package entity;

import main.GamePanel;
import object.obj_pot;


import java.awt.*;
import java.util.Random;

public class SKELETON extends Entity  {
    public SKELETON(GamePanel gp)
    {
        super(gp);
        type = 2;
        name = "skeleton";
        direction = "still";
        speed = 1;
        eScale = 10;
        maxHealth = 2;
        Health = maxHealth;

        solidArea = new Rectangle();
        solidArea.x = gp.tileSize-7;
        solidArea.y = (gp.tileSize / 3)*2 + gp.tileSize/2 -5 ;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = (gp.tileSize/4)*2;
        solidArea.height = (gp.tileSize)/3;

        SKwalk = getEntityImg(2,4,150,150,"/Skeleton/Walk.png");
        SKstill = getEntityImg(2,4,150,150,"/Skeleton/Idle.png");
        SKattack = getEntityImg(1,6,150,150,"/Skeleton/Attack3.png");
        Dies = getEntityImg(1,4,150,150,"/Skeleton/Death.png");
    }
    public void checkDrop()
    {
        int i = new Random().nextInt(10) + 1;

        if( i < 5)
        {
            gp.player.Health += 1;
        }
    }


    //public void visit(SKELETON npc) {}

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
