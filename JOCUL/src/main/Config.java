package main;

import java.io.*;

public class Config {

    GamePanel gp;
    public Config(GamePanel gp)
    {
        this.gp = gp;
    }
//    public void saveConfig()
//    {
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter( "config.txt"));
//
//            //fullscreen
//            bw.newLine();
//            //music
//            //
//            bw.close();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public void loadConfig()
//    {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
//
//            String s = br.readLine();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

}
