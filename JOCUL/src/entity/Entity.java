package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//clasa abstracta
public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public int eScale = 1;
    public boolean invincible = false;
    public int invincible_cnt = 0;
    public int type;
    public int attackDamage = 1;
    public int value;
    public int type_pickup = 5;
    public BufferedImage[] bg = new BufferedImage[1];
   public BufferedImage[] attackR = new BufferedImage[15];

   public BufferedImage[] run = new BufferedImage[25];
   public BufferedImage[] still = new BufferedImage[6];
   public BufferedImage[] slide = new BufferedImage[3];
   public BufferedImage[] SKwalk,Dwalk = new BufferedImage[9];
   public BufferedImage[] SKstill,Dstill = new BufferedImage[9];
   public BufferedImage[] SKattack,Dattack = new BufferedImage[13];
   public BufferedImage[] Dies,DDies = new BufferedImage[4];
   public BufferedImage potImg ;

   /*public BufferedImage up0,up1,up2,up3,up4,up5,
            down0,down1,down2,down3,down4,down5,
            left0,left1,left2,left3,left4,left5,
            right0,right1,right2,right3,right4,right5,
            still0,still1,still2,still3,still4,still5,
    attack0,attack1,attack2,attack3,attack4,attack5,
    slide0, slide1, slide2, slide3, slide4, slide5;*/

    public String name;
    public String direction;
    public String lastDirection;
    public int actionCounter = 0;
    int dyingCnt = 0;

    public int spriteCounter = 0;
    public int hpBarCnt = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public Rectangle weaponArea;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int attackDir = 0;
    public int attackcnt = 0;
    public int solidAreaDefaultX, solidAreaDefaultY,weaponAreaDefaultX,weaponAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }

    //character status
    public int maxHealth;
    public int Health;
    public boolean attacking;
     public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    public int dyingTimer = 120;

    public void setAction()
    {
    }
    public void checkDrop()
    {}

    public void update() {
    actionCounter++;

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        boolean contactP =  gp.cChecker.checkPlayer(this);
        if(this.type == 2 && contactP == true)
        {
            if(gp.player.invincible == false)
            {
                //dmg
                gp.player.Health -= 1;
                gp.player.invincible = true;
            }
        }

        if (collisionOn == false && dying == false) {
            switch (direction) {


                case "left": worldX -= speed;break;
                case "right": worldX += speed;break;
                case "up": worldY -= speed;break;
                case "down": worldY += speed;break;

            }

       }



        spriteCounter++;
        if (spriteCounter > 7) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 4;
            } else if (spriteNum == 4) {
                spriteNum = 5;
            } else if (spriteNum == 5) {
                spriteNum = 6;
            } else if (spriteNum == 6) {
                spriteNum = 1;

            }
            spriteCounter = 0;
        }
        if(invincible == true)
        {
            invincible_cnt ++;
            if(invincible_cnt > 30)
            {
                invincible = false;
                invincible_cnt = 0;
            }
        }

    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

           /*if(worldX > gp.player.worldX - gp.player.screenX &&
            worldX < gp.player.worldX + gp.player.screenX &&
                    worldY > gp.player.worldY - gp.player.screenY &&
                    worldY < gp.player.worldY + gp.player.screenY)*/

        if(name == "skeleton"){
            switch (direction) {
                case "die":
                    if (spriteNum == 1)
                        image = Dies[0];
                    if (spriteNum == 2)
                        image = Dies[1];
                    if (spriteNum == 3)
                        image = Dies[2];
                    if (spriteNum == 4)
                        image = Dies[3];
                case "still":
                    if (spriteNum == 1)
                        image = SKstill[0];
                    if (spriteNum == 2)
                        image = SKstill[1];
                    if (spriteNum == 3)
                        image = SKstill[2];
                    if (spriteNum == 4)
                        image = SKstill[3];
                    if (spriteNum == 5)
                        image = SKstill[1];
                    if (spriteNum == 6)
                        image = SKstill[2];
                    break;
                case "right", "up":
                    if (spriteNum == 1)
                        image = SKwalk[0];
                    if (spriteNum == 2)
                        image = SKwalk[1];
                    if (spriteNum == 3)
                        image = SKwalk[2];
                    if (spriteNum == 4)
                        image = SKwalk[3];
                    if (spriteNum == 5)
                        image = SKwalk[1];
                    if (spriteNum == 6)
                        image = SKwalk[2];
                    break;

                case "left", "down":
                    if (spriteNum == 1)
                        image = SKwalk[4];
                    if (spriteNum == 2)
                        image = SKwalk[5];
                    if (spriteNum == 3)
                        image = SKwalk[6];
                    if (spriteNum == 4)
                        image = SKwalk[7];
                    if (spriteNum == 5)
                        image = SKwalk[5];
                    if (spriteNum == 6)
                        image = SKwalk[6];
                    break;

            }}
        //DEMON SPRITES

        else {
            switch (direction) {
                case "die":
                    if (spriteNum == 1)
                        image = DDies[0];
                    if (spriteNum == 2)
                        image = DDies[1];
                    if (spriteNum == 3)
                        image = DDies[2];
                    if (spriteNum == 4)
                        image = DDies[3];
                case "still":
                    if (spriteNum == 1)
                        image = Dstill[0];
                    if (spriteNum == 2)
                        image = Dstill[1];
                    if (spriteNum == 3)
                        image = Dstill[2];
                    if (spriteNum == 4)
                        image = Dstill[3];
                    if (spriteNum == 5)
                        image = Dstill[1];
                    if (spriteNum == 6)
                        image = Dstill[2];
                    break;
                case "right", "up":
                    if (spriteNum == 1)
                        image = Dwalk[0];
                    if (spriteNum == 2)
                        image = Dwalk[1];
                    if (spriteNum == 3)
                        image = Dwalk[2];
                    if (spriteNum == 4)
                        image = Dwalk[3];
                    if (spriteNum == 5)
                        image = Dwalk[1];
                    if (spriteNum == 6)
                        image = Dwalk[2];
                    break;

                case "left", "down":
                    if (spriteNum == 1)
                        image = Dwalk[4];
                    if (spriteNum == 2)
                        image = Dwalk[5];
                    if (spriteNum == 3)
                        image = Dwalk[6];
                    if (spriteNum == 4)
                        image = Dwalk[7];
                    if (spriteNum == 5)
                        image = Dwalk[5];
                    if (spriteNum == 6)
                        image = Dwalk[6];
                    break;


            }}




        if(invincible == true)
        {
            //draw transparent when hit
            //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            hpBarOn = true;
            hpBarCnt = 0;
        }
        //Monster HP
        if(type == 2 && hpBarOn == true) {

            double oneScale = (double)gp.tileSize / maxHealth;
            double hpBarVal = oneScale * Health;

            g2.setColor(new Color(35,35,35));
            g2.fillRect(screenX + 28 , screenY + 16, gp.tileSize+2, 7);

            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX + 29 , screenY + 17, (int)hpBarVal, 5);
            //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            hpBarCnt++;
            if(hpBarCnt > 600){hpBarCnt = 0; hpBarOn = false;}
        }


         if (dying == true)
        {
            dyingAnimation(g2);
        }

           if(alive == true && dying == false)
               if(name == "skeleton")
               g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);
                else if(name == "demon")
                   g2.drawImage(image, screenX, screenY, gp.tileSize * 4, gp.tileSize * 4, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//            g2.setColor(Color.white);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);


    }

    public void dyingAnimation(Graphics2D g2)
    {
        dyingCnt++;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(dyingCnt<=25)
        {
            if(name == "skeleton")
            g2.drawImage(Dies[0],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
            else if(name == "demon")
                g2.drawImage(DDies[0],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
        }
        if(dyingCnt>25 && dyingCnt<=50)
        {
            if(name == "skeleton")
                g2.drawImage(Dies[1],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
            else if(name == "demon")
                g2.drawImage(DDies[1],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
        }
        if(dyingCnt>50 && dyingCnt<=75)
        {
            if(name == "skeleton")
                g2.drawImage(Dies[2],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
            else if(name == "demon")
                g2.drawImage(DDies[2],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
        }
        if(dyingCnt>75 && dyingCnt<=300)
        {
            if(name == "skeleton")
                g2.drawImage(Dies[3],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
            else if(name == "demon")
                g2.drawImage(DDies[3],screenX,screenY,gp.tileSize*2,gp.tileSize*2,null);
        }
        if(dyingCnt > 300)
        {
            dying = false;
            alive = false;
        }
    }
    public BufferedImage[] getEntityImg(int rows,int cols,int tw,int th, String SpriteLocation)
    {
        BufferedImage [] playerImage = new BufferedImage[rows * cols];
        try {
            BufferedImage playerSheet = ImageIO.read(getClass().getResourceAsStream(SpriteLocation));

            //BufferedImage [] playerImage = new BufferedImage[7];
            int numRows = rows;
            int numCols = cols;

            int tileWidth = tw ;
            int tileHeight = th;



            for(int row = 0; row < numRows; row++) {
                for(int col = 0; col < numCols; col++) {
                    int tileIndex = row * numCols + col;
                    int x = col * tileWidth;
                    int y = row * tileHeight;


                    playerImage[tileIndex] = playerSheet.getSubimage(x, y, tileWidth, tileHeight);

                    //if (tileIndex == 48) {
                    //playerImage[tileIndex].collision = true;
                    //}
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerImage;
    }
}
