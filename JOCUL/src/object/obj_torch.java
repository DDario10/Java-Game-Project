package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class obj_torch extends SuperObject {

    GamePanel gp;
    public obj_torch(GamePanel gp)
    {

        this.gp = gp;
        name = "Key";
        solidArea = new Rectangle(0,0,48,48);
        try
        {
            imgs[0] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_1.png"));
            imgs[1] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_2.png"));
            imgs[2] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_3.png"));
            imgs[3] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_4.png"));
            imgs[4] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_5.png"));
            imgs[5] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_6.png"));
            imgs[6] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_7.png"));
            imgs[7] = ImageIO.read(getClass().getResourceAsStream("/objects/torch_8.png"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
