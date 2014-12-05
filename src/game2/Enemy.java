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
    
    //Win Case, true if game is going on 
    boolean isHit; 
    
    //moveCases: 
    // 1 : left 
    // 2 : right
    // 3 : leftDown
    // 4 : rightDown
    int moveCase;
    
    public Enemy (int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = new Posn(this.screenWidth - this.shipWidth/2, this.shipHeight/2);
        this.isHit = false;
        this.moveCase = 1;
    }
    
    private Enemy(int screenWidth, int screenHeight, Posn position, boolean isHit, int moveCase){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = position;
        this.isHit = isHit;
        this.moveCase = moveCase;
    }
    
    public Enemy onTick() {
        int newMoveCase = this.setMoveCase();
        
        if (this.moveCase == 1){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x - this.moveRate, this.position.y), 
                    this.isHit, newMoveCase);
        } else if (this.moveCase == 2){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x + this.moveRate, this.position.y), 
                    this.isHit, newMoveCase);
        } else if (this.moveCase == 3){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y + this.shipHeight), 
                    this.isHit, newMoveCase);
        } else /* moveCase == 4 */{
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y + this.shipHeight), 
                    this.isHit, newMoveCase);
        }
    }
    
    private int setMoveCase() {
        if (this.moveCase == 1){
            if (this.position.x - this.moveRate < 0 ) {
                return 3;
            } else {
                return 1; 
            }
        } else if (this.moveCase == 2){
            if (this.position.x + this.moveRate > this.screenWidth){
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
}
