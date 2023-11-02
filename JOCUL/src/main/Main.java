package main;

import javax.swing.*;

public class Main {
    private static Main instance;

    private Main() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Dawnbringer");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        //gamePanel.config.loadConfig();


        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    //se foloseste sablonul de proiectare singleton pentru Main
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public static void main(String[] args) {
        Main.getInstance();
    }
}

