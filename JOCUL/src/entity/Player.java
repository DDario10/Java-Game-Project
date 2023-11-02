package entity;

import main.GamePanel;
import main.KeyHandler;

import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Player extends Entity /*implements Visitor */{
    GamePanel gp;
    Graphics2D g2;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int score = 0;
    //DATABASE
    public Connection connection;
    String dbFilePath = "db/tiobe.db";
    String absolutePath = new File(dbFilePath).getAbsolutePath();
    public Player(GamePanel gp, KeyHandler keyH)
    {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        eScale = 1;
        name = "player";
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

//        solidArea = new Rectangle();
//        solidArea.x = gp.tileSize / 3;
//        solidArea.y = gp.tileSize / 2;
//        solidArea.width = gp.tileSize - 2 * solidArea.x;
//        solidArea.height = solidArea.y;

        //PLAYER SOLID AREA
        solidArea = new Rectangle();
        solidArea.x = gp.tileSize / 4;
        solidArea.y = (gp.tileSize / 3)*2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = (gp.tileSize/4)*2;
        solidArea.height = gp.tileSize/3;


        //WEAPON SOLID AREA
        weaponArea = new Rectangle();
        weaponArea.x = gp.tileSize / 4;
        weaponArea.y = (gp.tileSize / 3)*2;
        weaponAreaDefaultX = weaponArea.x;
        weaponAreaDefaultY = weaponArea.y;
        weaponArea.width = (gp.tileSize/4)*2;
        weaponArea.height =(gp.tileSize/4)*2;

        //Attack Area
        attackArea.width = 36;
        attackArea.height = 36;

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:" + absolutePath);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        type = 1;

        setDefaultValues();

        attackR = getEntityImg(2,6,50,37,"/player/adventurer-attack.png");
        run = getEntityImg(4,6,50,37,"/player/adventurer-run.png");
        still = getEntityImg(1,5,50,37,"/player/idle.png");
        slide = getEntityImg(2,2,50,37,"/player/slide.png");
        bg = getEntityImg(1,1,2048,2048,"/player/cover1.png");
    }


    //public void visit(Player noob) {}
    public void attacking()
    {


            //Save weapons coordinates

            int currentWeaponX = weaponAreaDefaultX;
            int currentWeaponY = weaponAreaDefaultY;
            int weaponAreaWidth = weaponArea.width;
            int weaponAreaHeight = weaponArea.height;

            //adjust weapon coord for attack

            switch (attackDir) {
                case 1:
                    weaponArea.y -= attackArea.height;
                    break;
                case 2:
                    weaponArea.x += attackArea.width;
                    break;
                case 3:
                    weaponArea.y += attackArea.height;
                    break;
                case 4:
                    weaponArea.x -= attackArea.width;
                    break;
            }

            //weapon area becomes attack area
            weaponArea.width = attackArea.width;
            weaponArea.height = attackArea.height;

            //check monster collision with weapon
            int MonsterIndex = gp.cChecker.checkEnemyHit(this, gp.Monsters);
            damageMonster(MonsterIndex);
            //attacking = false;

            weaponArea.x = currentWeaponX;
            weaponArea.y = currentWeaponY;
            weaponArea.width = weaponAreaWidth;
            weaponArea.height = weaponAreaHeight;

    }
    public void setDefaultValues()
    {
        worldX = gp.tileSize * 21;
        worldY = gp.tileSize * 14;
        speed = 1;
        direction = "still";
        lastDirection = direction;

        //status
        maxHealth = 10;
        Health = maxHealth;
    }

    public void setDefaultPosition()
    {
        worldX = gp.tileSize * 28;
        worldY = gp.tileSize * 1;
        speed = 1;
        direction = "still";
        lastDirection = direction;

    }
    public void restoreStats()
    {
        //status
        maxHealth = 10;
        Health = maxHealth;
        invincible =false;
    }


    public void update() {

        if(attacking == true)
        {
            attacking();
        }

         else if(keyH.isSpacePressed() == true)
        {
            //direction = "slide";
            if(keyH.isLeftPressed() == true) {
                direction = "slideL";
                worldX -= speed * 2;
            }
            else if (keyH.isRightPressed() == true) {
                direction = "slideR";
                worldX += speed*2;
            } else if (keyH.isUpPressed() == true) {
                direction = "slideU";
                worldY -= speed*2;
            } else if (keyH.isDownPressed()==true) {
                direction = "slideD";
                worldY += speed*2;

            }
        }

        else if (keyH.isUpPressed() == true || keyH.isDownPressed() == true ||
                keyH.isLeftPressed() == true || keyH.isRightPressed() == true)
        {

            if (keyH.isUpPressed() == true){
                direction = "up";
                lastDirection ="up";
                worldY -= speed;}
            else if (keyH.isDownPressed() == true){
                direction = "down";
                lastDirection ="down";
                worldY += speed;}
            else if (keyH.isLeftPressed() == true) {
                direction = "left";
                lastDirection = "left";
                worldX -= speed;
            }  else if (keyH.isRightPressed() == true) {
                direction = "right";
                lastDirection = "right";
                worldX += speed;
            }
        } else
            if(keyH.isUpPressed() == false && keyH.isDownPressed() == false &&
                    keyH.isLeftPressed() == false && keyH.isRightPressed() == false)
            {
                direction = "still";
                lastDirection = "still";
            }
            //CHECK COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);


            //check obj coll
            int objIndex = gp.cChecker.checkObject(this, true);
            interact(objIndex);

            // check characters coll
            int npcIndex = gp.cChecker.checkEntity(this, gp.Monsters[gp.currentMap]);
            interactC(npcIndex);

            //check event

            gp.eHandler.checkEvent();



            // ->>> IF COLLISION FALSE ->> PLAYER CAN GO FURTHER
            if(collisionOn != false)
            {
                switch (direction)
                {
                    
                    case "up": worldY += speed;break;
                    case "down": worldY -= speed;break;
                    case "left": worldX += speed;break;
                    case "right": worldX -= speed;break;
                    case "slideR": worldX -= speed*2;break;
                    case "slideL": worldX += speed*2;break;
                    case "slideU": worldY += speed*2;break;
                    case "slideD": worldY -= speed*2;break;

                }
            }

            spriteCounter++;
            if (spriteCounter > 7 ) {
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
                if(invincible_cnt > 60)
                {
                    invincible = false;
                    invincible_cnt = 0;
                }
            }
            if(Health <= 0)
            {
                gp.gameState = gp.gameOverState;
            }
        }

    public void interact(int i)
    {
        if(i != 999999)
        {
            //specific  interac for each obj
            //gp.obj[i] = null;

            String objectName = gp.obj[gp.currentMap][i].name;

            switch (objectName)
            {
                case "pot":
                    gp.obj[gp.currentMap][i].use();
                    gp.obj[gp.currentMap][i] = null;

                    break;
                case "chest":
                    break;
                case "torch":
                    break;
            }
        }
    }
    //NPC COLLISION
    public void interactC(int i)
    {
        if(i!=999999)
        {
            if(invincible == false)
            {
                Health -= attackDamage;
                invincible = true;
            }
           // System.out.println("schelet!!");
            //if(Health <= 0 ) System.exit(0);
        }
    }
    //event

    public void damageMonster(int i)
    {

        if(i != 999999)
        {
           if(gp.Monsters[gp.currentMap][i].invincible == false) {

               gp.Monsters[gp.currentMap][i].Health -= 1;
               gp.Monsters[gp.currentMap][i].invincible = true;

           }
               if(gp.Monsters[gp.currentMap][i].Health < 1 )
                   //gp.Monsters[i] = null;
               {
                  gp.Monsters[gp.currentMap][i].dying = true;
                  //gp.Monsters[i] = null;

               }


        }

    }
    public void draw(Graphics2D g2)
    {
       // g2.setColor(Color.yellow);
        //g2.fillRect(x, y,gp.tileSize,gp.tileSize);

        BufferedImage image = null;

            switch (direction) {
                case "attackL":
                    if (spriteNum == 1)
                        image = attackR[5];
                    if (spriteNum == 2)
                        image = attackR[4];
                    if (spriteNum == 3)
                        image = attackR[3];
                    if (spriteNum == 4)
                        image = attackR[2];
                    if (spriteNum == 5)
                        image = attackR[1];
                    if (spriteNum == 6)
                        image = attackR[0];
                    break;

                case "attackR":
                    if (spriteNum == 1)
                        image = attackR[6];
                    if (spriteNum == 2)
                        image = attackR[7];
                    if (spriteNum == 3)
                        image = attackR[8];
                    if (spriteNum == 4)
                        image = attackR[9];
                    if (spriteNum == 5)
                        image = attackR[10];
                    if (spriteNum == 6)
                        image = attackR[11];
                    break;

                case "still":
                    if (attacking == false) {
                        if (spriteNum == 1)
                            image = still[0];
                        if (spriteNum == 2)
                            image = still[1];
                        if (spriteNum == 3)
                            image = still[1];
                        if (spriteNum == 4)
                            image = still[3];
                        if (spriteNum == 5)
                            image = still[3];
                        if (spriteNum == 6)
                            image = still[4];
                    }
                    if (attacking == true) {
                        if (spriteNum == 1)
                            image = attackR[6];
                        if (spriteNum == 2)
                            image = attackR[7];
                        if (spriteNum == 3)
                            image = attackR[8];
                        if (spriteNum == 4)
                            image = attackR[9];
                        if (spriteNum == 5)
                            image = attackR[10];
                        if (spriteNum == 6)
                            image = attackR[11];
                    }
                    break;

                case "slideR", "slide":
                    if (attacking == false) {
                        if (spriteNum == 1)
                            image = slide[0];
                        if (spriteNum == 2)
                            image = slide[1];
                        if (spriteNum == 3)
                            image = slide[0];
                        if (spriteNum == 4)
                            image = slide[1];
                        if (spriteNum == 5)
                            image = slide[0];
                        if (spriteNum == 6)
                            image = slide[1];
                    }
                    if (attacking == true) {
                        if (spriteNum == 1)
                            image = attackR[6];
                        if (spriteNum == 2)
                            image = attackR[7];
                        if (spriteNum == 3)
                            image = attackR[8];
                        if (spriteNum == 4)
                            image = attackR[9];
                        if (spriteNum == 5)
                            image = attackR[10];
                        if (spriteNum == 6)
                            image = attackR[11];
                    }
                    break;
                case "slideL":
                    if (attacking == false) {
                        if (spriteNum == 1)
                            image = slide[2];
                        if (spriteNum == 2)
                            image = slide[3];
                        if (spriteNum == 3)
                            image = slide[2];
                        if (spriteNum == 4)
                            image = slide[3];
                        if (spriteNum == 5)
                            image = slide[2];
                        if (spriteNum == 6)
                            image = slide[3];
                    }
                    if (attacking == true) {
                        if (spriteNum == 1)
                            image = attackR[5];
                        if (spriteNum == 2)
                            image = attackR[4];
                        if (spriteNum == 3)
                            image = attackR[3];
                        if (spriteNum == 4)
                            image = attackR[2];
                        if (spriteNum == 5)
                            image = attackR[1];
                        if (spriteNum == 6)
                            image = attackR[0];
                    }
                    break;


                case "NE", "SE", "right":
                    if (attacking == false) {
                        if (spriteNum == 1)
                            image = run[0];
                        if (spriteNum == 2)
                            image = run[1];
                        if (spriteNum == 3)
                            image = run[2];
                        if (spriteNum == 4)
                            image = run[3];
                        if (spriteNum == 5)
                            image = run[4];
                        if (spriteNum == 6)
                            image = run[5];
                    }
                    if (attacking == true) {
                        if (spriteNum == 1)
                            image = attackR[6];
                        if (spriteNum == 2)
                            image = attackR[7];
                        if (spriteNum == 3)
                            image = attackR[8];
                        if (spriteNum == 4)
                            image = attackR[9];
                        if (spriteNum == 5)
                            image = attackR[10];
                        if (spriteNum == 6)
                            image = attackR[11];
                    }
                    break;
                case "NW", "SW", "left":
                    if (attacking == false) {
                        if (spriteNum == 1)
                            image = run[11];
                        if (spriteNum == 2)
                            image = run[10];
                        if (spriteNum == 3)
                            image = run[9];
                        if (spriteNum == 4)
                            image = run[8];
                        if (spriteNum == 5)
                            image = run[7];
                        if (spriteNum == 6)
                            image = run[6];
                    }
                    if (attacking == true) {
                        if (spriteNum == 1)
                            image = attackR[5];
                        if (spriteNum == 2)
                            image = attackR[4];
                        if (spriteNum == 3)
                            image = attackR[3];
                        if (spriteNum == 4)
                            image = attackR[2];
                        if (spriteNum == 5)
                            image = attackR[1];
                        if (spriteNum == 6)
                            image = attackR[0];
                    }

                    break;

                case "slideU", "up":
                    if (attacking == false) {
                        if (spriteNum == 1)
                            image = run[12];
                        if (spriteNum == 2)
                            image = run[15];
                        if (spriteNum == 3)
                            image = run[16];
                        if (spriteNum == 4)
                            image = run[17];
                        if (spriteNum == 5)
                            image = run[13];
                        if (spriteNum == 6)
                            image = run[14];
                    }
                    if (attacking == true) {
                        if (spriteNum == 1)
                            image = attackR[6];
                        if (spriteNum == 2)
                            image = attackR[7];
                        if (spriteNum == 3)
                            image = attackR[8];
                        if (spriteNum == 4)
                            image = attackR[9];
                        if (spriteNum == 5)
                            image = attackR[10];
                        if (spriteNum == 6)
                            image = attackR[11];
                    }
                    break;
                case "slideD", "down":
                    if (attacking == false) {
                        if (spriteNum == 1)
                            image = run[23];
                        if (spriteNum == 2)
                            image = run[22];
                        if (spriteNum == 3)
                            image = run[21];
                        if (spriteNum == 4)
                            image = run[20];
                        if (spriteNum == 5)
                            image = run[19];
                        if (spriteNum == 6)
                            image = run[18];
                    }
                    if (attacking == true) {
                        if (spriteNum == 1)
                            image = attackR[5];
                        if (spriteNum == 2)
                            image = attackR[4];
                        if (spriteNum == 3)
                            image = attackR[3];
                        if (spriteNum == 4)
                            image = attackR[2];
                        if (spriteNum == 5)
                            image = attackR[1];
                        if (spriteNum == 6)
                            image = attackR[0];
                    }
                    break;

            }



        if(invincible == true)
        {
            //draw transparent when hit
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize , gp.tileSize, null);

        //reset transparency
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setColor(Color.red);

        //g2.drawRect(attackWorldX,attackWorldY,attackArea.width,attackArea.height);

        //debug
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.red);
        g2.drawString("Invincible:" + invincible_cnt,10,400);
    }

    public void createScoresTable() {
        try {
            Statement statement = connection.createStatement();

//            String query = "DROP TABLE IF EXISTS PlayerStats";
//            statement.executeUpdate(query);
//            System.out.println("Table deleted successfully.");

             String query = "CREATE TABLE IF NOT EXISTS PlayerStats ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "score INTEGER,"
                    + "playerX INTEGER,"
                    + "playerY INTEGER,"
                    + "PlayerHP INTEGER,"
                    + "currentMap INTEGER"
                    + ")";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveScoresToDatabase() {
        try {
            String insertQuery = "INSERT INTO PlayerStats (score, playerX, playerY, PlayerHP, currentMap) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, score);
            insertStatement.setInt(2, this.worldX);
            insertStatement.setInt(3, this.worldY);
            insertStatement.setInt(4, Health);
            insertStatement.setInt(5, gp.currentMap);



            // Execute the INSERT statement
            insertStatement.executeUpdate();

            // Close the statement
            insertStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadScoresFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PlayerStats ORDER BY id DESC LIMIT 1");

            if (resultSet.next()) {
                score = resultSet.getInt("score");
                this.worldX = resultSet.getInt("playerX");
                this.worldY = resultSet.getInt("playerY");
                Health = resultSet.getInt("PlayerHP");
                gp.currentMap = resultSet.getInt("currentMap");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
