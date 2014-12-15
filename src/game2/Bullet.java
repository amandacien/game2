/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game2;
import java.awt.Color;
import java.util.*;
import javalib.colors.IColor;
import javalib.colors.Yellow;
import javalib.worldimages.*;

/**
 *
 * @author AmandaMa
 */
public class Bullet {
    
    static IColor col = new Yellow();
    
    Posn position;
    
    //Colors, (1 red) (2 blue) (3 yellow)
    //        (4 white - bullet hitting the enemy)
    int color;
    
    final int moveRate = 5;
    
    final int bulletDiameter = 8;
    
    int screenWidth;
    int screenHeight;
    
    //1, going down, -1 going up
    int direction;
    
    //needs to know if it hits an object, it disappears 
    boolean onScreen;
    
    public Bullet(Posn position, int color, int screenWidth, 
            int screenHeight, int direction, boolean onScreen){
        this.position = position;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.color = color; 
        this.direction = direction;
        this.onScreen = onScreen;
    }
    
    public Bullet onTick(){
        return new Bullet(new Posn(this.position.x, 
                                   this.position.y + moveRate * direction),
                            this.color, this.screenWidth, 
                            this.screenHeight, this.direction, this.onScreen);
    }
    
    //returns a new bullet saying if the bullet is still on the screen
    public Bullet outOfBounds(){
        if (this.position.y < 0 || this.position.y > this.screenHeight) {
            return new Bullet(this.position,this.color, this.screenWidth, 
                            this.screenHeight, this.direction, false);
        } else {
            return this;
        }
    }
    
    //The bullet only has to stop if it hits an enemy, becuase once the player
    //is hit, the game is over 
    public Bullet isHit(Enemy enemy){
        if (this.color == 4){
            if (this.position.x < enemy.position.x + enemy.shipWidth/2 &&
                this.position.x > enemy.position.x - enemy.shipWidth/2 &&
                this.position.y > enemy.position.y - enemy.shipHeight/2 &&
                this.position.y < enemy.position.y + enemy.shipHeight/2){
                    return new Bullet(this.position,this.color, this.screenWidth, 
                            this.screenHeight, this.direction, false);
            } else {
                return this;
            }
        } else {
            return this;
        }
    }
    
    public WorldImage bulletImage() {
        /*if (this.color == 1) {
            return new FromFileImage(this.position, "redBullet.png");
        } else if (this.color == 2) {
            return new FromFileImage(this.position, "blueBullet.png");
        } else if (this.color == 3) {
            return new FromFileImage(this.position, "yellowBullet.png");
        } else {
            return new FromFileImage(this.position, "whiteBullet.png");
        }*/ 
        return new DiskImage(this.position, this.bulletDiameter, this.col);
    }
    
    //testing 
    static int testScreenWidth = 300;
    static int testScreenHeight = 600;
    static int checkOnTick;
    static int checkOutOfBounds;
    static int checkBulletIsHit;
    static Random rand = new Random();
    
    //returns a random int from start with a range of range 
    private static int randInt(int start, int range) {
        return rand.nextInt(range) + start ;
    }
    
    private static void checkOnTick() throws Exception {
        for (int i = 0; i < 1000; i++) {
            
            int randInt = randInt(0, 2);
            
            if (randInt == 0) {
                randInt = -1 ;
            } else {
                randInt = 1;
            }
            
            Posn randPosn = new Posn(randInt(0, testScreenWidth), randInt(0, testScreenHeight));
            
            Bullet bullet1 = new Bullet(randPosn, randInt(1,3), testScreenWidth, 
                    testScreenHeight, randInt, true);
            Bullet bullet2 = bullet1.onTick();
            
            if(randInt == 1){
                if (bullet1.position.y + bullet1.moveRate != bullet2.position.y) {
                   throw new Exception("The bullet isn't moving correctly down");
                }
            } else {
                if (bullet1.position.y - bullet1.moveRate != bullet2.position.y){
                    throw new Exception("The bullet isn't moving correctly up");
                }
            }
            checkOnTick++;
        }
    }
    
    private static void checkOutOfBounds() throws Exception {
        for (int i = 0; i < 1000; i++) {
            
            //decides the trajectory of the bullet
            int randInt = randInt(0, 2);
            if (randInt == 0) {
                randInt = -1 ;
            } else {
                randInt = 1;
            }
            
            //random Positions
            int randX = randInt(0,testScreenWidth);
            int randY = randInt(0, testScreenHeight);
            Posn randPosn = new Posn(randX, randY);
            
            
            //creating the bullets to compare
            Bullet bullet1 = new Bullet(randPosn, randInt(1,3), 
                    testScreenWidth, testScreenHeight, randInt, true);
            Bullet bullet2 = bullet1; 
            
            int randTickInt = randInt(0, 50);
            
            for(int k = 0; k < randTickInt; k++){
                bullet2 = bullet2.onTick().outOfBounds();
            }
            
            //what is potentially the bullet placement after the ticking
            int bulletAfterPosn = randY + (randInt * (randTickInt * bullet1.moveRate));
            
            
            //checks the cases of being bound
            if(randInt == 1 && bulletAfterPosn > testScreenHeight){
                if (bullet2.onScreen) {
                   throw new Exception("The bullet is actually out of bounds on the bottom");
                }
            } else if (randInt == -1 && bulletAfterPosn < 0) { 
                if (bullet2.onScreen){
                    throw new Exception("The bullet is actually out of bounds on the top");
                }
            } else {
                if (!bullet2.onScreen){
                    throw new Exception("The bullet is actually within the bounds of the screen: ");
                }
            }
            
            checkOutOfBounds++;
        }
    }
    
    
    public static void checkBulletIsHit() throws Exception {
        for (int i = 0; i < 1000; i++){ 
            
            //creating a randomly placed spaceship
            Spaceship sp1 = new Spaceship(testScreenWidth, testScreenHeight);
            int spX = randInt(sp1.shipWidth/2, sp1.screenWidth - sp1.shipWidth);
            Spaceship sp2 = new Spaceship(new Posn(spX, sp1.position.y), sp1.red, sp1.blue,
                    sp1.yellow, sp1.winCase, sp1.screenWidth, sp1.screenHeight);
            
            //creating a randomly placed enemy
            Enemy en1 = new Enemy(testScreenWidth, testScreenHeight);
            int enX = randInt(en1.shipWidth/2, en1.screenWidth - en1.shipWidth);
            int enY = randInt(en1.shipHeight/2, en1.screenHeight - en1.shipHeight);
            Enemy en2 = new Enemy(en1.screenWidth, en1.screenHeight, 
                    new Posn(enX, enY), en1.isHit, randInt(1,4));
            
            Bullet bullet = sp1.makeBullet();
            
            int randTick = randInt(0, (testScreenHeight - sp1.shipHeight)/bullet.moveRate);
            
            for (int j = 0; j < randTick; j++) {
                bullet = bullet.onTick();
                en2 = en2.onTick();
                
                bullet = bullet.isHit(en2);
                en2 = en2.isHit(bullet);
            }
            
            if (bullet.onScreen) {
                if (en2.isHit) {
                    throw new Exception("The bullet is still on the screen," + 
                            " which means the enemy was not hit");
                }
            } else {
                if (!en2.isHit) {
                    throw new Exception("The bullet is offscreen" + 
                            " which means the enemy was hit");
                }
            }
            
            checkBulletIsHit++;
        }
    }
    
    public static void main(String[] args) throws Exception {
        checkOnTick();
        checkOutOfBounds();
        checkBulletIsHit();
        
        System.out.println("checkOnTick passed " + checkOnTick + " times");
        System.out.println("checkOutOfBounds passed " + checkOutOfBounds + " times");
        System.out.println("checkBulletIsHit passed " + checkBulletIsHit + " times");
        
    }
    
    
}


