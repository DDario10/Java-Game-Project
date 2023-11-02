package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    public TileManager(GamePanel gp)
    {
        this.gp = gp;
        tile = new Tile[2000];
        mapTileNum = new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/map01.txt",0 );
        loadMap("/maps/map02.txt",1);
        loadMap("/maps/map03.txt",2);
    }




    public void getTileImage() {

        try {
            //MAP 1
            BufferedImage tileSheet = ImageIO.read(getClass().getResourceAsStream("/tiles/sheetul3.png"));

            int tileWidth = gp.originalTileSize;
            int tileHeight = gp.originalTileSize;

            int numRows = 27;
            int numCols = 26;

            for(int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    int tileIndex = row * numCols + col;
                    int x = col * tileWidth;
                    int y = row * tileHeight;

                    tile[tileIndex] = new Tile();
                    tile[tileIndex].image = tileSheet.getSubimage(x, y, tileWidth, tileHeight);
//                     316, 339, 338, 317, 323, 324,
//                     328, 318, 329, 333, 319, 334, 346, 347,
//                     348, 354, 355, 356, 172, 362, 363, 364, 367,
//                     370, 371, 372, 378, 379, 380, 116, 117, 118,
//                     119, 120, 121, 52
                    if (tileIndex == 315 || tileIndex == 338 || tileIndex == 337 || tileIndex == 316
                            || tileIndex == 322 || tileIndex == 323 || tileIndex == 327 || tileIndex == 317
                            || tileIndex == 328 || tileIndex == 332 || tileIndex == 318 || tileIndex == 333
                            || tileIndex == 345 || tileIndex == 346 || tileIndex == 347 || tileIndex == 353
                            || tileIndex == 354 || tileIndex == 355 || tileIndex == 171 || tileIndex == 361
                            || tileIndex == 362 || tileIndex == 363 || tileIndex == 366 || tileIndex == 369
                            || tileIndex == 370 || tileIndex == 371 || tileIndex == 377 || tileIndex == 378
                            || tileIndex == 379 || tileIndex == 115 || tileIndex == 116 || tileIndex == 117
                            || tileIndex == 118 || tileIndex == 119 || tileIndex == 120 || tileIndex == 51
                            || tileIndex == 389 || tileIndex == 390 || tileIndex == 391
                            || tileIndex ==412 || tileIndex ==413 || tileIndex ==414
                            || tileIndex == 434 || tileIndex == 435 || tileIndex == 436 || tileIndex == 457) {
                        tile[tileIndex].collision = false;
                    }
                }
            }
           tile[300].image = null;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void loadMap(String filePath, int map)
    {
        try{

            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow)
            {
                String line = br.readLine();
                while(col < gp.maxWorldCol)
                {
                    String numbers[] =line.split(" ");


                    int num = Integer.parseInt(numbers[col]) ;
                    if(map == 2) num -= 1;

                    mapTileNum[map][col][row] = num;

                    col++;
                }
                if(col == gp.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2)
    {
        int worldCol = 0;
        int worldRow = 0;
        while ( worldCol< gp.maxWorldCol && worldRow < gp.maxWorldRow)
        {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

           /* if(worldX > gp.player.worldX - gp.player.screenX &&
            worldX < gp.player.worldX + gp.player.screenX &&
                    worldY > gp.player.worldY - gp.player.screenY &&
                    worldY < gp.player.worldY + gp.player.screenY)*/

            g2.drawImage(tile[tileNum].image,screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;

            if( worldCol== gp.maxWorldCol)
            {
                worldCol = 0;

                worldRow++;

            }
        }
    }
}
 /*public void getTileImage()
    {
        try{
            tile[0] = new Tile();
            tile[1] = new Tile();
            tile[2] = new Tile();
            tile[3] = new Tile();
            tile[4] = new Tile();
            tile[5] = new Tile();
            tile[6] = new Tile();
            tile[7] = new Tile();
            tile[8] = new Tile();
            tile[9] = new Tile();
            tile[10] = new Tile();
            tile[11] = new Tile();
            tile[12] = new Tile();
            tile[13] = new Tile();
            tile[14] = new Tile();
            tile[15] = new Tile();
            tile[16] = new Tile();
            tile[17] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_plain.png"));
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_left.png"));
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_center.png"));
            tile[2].collision = true;
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_right.png"));
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_outer_sw.png"));
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_outer_se.png"));
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_left.png"));
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_open.png"));
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_right.png"));
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_nw.png"));
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_w.png"));
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_sw.png"));
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_s_1.png"));
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_se.png"));
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_e.png"));
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_ne.png"));
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_n_1.png"));
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_mid_1.png"));



        }catch(IOException e)
        {
            e.printStackTrace();
        }

    }*/
