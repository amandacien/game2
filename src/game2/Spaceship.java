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
        this.position = new Posn(screenWidth/2, screenHeight - 10);
        this.red = 5;
        this.blue = 5;
        this.yellow = 5;
        this.winCase = true; 
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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
    
    
    
    
    
    
    
    //Testing Code
    static int checkOnKey = 0;
    static int checkMoveShip = 0;
    static Random rand = new Random();
    
    //returns a random int from 1 to x
    private static int randInt(int x) {
        return rand.nextInt(x) + 1 ;
    }
    
    private void checkOnKey() throws Exception {
        
        Spaceship myShip2 = this;
        
        for (int i = 0; i < 1000; i++) {
            int randInt = randInt(2);
            
            if (randInt == 1) {
                myShip2 = myShip2.onKey("right");
            } else {
                myShip2 = myShip2.onKey("left");
            }
            
            if (myShip2.position.x < myShip2.shipWidth/2) {
                throw new Exception("The ship has gone off the left bound");
            }
            if (myShip2.position.x > myShip2.screenWidth - myShip2.shipWidth/2){
                throw new Exception("This ship has gone off the right bound");
            }
            
            checkOnKey++;
        }
    }
    
    
    private void checkMoveShip() throws Exception {
        Spaceship myShip1 = this;
        Spaceship myShip2 = myShip1;
        
        for (int i = 0; i < 1000; i++) {
            int randInt = randInt(2);
            
            if (randInt == 1) {
                myShip2 = myShip2.onKey("right");
            } else {
                myShip2 = myShip2.onKey("left");
            }
            
            System.out.println(myShip1.position.x + ", " + myShip2.position.x);
            
            if(randInt == 2){
                if (myShip1.position.x < myShip1.shipWidth/2 + myShip1.moveShip) {
                    if (myShip2.position.x != myShip1.shipWidth/2){
                        throw new Exception("The ship isn't moving correctly" +
                                "when moving at the left bound");
                    }
                } else {
                    if (myShip1.position.x != myShip2.position.x + myShip1.moveShip){
                        throw new Exception("The ship isn't moving correctly " +
                                "when moving left");
                    }
                }
            } else {
                if (myShip1.position.x > (myShip1.screenWidth - 
                        myShip1.moveShip - myShip1.shipWidth/2)) {
                    if (myShip2.position.x != myShip1.screenWidth - myShip1.shipWidth/2){
                        throw new Exception("The ship isn't moving correctly" +
                                "when moving at the right bound");
                    }
                } else {
                    if (myShip1.position.x != myShip2.position.x - myShip1.moveShip){
                        throw new Exception("The ship isn't moving correctly " +
                                "when moving right");
                    }
                }
            }
            
            myShip1 = myShip2;
            
            checkMoveShip++;
        }
    }
    
    
    
    public static void main(String[] args) throws Exception {
        Spaceship t1 = new Spaceship(300, 600);
        
        t1.checkOnKey();
        t1.checkMoveShip();
        
        System.out.println(checkOnKey);
        System.out.println(checkMoveShip);
        
    }
    
    
     
}
