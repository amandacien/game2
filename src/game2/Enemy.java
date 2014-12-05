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
        if (this.moveCase == 1){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y), this.isHit, this.moveCase);
        } else if (this.moveCase == 2){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y), this.isHit, this.moveCase);
        } else if (this.moveCase == 3){
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y), this.isHit, this.moveCase);
        } else /* moveCase == 4 */{
            return new Enemy(this.screenWidth, this.screenHeight, 
                    new Posn(this.position.x,this.position.y), this.isHit, this.moveCase);
        }
    }
    
    
    
    
    
    public Enemy onKey(String key){
        return this;
    }
}
