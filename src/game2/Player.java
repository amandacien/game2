/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game2;
import java.util.*;
import javalib.colors.Yellow;
import javalib.worldimages.*;

/**
 *
 * @author AmandaMa
 */
public class Player {
    
    
    final int playerWidth = 50;
    final int playerHeight = 50;
    final int moveCase = 10;
    final int doorSize = 100;
    
    final static int myW = RunMaze.screenWidth;
    
    int screenWidth;
    int screenHeight;
    
    Posn position;
    
    //to tell in which gate the person entered so they can't leave that same gate
    // 1 = up
    // 2 = left 
    // 3 = down 
    // 4 = right 
    int entered; 
    
    //to tell what image to return in moving right, left, forward, and backward
    int direction;
    
    //#t spacebar is pressed, #f otherwise
    boolean spacebar;
 
    int level;
    
    //shields 
    int red;
    int blue;
    int yellow; 
    
    boolean inBattle;
    
    //tells which event is happening 
    int eventNum;
    
    static Random rand = new Random();
    
    private static int randInt(int start, int range) {
        return rand.nextInt(range) + start ;
    }
    
    //player for level 1
    public Player(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = new Posn(screenWidth/2, 0 + playerHeight/2);
        this.entered = 1;
        this.direction = 3;
        this.spacebar = false;
        this.level = 1;
        this.red = 5;
        this.blue = 5;
        this.yellow = 5;
        this.inBattle = false;
        this.eventNum = 6;
    }
    
    
    public Player(int screenWidth, int screenHeight, Posn position, int entered, 
            int direction, boolean spacebar, int level, int red, int blue, int yellow,
            boolean inBattle, int eventNum){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.position = position;
        this.entered = entered; 
        this.direction = direction;
        this.spacebar = spacebar;
        this.level = level;
        this.red = red;
        this.blue = blue;
        this.yellow = yellow;
        this.inBattle = inBattle;
        this.eventNum = eventNum;
        
    }
    
    public Player setDirectionPosn(int direction, Posn position){
        return new Player(this.screenWidth, this.screenHeight,position,
                    this.entered, direction, this.spacebar, this.level,
                    this.red, this.blue, this.yellow, 
                    this.inBattle, this.eventNum);
    }
   
    
    public Player onKey(String key){
        if (key.equals("up")) {
            return this.setDirectionPosn(1, 
                    new Posn(this.position.x, this.position.y - this.moveCase)).leavingRoom();
        } else if (key.equals("right")) {
            return this.setDirectionPosn(2, 
                    new Posn(this.position.x + this.moveCase, this.position.y)).leavingRoom();
        } else if (key.equals("down")) {
            return this.setDirectionPosn(3,
                    new Posn(this.position.x, this.position.y + this.moveCase)).leavingRoom();
        } else if (key.equals("left")) {
            return this.setDirectionPosn(4, 
                    new Posn(this.position.x - this.moveCase, this.position.y)).leavingRoom();
        } else if (key.equals(" ")) {
            System.out.println("they clicked space and spacebar is " + this.spacebar);
            if (!this.spacebar){
                
                //if the spacebar hasn't been hit when you hit it, it gives you a 
                //random object 
                
                int choice = randInt(0, 3);
                
                if (choice == 0){
                    return new Player(this.screenWidth, this.screenHeight, this.position,
                            this.entered, this.direction, true, this.level,
                            this.red, this.blue, this.yellow, true, 0);
                } else if (choice == 1){
                    int shieldPick = randInt(0,3);
                    if (shieldPick == 0){
                        return new Player(this.screenWidth, this.screenHeight, this.position,
                            this.entered, this.direction, true, this.level,
                            5, this.blue, this.yellow, this.inBattle, 1);
                    } else if (shieldPick == 1) {
                        return new Player(this.screenWidth, this.screenHeight, this.position,
                            this.entered, this.direction, true, this.level,
                            this.red, 5, this.yellow, this.inBattle, 2);
                    } else {
                        return new Player(this.screenWidth, this.screenHeight, this.position,
                            this.entered, this.direction, true, this.level,
                            this.red, this.blue, 5, this.inBattle, 3);
                    } 
                } else {
                    return new Player(this.screenWidth, this.screenHeight, this.position,
                            this.entered, this.direction, true, this.level,
                            this.red, this.blue, this.yellow, this.inBattle, 4);
                }
            } else {
                //if the spacebar was already pressed 
                return new Player(this.screenWidth, this.screenHeight, this.position,
                            this.entered, this.direction, this.spacebar, this.level,
                            this.red, this.blue, this.yellow, this.inBattle, 5);
            }
        } else {
            //if anything else is pressed 
                System.out.println("they clicked '" + key + "' and i ignored it :(");
            return this;
        }
        
    }
    
    
    public Player leavingRoom() {
        
        Posn pos1 = new Posn(this.position.x, 0 + this.playerHeight/2);
        Posn pos2 = new Posn(this.screenWidth - this.playerWidth/2, this.position.y);
        Posn pos3 = new Posn(this.position.x, this.screenHeight - this.playerHeight/2);
        Posn pos4 = new Posn(0 + this.playerWidth/2, this.position.y);
                        
         
        //Creating the players based on their edges that aren't allowed to move further 
        Player edge1 = new Player(this.screenWidth, this.screenHeight, pos1,
                        this.entered, this.direction, this.spacebar, this.level,
                        this.red, this.blue, this.yellow, this.inBattle, this.eventNum);
        
        Player edge2 = new Player(this.screenWidth, this.screenHeight,pos2,
                        this.entered, this.direction, this.spacebar, this.level, 
                        this.red, this.blue, this.yellow, this.inBattle, this.eventNum);
        
        Player edge3 = new Player(this.screenWidth, this.screenHeight, pos3,
                        this.entered, this.direction, this.spacebar, this.level, 
                        this.red, this.blue, this.yellow, this.inBattle, this.eventNum);
        
        Player edge4 = new Player(this.screenWidth, this.screenHeight, pos4,
                        this.entered, this.direction, this.spacebar, this.level, 
                        this.red, this.blue, this.yellow, this.inBattle, this.eventNum);
        
        
        //creating the booleans telling whether they're in the range of a door 
        boolean topBottomLeave = 
                ((this.position.x < this.screenWidth/2 + this.doorSize/2 - this.playerWidth/2) 
                && (this.position.x >  this.screenWidth/2 - this.doorSize/2 + this.playerWidth/2));
        
        boolean leftRightLeave = 
                ((this.position.y < this.screenHeight/2 - this.doorSize/2 + this.playerHeight/2)
                && (this.position.y > this.screenHeight/2 + this.doorSize/2 - this.playerHeight/2));
        
        boolean passedRight = this.position.x + this.playerWidth/2 > this.screenWidth;
        boolean passedLeft = this.position.x - this.playerWidth/2 < 0;
        boolean passedUp = this.position.y - this.playerHeight/2 < 0;
        boolean passedBottom = this.position.y + this.playerHeight/2 > this.screenHeight;
        
        //if the spacebar has been pressed 
        if (this.spacebar) {
            //if it's trying to leave above
            if (passedUp) /*up, 1*/{
                if (this.entered == 1) {
                    //can't leave because it's where the player entered 
                    return edge1;
                } else if (topBottomLeave) {
                    //if the player is leaving through a door, returns a new player in a new "room"
                    return this.setEntrPos(pos3, 3);
                } else {
                    //if it's not leaving through the door, it stays put aginst the edge 
                    return edge1;
                }
            } else if (passedBottom) /*down, 3*/ {
                if (this.entered == 3) {
                    return edge3;
                } else if (topBottomLeave) {
                    return this.setEntrPos(pos1, 1);
                } else {
                    return edge3;
                }
            } else if (passedLeft) /*left, 4*/{ 
                if (this.entered == 4) {
                    return edge4;
                } else if (leftRightLeave) {
                    return this.setEntrPos(pos2, 2);
                } else {
                    return edge4;
                }
            } else if (passedRight) /*right, 2*/ {
                if (this.entered == 2) {
                    return edge2;
                } else if (leftRightLeave) {
                    return this.setEntrPos(pos4, 4);
                } else {
                    return edge2;
                }
            } else {
                return this;
            }
        } else {
            if (passedUp) /*up, 1*/{
                return edge1;
            } else if (passedBottom) /*down, 3*/ {
                return edge3;
            } else if (passedLeft) /*left, 4*/{ 
                return edge4;
            } else if (passedRight) /*right, 2*/ {
                return edge2;
            } else {
                return this;
            }
        }
    }
    
    public Player setEntrPos(Posn position, int entrance){
        return new Player(this.screenWidth, this.screenHeight, position,
                            entrance, this.direction, false, this.level, this.red, this.blue, 
                            this.yellow, this.inBattle, 5);
    }
    
    
    public Player setShields(int red, int blue, int yellow) {
        return new Player(this.screenWidth, this.screenHeight, this.position,
                            this.entered , this.direction, this.spacebar, 
                            this.level, red, blue, yellow, false, this.eventNum);
    }
    
    
    public Player onTick(){
        return this;
    }
    
    public WorldImage playerImage() {
        return new RectangleImage(this.position, 
                this.playerWidth , this.playerHeight, new Yellow());
    }
    
}


