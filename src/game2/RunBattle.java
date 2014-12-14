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
/**
 *
 * @author AmandaMa
 */
public class RunBattle extends World {
    final int screenWidth = 300;
    final int screenHeight = 600;
   
    final int winNumber = 10;
    
    int level; 
    int frames;
    int enemiesIn;
    int enemiesOut;
    
    Spaceship myShip;
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    
    boolean gameOver;
    
    //start of a ship battle
    public RunBattle(int level){
        super();
        this.frames = 0;
        this.enemiesIn = 0;
        this.enemiesOut = 0;
        this.myShip = new Spaceship(screenWidth, screenHeight);
        this.enemies = new ArrayList();
        this.bullets = new ArrayList();
        this.gameOver = false;
    }
    
    //duration of a shipbattle
    public RunBattle(int level, int frames, int enemiesIn, int enemiesOut,
            Spaceship myShip, ArrayList<Enemy> enemies, ArrayList<Bullet> bullets, 
            boolean gameOver){
        super();
        this.level = level;
        this.frames = frames;
        this.enemiesIn = enemiesIn;
        this.enemiesOut = enemiesOut;
        this.myShip = myShip;
        this.enemies = enemies;
        this.bullets = bullets;
        this.gameOver = gameOver;
    }

    
    
    public WorldImage makeImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
