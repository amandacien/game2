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
    
    
    public RunMaze(int level, int red, int blue, int yellow, Player player) {
        this.level = level;
        this.player = player.setShields(red, blue, yellow);
        this.mysteryObj = new MysteryObj();
    }
    
    public RunMaze(MysteryObj mysteryObj, Player player, int level){
        super();
        this.mysteryObj = mysteryObj;
        this.player = player;
        this.level = level;
    }

    public World onKey(String key){
        return new RunMaze(this.mysteryObj.onKey(key), this.player.onKey(key), this.level);
    }
    
    public World onTick(){
        if(player.inBattle) {
            return new RunBattle(this.level, this.player.red, this.player.blue, 
                    this.player.yellow, this.player);
        } else {
            return new RunMaze(this.mysteryObj, this.player, this.level);
        }
    }
    
}
