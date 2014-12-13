/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game2;
import java.util.Random;
import javalib.worldimages.*;

/**
 *
 * @author AmandaMa
 */
public class Enemy implements Ship {
    
    Posn position;
    
    //Spaceship dimmensions 
    final int shipHeight = 10;
    final int shipWidth = 10;
    
    //the movement of your ship 
    final int moveShip = 15;
    
    //Screen Dimmensions
    int screenWidth;
    int screenHeight;
    
    
    //Movement rate 
    int moveRate = 10;
    
    //Win Case, true if the ship is hit
    boolean isHit; 
    
    //moveCases: 
    // 1 : left 
    // 2 : right
    // 3 : leftDown
    // 4 : rightDown
    int moveCase;
    
    
    //random generator stuff
    static Random rand = new Random();
    
    private static int randInt(int start, int range) {
        return rand.nextInt(range) + start ;
    }
    
    
    public Enemy (int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = new Posn(this.screenWidth - this.shipWidth/2, this.shipHeight/2);
        this.isHit = false;
        this.moveCase = 1;
    }
    
    public Enemy(int screenWidth, int screenHeight, Posn position, boolean isHit, int moveCase){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = position;
        this.isHit = isHit;
        this.moveCase = moveCase;
    }
    
    public Enemy onTick() {
        int nextMoveCase = this.setMoveCase();
        
        if (nextMoveCase == 1){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x - this.moveRate, this.position.y), 
                    this.isHit, nextMoveCase);
        } else if (nextMoveCase == 2){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x + this.moveRate, this.position.y), 
                    this.isHit, nextMoveCase);
        } else if (nextMoveCase == 3){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y + this.shipHeight), 
                    this.isHit, nextMoveCase);
        } else /* moveCase == 4 */{
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y + this.shipHeight), 
                    this.isHit, nextMoveCase);
        }
    }
    
    private int setMoveCase() {
        if (this.moveCase == 1){
            if (this.position.x - this.moveRate < 0 + this.shipWidth/2) {
                return 3;
            } else {
                return 1; 
            }
        } else if (this.moveCase == 2){
            if (this.position.x + this.moveRate > this.screenWidth - this.shipWidth/2){
                return 4;
            } else {
                return 2;
            }
        } else if (this.moveCase == 3){
            return 2;
        } else /* moveCase == 4 */{
            return 1;
        }
    }
    
    public Enemy onKey(String key){
        return this;
    }
   
    public Enemy isHit(Bullet bullet) {
        if (bullet.color == 4){
            if (bullet.position.x < this.position.x + this.shipWidth/2 &&
                bullet.position.x > this.position.x - this.shipWidth/2 &&
                bullet.position.y > this.position.y - this.shipHeight/2 &&
                bullet.position.y < this.position.y + this.shipHeight/2){
                    return new Enemy(this.screenWidth, this.screenHeight, 
                            new Posn(this.position.x,this.position.y + this.shipHeight),
                            true, this.moveCase);
            } else {
                return this;
            }
        } else {
            return this;
        }
    }
    
    public Bullet makeBullet(){
        return new Bullet(new Posn(this.position.x, this.position.y + this.shipHeight/2), 
                randInt(1, 3), this.screenWidth, this.screenHeight, 1, true);
    }
    
    
    //Testing Code 
    
    static int testScreenWidth = 300;
    static int testScreenHeight = 600;
    static int testMovingCorrectly = 0;
    static int testIsHit = 0;
    static int testMakeBullet = 0;
    
    private static void testMovingCorrectly() throws Exception {
        for (int i = 0; i < 1000; i++){
            int moveCase = randInt(1, 2);

            Enemy en1 = new Enemy(testScreenWidth,testScreenHeight);

            int randPosX = randInt(en1.shipWidth/2, en1.screenWidth - en1.shipWidth);
            int randPosY = randInt(en1.shipHeight/2, en1.screenHeight - en1.shipHeight);

            Enemy en2 = new Enemy(testScreenWidth,testScreenHeight, new Posn(randPosX, randPosY), en1.isHit, moveCase);
            Enemy en3 = en2.onTick();
            
            if (moveCase == 1) { 
                if (randPosX - en2.moveRate < en2.shipWidth/2){
                    if (en3.moveCase != 3){
                        throw new Exception("When unable to move further to " + 
                                "the left, the move case should be 3 ");
                    } 
                    if (en3.position.x != en2.position.x){
                        throw new Exception("The x should not change when moving down on the left");
                    }
                    if (en3.position.y != en2.position.y + en2.shipHeight) {
                        throw new Exception("The y should be the shipWidth more than what it was");
                    }
                } else {
                    if (en3.moveCase != 1){
                        throw new Exception("The ship can go futher to the left, the case shouldn't change");
                    }
                    if (en3.position.x != en2.position.x - en2.moveRate){
                        throw new Exception("The ship isn't moving to the left correctly");
                    }
                    if (en3.position.y != en2.position.y) {
                        throw new Exception("The ship isn't supposed to move y position");
                    }
                }
            } else {
                if (randPosX + en2.moveRate > en2.screenWidth - en2.shipWidth/2){
                    if (en3.moveCase != 4){
                        throw new Exception("When unable to move further to " + 
                                "the right, the move case should be 4 ");
                    } 
                    if (en3.position.x != en2.position.x){
                        throw new Exception("The x should not change when moving down on the right");
                    }
                    if (en3.position.y != en2.position.y + en2.shipHeight) {
                        throw new Exception("The y should be the shipWidth more than what it was");
                    }
                } else {
                    if (en3.moveCase != 2){
                        throw new Exception("The ship can go futher to the right, the case shouldn't change");
                    }
                    if (en3.position.x != en2.position.x + en2.moveRate){
                        throw new Exception("The ship isn't moving to the right correctly");
                    }
                    if (en3.position.y != en2.position.y) {
                        throw new Exception("The ship isn't supposed to move y positions (2)");
                    }
                }
            }
            testMovingCorrectly++;
        }
    }
    
    
    private static void testIsHit() throws Exception {
        for (int i = 0; i < 1000; i++) {
            
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
            
            //creating a bullet ticked to a random number of tick 
            int randTick = randInt(1, 150);
            Bullet bullet = sp1.makeBullet();
            
            for (int j = 0; j <= randTick; j++) { 
                bullet = bullet.onTick();
            }
            
            Enemy en3 = en2.isHit(bullet);
            
            boolean hitCase = (bullet.position.x > en3.position.x - en3.shipWidth/2 &&
                    bullet.position.x < en3.position.x + en3.shipWidth/2 &&
                    bullet.position.y > en3.position.y - en3.shipHeight/2 && 
                    bullet.position.y < en3.position.y + en3.shipHeight/2);
            
            if (hitCase) {
                if (bullet.color == 4) {
                    if (!en3.isHit) {
                        throw new Exception("Your ship should should have been hit");
                    }
                } else {
                    throw new Exception("Your spaceship isn't " + 
                            " making bullets correctly");
                }
            } else {
                if (!en3.equals(en2)) {
                    throw new Exception("The enemy was not hit and therefore " + 
                            "should stay the same");
                }
            }
            testIsHit++;
        }
    }
    
    private static void testMakeBullet() throws Exception{
        for (int i = 0; i < 1000; i++) {
            Enemy en1 = new Enemy(testScreenWidth, 600);
            int randPosX = randInt(en1.shipWidth/2, en1.screenWidth - en1.shipWidth);
            int randPosY = randInt(en1.shipHeight/2, en1.screenHeight - en1.shipHeight);
            Enemy en2 = new Enemy(en1.screenWidth, en1.screenHeight, 
                    new Posn(randPosX, randPosY), en1.isHit, randInt(1,4));
            
            Bullet bullet = en2.makeBullet();
            
            if(bullet.position.x != en2.position.x){
                throw new Exception("It's not creating at the correct X point");
            } 
            if(bullet.position.y != en2.position.y + en2.shipHeight/2){
                throw new Exception("It's not creating at the correct Y point");
            }
            testMakeBullet++;
        }
    }
        
    public static void main(String[] args) throws Exception {
            testMovingCorrectly();
            testIsHit();
            testMakeBullet();
            
            
            System.out.println("testMovingCorrectly ran " + testMovingCorrectly 
                    + " times");
            System.out.println("testIsHit ran " + testIsHit 
                    + " times");
            System.out.println("testMakeBullet ran " + testMakeBullet 
                    + " times");
            
    }
    
}
