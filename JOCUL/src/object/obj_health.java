package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class obj_health extends SuperObject {
    GamePanel gp;
    public obj_health(GamePanel gp)
    {
        this.gp = gp;
        name = "Health";
        solidArea = new Rectangle(0,0,16,16);
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/hearths/heart.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/hearths/border.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/hearths/background.png"));

        }catch (IOException e)
        {
            e.printStackTrace();
        }

}}
