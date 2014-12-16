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
    
    final static int screenWidth = 400;
    final static int screenHeight = 500; 
    
    Player player;
    int level;
    
    //for the start of the game
    public RunMaze(){
        this(1, 5, 5, 5, new Player(screenWidth, screenHeight));
    }
    
    //for getting out of the battle 
    public RunMaze(int level, int red, int blue, int yellow, Player player) {
        super();
        this.level = level;
        this.player = player.setShields(red, blue, yellow);
    }
    
    //for the duration of the maze 
    public RunMaze(Player player, int level){
        super();
        this.player = player;
        this.level = level;
    }
    
    public WorldEnd worldEnds(){
        if(this.player.level >= 10){
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                new TextImage(new Posn(screenWidth/2,screenHeight/2), 
                        ("You've defeated them all!"),
                        20, new White())));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }

    public World onKeyEvent(String key){
        return new RunMaze(this.player.onKey(key), this.level);
    }
    
    public World onTick(){
        if (this.player.inBattle) {
            return new RunBattle(this.level, this.player.red, this.player.blue, 
                    this.player.yellow, this.player);
        } else {
            return new RunMaze(this.player.onTick(), this.level);
        }
    }
    
    private WorldImage background(){
        return new RectangleImage(new Posn(screenWidth/2, screenHeight/2),
                            screenWidth, screenHeight, new Black());
    }
    
    private WorldImage eventImage(){
        String eventCase;
        if (this.player.eventNum == 0) {
            eventCase = "Battle is on!";
        } else if (this.player.eventNum == 1) {
            eventCase = "Replenished Red Shield";
        } else if (this.player.eventNum == 2) {
            eventCase = "Replenished Blue Shield";
        } else if (this.player.eventNum == 3) {
            eventCase = "Replenished Blue Shield!";
        } else if (this.player.eventNum == 4) {
            eventCase = "Luck or not, you get nothing from this room";
        } else if (this.player.eventNum == 5) {
            eventCase = "You've hit the spacebar already, leave!";
        } else {
            eventCase = "You have to hit the spacebar";
        }
        
        return new TextImage(new Posn(this.screenWidth/2, 15), 
                            (eventCase),10, new White());
    }
    
    
    public WorldImage makeImage(){
        return new OverlayImages(background(),
               new OverlayImages(player.playerImage(), eventImage()));
        /*return new RectangleImage(new Posn(screenWidth/2, screenHeight/2),
                            screenWidth, screenHeight, new Black());*/
    }
    
    
    
    public static void main (String[] args) {
        RunMaze game = new RunMaze();
        
        game.bigBang(400, 500, 0.15);
    }
}

