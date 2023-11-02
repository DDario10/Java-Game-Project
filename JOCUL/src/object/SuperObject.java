package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image, image2, image3,potImg;
    public BufferedImage[] imgs = new BufferedImage[8];
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp)
    {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

           /* if(worldX > gp.player.worldX - gp.player.screenX &&
            worldX < gp.player.worldX + gp.player.screenX &&
                    worldY > gp.player.worldY - gp.player.screenY &&
                    worldY < gp.player.worldY + gp.player.screenY)*/
        g2.drawImage(image,screenX, screenY, gp.tileSize/2, gp.tileSize/2 , null);

    }

    public void use(){}
    public void drawTeleporter(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Intervalul de timp intre care se schimba imaginile - in milisecunde
        int interval = 120;

        int currentImageIndex = (int) ((System.currentTimeMillis() / interval) % imgs.length);



        g2.drawImage(imgs[currentImageIndex], screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
