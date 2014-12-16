/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game2;
import java.util.*;
import javalib.worldimages.*;

/**
 *
 * @author AmandaMa
 */
public class Player {
    
    Posn position;
    
    //to tell what image to return in moving right, left, forward, and backward
    int direction;
    
    //to tell in which gate the person entered so they can't leave that same gate
    // 1 = up
    // 2 = left 
    // 3 = down 
    // 4 = right 
    int entered; 
    
    
    
    //tells if the object is open or not - allows you to leave the room 
    boolean openedObject;
    
    int screenWidth;
    int screenHeight;
    
    int level;
    
    //shields 
    int red;
    int blue;
    int yellow; 
    
    final int playerWidth = 50;
    final int playerHeight = 50;
    final int moveCase = 5;
    final int doorSize = 100;
    
    boolean inBattle;
    
    
    
    public Player(int screenHeight, int screenWidth, Posn position, int entered, 
            int direction, boolean openedObject, int level, int red, int blue, 
            int yellow, boolean inBattle){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.position = position;
        this.entered = entered; 
        this.direction = direction;
        this.openedObject = openedObject;
        this.level = level;
        this.red = red;
        this.blue = blue;
        this.yellow = yellow;
        this.inBattle = inBattle;
    }
    
    public Player onKey(String key){
        if (key.equals("up")) {
            return new Player(this.screenHeight, this.screenWidth,
                    new Posn(this.position.x, this.position.y - this.moveCase),
                    this.entered, 1, this.openedObject, this.level,
                    this.red, this.blue, this.yellow, this.inBattle).leavingRoom();
        } else if (key.equals("left")) {
            return new Player(this.screenHeight, this.screenWidth,
                    new Posn(this.position.x - this.moveCase, this.position.y),
                    this.entered, 2, this.openedObject, this.level,
                    this.red, this.blue, this.yellow, this.inBattle).leavingRoom();
        } else if (key.equals("down")) {
            return new Player(this.screenHeight, this.screenWidth,
                    new Posn(this.position.x, this.position.y + this.moveCase),
                    this.entered, 3, this.openedObject, this.level,
                    this.red, this.blue, this.yellow, this.inBattle).leavingRoom();
        } else if (key.equals("right")) {
            return new Player(this.screenHeight, this.screenWidth,
                    new Posn(this.position.x + this.moveCase, this.position.y),
                    this.entered, 4, this.openedObject, this.level,
                    this.red, this.blue, this.yellow, this.inBattle).leavingRoom();
        } else if (key.equals("Space")) {
            return new Player(this.screenHeight, this.screenWidth,this.position,
                    this.entered, this.direction, true, this.level,
                    this.red, this.blue, this.yellow, this.inBattle);
        } else {
            return this;
        }
    }
    
    public Player eventConsequences(MysteryObj mysteryObj){
        if (mysteryObj.event == 0){
            return new Player(this.screenHeight, this.screenWidth, this.position,
                    this.entered, this.direction, this.openedObject, this.level,
                    this.red, this.blue, this.yellow, true);
        } else if (mysteryObj.event == 1) {
            return new Player(this.screenHeight, this.screenWidth, this.position,
                    this.entered, this.direction, this.openedObject, this.level,
                    5, this.blue, this.yellow, this.inBattle);
        } else if (mysteryObj.event == 2) {
            return new Player(this.screenHeight, this.screenWidth, this.position,
                    this.entered, this.direction, this.openedObject, this.level,
                    this.red, 5, this.yellow, this.inBattle);
        } else if (mysteryObj.event == 3) {
            return new Player(this.screenHeight, this.screenWidth, this.position,
                    this.entered, this.direction, this.openedObject, this.level,
                    this.red, this.blue, 5, this.inBattle);
        } else {
            return this;
        }
    }
    
    public Player leavingRoom() {
        
        //Creating the players based on their edges that aren't allowed to move further 
        Player edge1 = new Player(this.screenHeight, this.screenWidth,
                        new Posn(this.position.x, 0 + this.playerHeight/2),
                        this.entered, this.direction, this.openedObject, this.level,
                        this.red, this.blue, this.yellow, this.inBattle);
        
        Player edge3 = new Player(this.screenHeight, this.screenWidth,
                        new Posn(this.position.x, this.screenHeight - this.playerHeight/2),
                        this.entered, this.direction, this.openedObject, this.level, 
                        this.red, this.blue, this.yellow, this.inBattle);
        
        Player edge4 = new Player(this.screenHeight, this.screenWidth,
                        new Posn(0 + this.playerWidth/2, this.position.y),
                        this.entered, this.direction, this.openedObject, this.level, 
                        this.red, this.blue, this.yellow, this.inBattle);
        
        Player edge2 = new Player(this.screenHeight, this.screenWidth,
                        new Posn(this.screenWidth - this.playerWidth/2, this.position.y),
                        this.entered, this.direction, this.openedObject, this.level, 
                        this.red, this.blue, this.yellow, this.inBattle);
        
        //creating the booleans telling whether they're in the range of a door 
        boolean topBottomLeave = (this.position.x < this.screenWidth/2 + this.doorSize/2 - this.playerWidth/2) 
                && (this.position.x >  this.screenWidth/2 - this.doorSize/2 + this.playerWidth/2);
        
        boolean leftRightLeave = (this.position.y < this.screenHeight/2 - this.doorSize/2 + this.playerHeight/2)
                && (this.position.y > this.screenHeight/2 + this.doorSize/2 - this.playerHeight/2);
        
        //if the opject is opened
        if (this.openedObject) {
            //if it's trying to leave above
            if (this.position.y - this.playerHeight/2 < 0) /*up, 1*/{
                if (this.entered == 1) {
                    //can't leave because it's where the player entered 
                    return edge1;
                } else if (topBottomLeave) {
                    //if the player is leaving through a door, returns a new player in a new "room"
                    return new Player(this.screenHeight, this.screenWidth,
                            new Posn(this.position.x, this.screenHeight - this.playerHeight/2),
                            3, this.direction, false, this.level, this.red, this.blue, 
                            this.yellow, this.inBattle);
                } else {
                    //if it's not leaving through the door, it stays put aginst the edge 
                    return edge1;
                }
            } else if (this.position.y + this.playerWidth/2 > this.screenWidth) /*down, 3*/ {
                if (this.entered == 3) {
                    return edge3;
                } else if (topBottomLeave) {
                    new Player(this.screenHeight, this.screenWidth,
                            new Posn(this.position.x, 0 + this.playerHeight/2),
                            1, this.direction, false, this.level, this.red, this.blue, 
                            this.yellow, this.inBattle);
                } else {
                    return edge3;
                }
            } else if (this.position.x - this.playerHeight/2 < 0) /*left, 4*/{ 
                if (this.entered == 4) {
                    return edge4;
                } else if (leftRightLeave) {
                    new Player(this.screenHeight, this.screenWidth,
                            new Posn(this.screenWidth - this.playerWidth/2, this.position.y),
                            2, this.direction, false, this.level, this.red, this.blue, 
                            this.yellow, this.inBattle);
                } else {
                    return edge4;
                }
            } else if (this.position.x + this.playerHeight/2 > this.screenHeight) /*right, 2*/ {
                if (this.entered == 2) {
                    return edge2;
                } else if (leftRightLeave) {
                    new Player(this.screenHeight, this.screenWidth,
                            new Posn(0 + this.playerWidth/2, this.position.y),
                            2, this.direction, false, this.level, this.red, this.blue, 
                            this.yellow, this.inBattle);
                } else {
                    return edge2;
                }
            } else {
                return this;
            }
        } else if (this.position.y - this.playerHeight/2 < 0) /*up, 1*/{
            return edge1;
        } else if (this.position.y + this.playerWidth/2 > this.screenWidth) /*down, 3*/ {
            return edge3;
        } else if (this.position.x - this.playerHeight/2 < 0) /*left, 4*/{ 
            return edge4;
        } else if (this.position.x + this.playerHeight/2 > this.screenHeight) /*right, 2*/ {
            return edge2;
        } else {
            return this;
        }
    }
    
    
}


