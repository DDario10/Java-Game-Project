package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel  implements  Runnable{
    //SCREEN SETTINGS
    public final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;// 4:3
    public final int screenWidth = tileSize * maxScreenCol; //1536
    public final int screenHeight = tileSize * maxScreenRow; //864

    // WORLD SETTINGS
    public final int maxMap = 4;
    public int currentMap = 0;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);


    //FPS
    int FPS = 120;
    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler(this);
    public  UI ui = new UI(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);

    //HOW MANY OBJECTS CAN BE DISPLAYED
    public SuperObject[][] obj = new SuperObject[maxMap][100];
    public Entity[][] Monsters = new Entity[maxMap][100];

    //default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 2;
    int timer = 0;

// GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int optionsState = 3;
    public final int gameOverState = 4;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame()
    {

        aSetter.setObject();
        aSetter.setMonster();
        gameState = titleState ;
    }
    public void retry()
    {
        player.setDefaultPosition();
        player.restoreStats();
        aSetter.setMonster();
    }
    public void restart()
    {
        player.score =0;
        player.setDefaultValues();
        player.setDefaultPosition();
        player.restoreStats();
        aSetter.setObject();
        aSetter.setMonster();

    }
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawtime = System.nanoTime() + drawInterval;
            while(gameThread != null)
            {
                //1. Update:
                update();

                //2. Draw screen with updated info:
                repaint();
                try {
                    double remainingTime = nextDrawtime - System.nanoTime();
                    remainingTime = remainingTime / 1000000;

                    if(remainingTime < 0)
                    {
                        remainingTime = 0;
                    }

                    Thread.sleep((long) remainingTime);

                    nextDrawtime += drawInterval;

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
    }
    public void update()
    {
        if (gameState == playState)
        {
            timer++;
            if(timer == 60) {player.score -=1; timer = 0;}
            player.update();
            //mosnters
            for(int i = 0; i < Monsters[1].length; i++)
            {
                if(Monsters[currentMap][i]!=null)
                {
                    if(Monsters[currentMap][i].alive == true )
                    Monsters[currentMap][i].update();
                    if(Monsters[currentMap][i].alive == false)
                    {
                        Monsters[currentMap][i].checkDrop();
                        Monsters[currentMap][i] = null;
                        player.score += 100;
                        System.out.println("score" + player.score);
                    }

                }
            }
        }
        if(gameState == pauseState) {
            //..
            if(keyH.isPPressed() == true) gameState = playState;
        }
    }
    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //tileM.draw(g2);

        //title screen

        if(gameState == titleState)
        {
            ui.draw(g2);

        }
        //
        else{
            tileM.draw(g2);
            //objects
            for(int i = 0; i < obj[1].length; i++)
            {
                if(obj[currentMap][i]!=null) {

                    obj[currentMap][i].draw(g2, this);
                    if(i>=5 && i<=10) obj[currentMap][i].drawTeleporter(g2, this);

                }
            }
            //monsters
            for(int i = 0; i < Monsters[1].length; i++)
            {
                if(Monsters[currentMap][i] != null)
                {

                    Monsters[currentMap][i].draw(g2);


                }

            }


            //player
            player.draw(g2);

            ui.draw(g2);

        }

        g2.dispose();
    }
}
