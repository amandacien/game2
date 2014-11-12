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
    
    public Bullet moveBullet(){
        return new Bullet(new Posn(this.position.x, 
                                   this.position.y + moveRate * direction),
                            this.color, this.screenWidth, 
                            this.screenHeight, this.direction);
    }
}


