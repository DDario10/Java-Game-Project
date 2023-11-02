package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class obj_chest extends SuperObject{

    GamePanel gp;
    public obj_chest(GamePanel gp)
    {
        this.gp = gp;
        name = "Key";
        solidArea = new Rectangle(0,0,16,16);
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest_closed.png"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        collision = true;
        //solidArea.x
        //solidArea.y
    }
}
