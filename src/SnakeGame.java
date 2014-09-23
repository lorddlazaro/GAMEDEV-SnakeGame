/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.golden.gamedev.GameLoader;

import java.awt.Dimension;
import java.io.FileNotFoundException;


/**
 *
 * @author student
 */
public class SnakeGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GameFrame gf = new GameFrame();
        GameLoader game = new GameLoader();
        
        gf.readHighscore();
		
        game.setup(gf,new Dimension(640,640), false);
        game.start();
        
    }
}
