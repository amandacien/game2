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
public class Bullet {
    
    Posn position;
    
    //Colors, (1 red) (2 blue) (3 yellow)
    int color;
    
    final int moveRate = 5;
    
    final int bulletRadius = 4;
    
    int screenWidth;
    int screenHeight;
    
    //1, going down, -1 going up
    int direction;
    
    Bullet(Posn position, int color, int screenWidth, int screenHeight, int direction){
        this.position = position;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.color = color; 
        this.direction = direction;
    }
    
    public Bullet onTick(){
        return new Bullet(new Posn(this.position.x, 
                                   this.position.y + moveRate * direction),
                            this.color, this.screenWidth, 
                            this.screenHeight, this.direction);
    }
    
    //true for out of bounds
    public boolean outOfBounds(){
        return (this.position.y < 0 || this.position.y > this.screenHeight);
    }
    
    
    //testing 
    
    static int checkOnTick;
    static int checkOutOfBounds;
    
    static Random rand = new Random();
    
    //returns a random int from 1 to x
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
            
            Posn randPosn = new Posn(randInt(0, 300), randInt(0, 600));
            
            Bullet bullet1 = new Bullet(randPosn, randInt(1,3), 300, 600, randInt);
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
            int randX = randInt(0,300);
            int randY = randInt(0, 600);
            Posn randPosn = new Posn(randX, randY);
            
            //screen dimmensions
            int screenWidth = 300;
            int screenHeight = 600;
            
            //creating the bullets to compare
            Bullet bullet1 = new Bullet(randPosn, randInt(1,3), screenWidth, screenHeight, randInt);
            Bullet bullet2 = bullet1; 
            
            int randTickInt = randInt(0, 50);
            
            for(int k = 0; k < randTickInt; k++){
                bullet2 = bullet2.onTick();
            }
            
            //what is potentially the bullet placement after the ticking
            int bulletAfterPosn = randY + (randInt * (randTickInt * bullet1.moveRate));
            
            
            //checks the cases of being bound
            if(randInt == 1 && bulletAfterPosn > screenHeight){
                if (!bullet2.outOfBounds()) {
                   throw new Exception("The bullet is actually out of bounds on the bottom");
                }
            } else if (randInt == -1 && bulletAfterPosn < 0) { 
                if (!bullet2.outOfBounds()){
                    throw new Exception("The bullet is actually out of bounds on the top");
                }
            } else {
                if (bullet2.outOfBounds()){
                    throw new Exception("The bullet is actually within the bounds of the screen: ");
                }
            }
            
            checkOutOfBounds++;
        }
    }
    
    public static void main(String[] args) throws Exception {
        checkOnTick();
        checkOutOfBounds();
        
        System.out.println("checkOnTick passed " + checkOnTick + " times");
        System.out.println("checkOutOfBounds passed " + checkOutOfBounds + " times");
    }
    
    
}


