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
public class Spaceship implements Ship{
    
    Posn position;
    
    //shields 
    int red;
    int blue; 
    int yellow; 
    
    //Spaceship dimmensions 
    final int shipHeight = 50;
    final int shipWidth = 30;
    
    //the movement of your ship 
    final int moveShip = 5;
    
    //Screen Dimmensions
    int screenWidth;
    int screenHeight;
    
    //Win Case, true if game is going on 
    boolean winCase; 
    
    Spaceship(int screenWidth, int screenHeight){
        position = new Posn(screenWidth/2, screenHeight - 10);
        red = 5;
        blue = 5;
        yellow = 5;
        winCase = true; 
    }
    
    Spaceship(Posn position, int red, int blue, int yellow, boolean winCase,
                int screenWidth, int screenHeight){
        this.position = position;
        this.red = red;
        this.blue = blue;
        this.yellow = yellow;
        this.winCase = winCase;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    public Spaceship onKey(String key){
        if (key.equals("left")) {
            return new Spaceship(new Posn(this.position.x - moveShip, this.position.y),   
                                this.red, this.blue, this.yellow, this.winCase, 
                                this.screenWidth, this.screenHeight).outOfBounds();
        } else if (key.equals("right")){
            return new Spaceship(new Posn(this.position.x + moveShip, this.position.y),   
                                this.red, this.blue, this.yellow, this.winCase, 
                                this.screenWidth, this.screenHeight).outOfBounds();
        } else {    
            return this;
        }
    }
    
    private Spaceship outOfBounds(){
        if (this.position.x < shipWidth/2){
            return new Spaceship(new Posn(shipWidth/2, this.position.y),   
                                this.red, this.blue, this.yellow, this.winCase, 
                                this.screenWidth, this.screenHeight);
        } else if (this.position.x > screenWidth - (shipWidth/2)) {
            return new Spaceship(new Posn(screenWidth - (shipWidth/2), this.position.y),   
                                this.red, this.blue, this.yellow, this.winCase, 
                                this.screenWidth, this.screenHeight);
        } else {
            return this;
        }
    }
    
    public Ship onTick(){
        return this;
    }    
    
    
    //testing
    int checkOnKey = 0;
    static Random rand = new Random();
    
    //returns a random int from 1 to x
    private static int randInt(int x) {
        return rand.nextInt(x) + 1 ;
    }
    
    private void checkOnKey(Spaceship myShip) throws Exception {
        Spaceship myShip2 = myShip;
        
        for (int i = 0; i < 1000; i++) {
            
        }
       
    }
    
    private void testOnKey(String k) {
        this.onKey(k).checkOutOfBounds();
    }
    
    public static void main(String[] args) {
        Spaceship t1 = new Spaceship();
        t1.testOnKey("left");
    }
    }
    
     
}
