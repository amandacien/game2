/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game2;
import java.awt.Color;
import java.util.Random;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
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
    
    Bullet(Posn position, int color, int screenWidth, int ScreenHeight, int direction){
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
    
    public boolean outOfBounds(){
        return (this.position.y < this.bulletRadius/2) || 
                (this.position.y > this.screenHeight - this.bulletRadius/2);
    }
    
    
    //testing 
    
    static int checkOnTick;
    
    
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
            
            Posn randPosn = new Posn(randInt(15, 285), randInt(50, 600));
            
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
    
    public static void main(String[] args) throws Exception {
        checkOnTick();
        
        System.out.println("checkOnTick passed " + checkOnTick + " times");
    }
    
    
}


