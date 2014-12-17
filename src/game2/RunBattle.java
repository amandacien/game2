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
public class RunBattle extends World {
    final int screenWidth = 400;
    final int screenHeight = 500;
   
    final int winNumber = 20;
    
    int level; 
    int frames;
    int enemiesIn;
    int enemiesOut;
    
    Spaceship myShip;
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    
    boolean gameOver;
    
    static Random rand = new Random();
    
    Player player;

    private static int randInt(int start, int range) {
        return rand.nextInt(range) + start ;
    }
    
    //start of a ship battle
    public RunBattle(int level, int red, int blue, int yellow, Player player){
        super();
        this.frames = 0;
        this.enemiesIn = 0;
        this.enemiesOut = 0;
        this.myShip = new Spaceship(screenWidth, screenHeight, red, blue, yellow);
        this.enemies = new ArrayList();
        this.bullets = new ArrayList();
        this.gameOver = false;
        this.player = player;
        
    }
    
    //duration of a shipbattle
    public RunBattle(int level, int frames, int enemiesIn, int enemiesOut,
            Spaceship myShip, ArrayList<Enemy> enemies, ArrayList<Bullet> bullets, 
            boolean gameOver, Player player){
        super();
        this.level = level;
        this.frames = frames;
        this.enemiesIn = enemiesIn;
        this.enemiesOut = enemiesOut;
        this.myShip = myShip;
        this.enemies = enemies;
        this.bullets = bullets;
        this.gameOver = gameOver;
        this.player = player;
    }
    
    public RunBattle onKeyEvent(String key){
        if (key.equals("left") || (key.equals("right"))) {
            return new RunBattle(this.level, this.frames, this.enemiesIn, this.enemiesOut,
            myShip.onKey(key), this.enemies, this.bullets, this.gameOver, this.player);
        } else { 
            return this;
        }
    }
    
    public WorldEnd worldEnds(){
        if(gameOver){
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                new TextImage(new Posn(screenWidth/2,screenHeight/2), 
                        ("Game Over"),
                        20, new White())));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }
    
    public World onTick() {
        ArrayList<Bullet> newBullets = bullets; 
        ArrayList<Enemy> newEnemies = new ArrayList();
        Spaceship newMyShip = this.myShip;
        
        int newEnemiesIn = this.enemiesIn;
        int newEnemiesOut = this.enemiesOut;
        
        for (int i = 0; i < enemies.size(); i++) {   
            //ticks each enemy
            Enemy newEnemy = enemies.get(i).onTick();
            
            for (int j = 0; j < newBullets.size(); j++) {
                //Considers the bullet in question to the enemy 
                Bullet newBullet = newBullets.get(j).outOfBounds().isHitEnemy(newEnemy);
                
                //looks at if the enemy is not hit by any bullet 
                newEnemy = newEnemy.isHit(newBullet);
                
                
                //sets the bullet to one that is out of bounds or hit 
                newBullets.set(j, newBullet);
                
            }
            
            //considering the information, it considers what to add to the array
            if (newEnemy.isHit) {
                //if the enemy is hit, then adds one to the count of enemies gone
                newEnemiesOut += 1;
            } else {
                //if it's not hit then it's added to the array 
                newEnemies.add(newEnemy);
                
                //because it's being added to the array, the enemy then
                //has to consider if it attaks, the harder the level is, the more  
                //often it will shoot
                if (randInt(0, 29 - ((this.level) * 3)) == 0) {
                    newBullets.add(newEnemy.makeBullet());
                }
                
                //if the enemy has surpassed the ship, the game is over
                if (newEnemy.gameOver(newMyShip)) {
                    gameOver = true;
                }
            } 
        }
        
        
        //then looks at if the ship is being hit by bullets 
        for (int k = 0; k < newBullets.size(); k++) {
            
            newMyShip = newMyShip.isHit(newBullets.get(k));
            
            Bullet anotherNewBullet = newBullets.get(k).isHitSpaceShip(newMyShip);
            
            if (newMyShip.winCase == false) {
                gameOver = true;
            } 
            
            newBullets.set(k, anotherNewBullet);
        }
        
        
        //shoots a bullet every 10 frames from the ship 
        if (this.frames % 10 == 2) {
            newBullets.add(newMyShip.makeBullet());
        }
        
        //adds an enemy every 8 frames 
        if (this.frames % 8 == 0 && newEnemiesIn < winNumber ) {
            newEnemies.add(new Enemy(screenWidth, screenHeight));
            newEnemiesIn += 1;
        }
        
        
        ArrayList<Bullet> finalBullets = new ArrayList();
        
        for (int l = 0; l < newBullets.size(); l++) {
            Bullet anotherBullet = newBullets.get(l).onTick();
            
            if (anotherBullet.onScreen) {
                finalBullets.add(anotherBullet);
            }
        }
        
        
        //if 20 enemies have entered and you have killed all 20, you go back to the maze 
        if (newEnemiesIn == winNumber && newEnemiesOut == winNumber) {
            return new RunMaze(this.level + 1, newMyShip.red, newMyShip.blue, 
                    newMyShip.yellow, this.player);
        } else {
            //if not, the battle keeps going
            return new RunBattle (this.level, this.frames + 1, newEnemiesIn, newEnemiesOut,
            newMyShip, newEnemies, finalBullets, this.gameOver, this.player);
        }
                
    }
    
    private WorldImage background(){
        return new RectangleImage(new Posn(screenWidth/2, screenHeight/2),
                            screenWidth, screenHeight, new Black());
    }
    
    
    private WorldImage enemyImage(ArrayList<Enemy> enemies, int counter){
        if (enemies.isEmpty())
            return bulletImage(bullets, bullets.size() - 1);
        else if (counter == -1)
            return bulletImage(bullets, bullets.size() - 1);
        else 
            return (new OverlayImages (enemyImage(enemies, counter - 1),
                    enemies.get(counter).enemyImage()));
            
    }
    
    private WorldImage bulletImage(ArrayList<Bullet> bullets, int counter){
        if (bullets.isEmpty())
            return background();
        else if (counter == -1)
            return background();
        else 
            return (new OverlayImages (bulletImage(bullets, counter - 1),
                    bullets.get(counter).bulletImage()));
            
    }
    
    private WorldImage shieldImage(){
        return new OverlayImages(new TextImage(new Posn(30, 15), 
                            ("Blue: " + this.myShip.blue),10, new White()),
                new OverlayImages(new TextImage(new Posn(30, 35), 
                            ("Red: " + this.myShip.red),10, new White()),
                        new TextImage(new Posn(30, 55), 
                            ("Yellow: " + this.myShip.yellow),10, new White())));
    }
    
    public WorldImage makeImage(){
       return new OverlayImages((enemyImage(enemies, enemies.size() - 1)),
               new OverlayImages(myShip.spaceshipImage(), shieldImage()));
    }
    
    
    public static void main (String[] args) {
        RunBattle game = new RunBattle(1, 5 ,5, 5, new Player(400, 500));
        
        game.bigBang(400, 500, 0.15);
    }
    
}
