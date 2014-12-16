/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game2;
import java.awt.Color;
import java.util.*;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import java.util.Random;

/**
 *
 * @author AmandaMa
 */
public class RunMaze extends World {
    
    final int screenWidth = 400;
    final int screenHeight = 500; 
    
    MysteryObj mysteryObj;
    Player player;
    
    int level;
    
    public RunMaze(){
        new RunMaze(1, 5, 5, 5 new Player(screenWidth, screenHeight));
    }
    
    public RunMaze(int level, int red, int blue, int yellow, Player player) {
        super();
        this.level = level;
        this.player = player.setShields(red, blue, yellow);
    }
    
    public RunMaze(Player player, int level){
        super();
        this.player = player;
        this.level = level;
    }

    public World onKey(String key){
        return new RunMaze(this.player.onKey(key), this.level);
    }
    
    public World onTick(){
        \
    }
    
}
