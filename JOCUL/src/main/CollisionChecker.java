package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp)
    {
        this.gp = gp;
    }
    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2, tileNum3, tileNum4;

        switch(entity.direction)
        {
            case "NE":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                entityRightCol = (entityRightWorldX - entity.speed) / gp.tileSize;
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum4 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ||
                        gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true)
                {
                    entity.collisionOn = true;
                }

                break;
            case "NW":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum4 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ||
                        gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;

            case "SE":
                entityBottomRow = (entityBottomWorldY + entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                entityRightCol = (entityRightWorldX - entity.speed) / gp.tileSize;
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum4 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ||
                        gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "SW":
                entityBottomRow = (entityBottomWorldY + entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum4 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ||
                        gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "up","slideU":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "down","slideD":
                entityBottomRow = (entityBottomWorldY + entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "left","slideL":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow] ;
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow] ;
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow] ;
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "slideR":
            entityRightCol = (entityRightWorldX - entity.speed * 2) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
            break;


        }
    }
    public int checkObject(Entity entity, boolean player)
    {
        int index = 999999;

        for(int i = 0; i < gp.obj[1].length; i++)
        {
            if(gp.obj[gp.currentMap][i] != null)
            {
                //solid area position of entity

                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //-||- obj

                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch (entity.direction)
                {
                    case "up","slideU","NW","NE":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea))
                        {
                            if(gp.obj[gp.currentMap][i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)
                            {
                                index = i;
                            }
                        }
                        break;

                    case "down","slideD","SE","SW":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea))
                        {
                            if(gp.obj[gp.currentMap][i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)
                            {
                                index = i;
                            }

                        }
                        break;

                    case "left","slideL":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea))
                        {
                            if(gp.obj[gp.currentMap][i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)
                            {
                                index = i;
                            }
                        }
                        break;

                    case "right","slideR":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea))
                        {
                            if(gp.obj[gp.currentMap][i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)
                            {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }
    //characters collision
    public int checkEntity(Entity entity, Entity [] target)
    {
        int index = 999999;

        for(int i = 0; i < target.length; i++)
        {
            if(target[i] != null)
            {
                //solid area position of entity

                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //-||- player

                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction)
                {
                    case "up","slideU":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea))
                        {

                                entity.collisionOn = true;
                                index = i;

                        }
                        break;

                    case "down","slideD":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea))
                        {
                             entity.collisionOn = true;
                             index = i;


                        }
                        break;

                    case "left","slideL":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea))
                        {
                            entity.collisionOn = true;
                             index = i;

                        }
                        break;

                    case "right","slideR":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea))
                        {
                            entity.collisionOn = true;
                            index = i;

                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;

    }
    public int checkEnemyHit(Entity entity, Entity [][] target)
    {
        int index = 999999;

        for(int i = 0; i < target[1].length; i++)
        {
            if(target[gp.currentMap][i] != null)
            {
                //solid area position of entity

                entity.weaponArea.x = entity.worldX + entity.weaponArea.x;
                entity.weaponArea.y = entity.worldY + entity.weaponArea.y;

                //-||- player

                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (entity.direction)
                {
                    case "up","slideU":
                        entity.solidArea.y -= entity.speed;
                        if(entity.weaponArea.intersects(target[gp.currentMap][i].solidArea))
                        {

                            entity.collisionOn = true;
                            index = i;

                        }
                        break;

                    case "down","slideD":
                        entity.weaponArea.y += entity.speed;
                        if(entity.weaponArea.intersects(target[gp.currentMap][i].solidArea))
                        {
                            entity.collisionOn = true;
                            index = i;


                        }
                        break;

                    case "left","slideL":
                        entity.weaponArea.x -= entity.speed;
                        if(entity.weaponArea.intersects(target[gp.currentMap][i].solidArea))
                        {
                            entity.collisionOn = true;
                            index = i;

                        }
                        break;

                    case "right","slideR":
                        entity.weaponArea.x += entity.speed;
                        if(entity.weaponArea.intersects(target[gp.currentMap][i].solidArea))
                        {
                            entity.collisionOn = true;
                            index = i;

                        }
                        break;
                }
                entity.weaponArea.x = entity.weaponAreaDefaultX;
                entity.weaponArea.y = entity.weaponAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;

    }
    public boolean checkPlayer(Entity entity)
    {
        boolean contactP = false;
        //solid area position of entity

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //-||- player

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction)
        {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;

            case "down":
                entity.solidArea.y += entity.speed;

                break;

            case "left":
                entity.solidArea.x -= entity.speed;

                break;

            case "right":
                entity.solidArea.x += entity.speed;

                break;

        }
        if(entity.solidArea.intersects(gp.player.solidArea))
        {

            entity.collisionOn = true;
            contactP = true;

        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactP;
    }

}
