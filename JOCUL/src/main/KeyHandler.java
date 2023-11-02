package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// SE FOLOSESTE SABLONUL OBSERVER
public class KeyHandler implements KeyListener {

    GamePanel gp;
    private List<KeyListener> listeners = new ArrayList<>();
    private boolean upPressed, downPressed, leftPressed, rightPressed,KPressed,SpacePressed,enterPressed,PPressed,EscPressed;
    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }
    public void addListener(KeyListener listener) {
        listeners.add(listener);
    }

    public void removeListener(KeyListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Notify all listeners of the key event
        for (KeyListener listener : listeners) {
            listener.keyTyped(e);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //title state
        if(gp.gameState == gp.titleState)
        {
            if (code == KeyEvent.VK_W)
            {
               if(gp.ui.commandNum - 1 >= 0)
                gp.ui.commandNum--;
               else gp.ui.commandNum = 2;

            }
                if (code == KeyEvent.VK_S)
                {
                   if(gp.ui.commandNum + 1 <= 2)
                    gp.ui.commandNum++;
                   else gp.ui.commandNum = 0;
                }
                if(code == KeyEvent.VK_ENTER)
                {
                    if(gp.ui.commandNum == 0)
                    {
                        gp.gameState = gp.playState;
                        gp.player.createScoresTable();
                    }
                    if(gp.ui.commandNum == 1)
                    {
                        gp.player.loadScoresFromDatabase();
                        gp.gameState = gp.playState;

                    }

                    if(gp.ui.commandNum == 2)
                    {
                        gp.player.saveScoresToDatabase();
                        try {
                            gp.player.connection.close();

                        } catch (SQLException ex) {

                            throw new RuntimeException(ex);
                        }
                        System.exit(0);

                    }
                }



            for (KeyListener listener : listeners) {
                listener.keyPressed(e);
            }
        }
        //pause state
        if(gp.gameState == gp.pauseState)
        {
            if(code == KeyEvent.VK_P)
            {
                PPressed = true;
                gp.gameState = gp.playState;
            }
            if(code == KeyEvent.VK_ESCAPE)
            {
                EscPressed = true;
                gp.gameState = gp.optionsState;
            }
        }
        //option state
        if(gp.gameState == gp.optionsState)
        {
            if(code == KeyEvent.VK_ESCAPE)
            {
                EscPressed = true;
                gp.gameState = gp.playState;
            }
            if(code == KeyEvent.VK_ENTER)
            {
                enterPressed = true;
                if(gp.ui.commandNum == 2) {
                    gp.player.saveScoresToDatabase();
                    System.out.println("Game saved");
                }
            }
            int maxCommN = 0;
            switch (gp.ui.substate)
            {
                case 0: maxCommN = 4;
            }
            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum --;
                upPressed = true;

                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum = maxCommN;
                }

            }
            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum++;
                downPressed = true;
                if(gp.ui.commandNum > maxCommN)
                {
                    gp.ui.commandNum = 0;
                }
            }
        }

        //play state
        if(gp.gameState == gp.playState)
        {  if(code == KeyEvent.VK_ENTER)
            {
                enterPressed = true;
            }
         if (code == KeyEvent.VK_W)
        {
            upPressed = true;
            gp.player.attackDir = 1;
        }
        if (code == KeyEvent.VK_S)
        {
            downPressed = true;
            gp.player.attackDir = 3;
        }
        if (code == KeyEvent.VK_A)
        {
            leftPressed = true;
            gp.player.attackDir = 4;
        }
        if (code == KeyEvent.VK_D)
        {
            rightPressed = true;
            gp.player.attackDir = 2;
        }
        if (code == KeyEvent.VK_P)
        {

                gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_K)
        {
            KPressed = true;
             gp.player.attacking =true;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            SpacePressed = true;
        }
            if(code == KeyEvent.VK_ESCAPE)
            {
                EscPressed = true;
                gp.gameState = gp.optionsState;
            }

        // Notify all listeners of the key event
        for (KeyListener listener : listeners) {
            listener.keyPressed(e);
        }}

        //game over state
        if(gp.gameState == gp.gameOverState)
        {
            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1)
                {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER)
            {
                if(gp.ui.commandNum == 0)
                {
                    gp.gameState = gp.playState;
                   // gp.retry();
                }
                else if(gp.ui.commandNum == 1)
                {
                    gp.gameState = gp.titleState;
                   // gp.restart();
                }
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_K)
        {
            KPressed = false;
            gp.player.attacking =false;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            SpacePressed = false;
        }
        if(code == KeyEvent.VK_P)
        {
            PPressed = false;
        }
        if(code == KeyEvent.VK_ESCAPE)
        {
            EscPressed = false;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = false;
        }

        // Notify all listeners of the key event
        for (KeyListener listener : listeners) {
            listener.keyReleased(e);
        }
    }

    // Additional methods for getting the state of the keys
    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isKPressed() {
        return KPressed;
    }
    public boolean isSpacePressed() {
        return SpacePressed;
    }
    public boolean isEnterPressed(){return enterPressed;}
    public void setEnterPressed(boolean x){
        enterPressed = x;
    }
    public boolean isPPressed(){return PPressed;}

    public boolean isEscPressed() {return EscPressed;}
}
