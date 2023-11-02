package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class obj_pot extends SuperObject {
    GamePanel gp;
    public obj_pot(GamePanel gp)  {

        this.gp = gp;

        name = "pot";

        try {
            potImg = read(getClass().getResourceAsStream("/objects/potion.png"));
        }catch (IOException e)
        {e.printStackTrace();}
    }

    public void use(Entity entity)
    {
        gp.player.Health += 1;
    }
}
