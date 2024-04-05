package actors;

import java.awt.image.BufferedImage;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * Player (Child of GameObject)
 * Description:
 * Represents the player.
 *
 * Future Updates/Refactor:
 * See input controls. Getting directional vector could be cleaned up as it doesnt use Vector2D.
 * In retrospect the stop and press flags could have been one method and pass true or false. Not sure why my brain built
 * it like this. It does read easier though.
 */

public class Player extends GameObject {

    //Input tracking
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean rightPressed = false;
    private boolean leftPressed = false;

    public Player(BufferedImage sprite){
        super(sprite);
        //Spawn in the screen center (HARDCODED DIMENSIONS USED)
        location.x = 800/2 - sprite.getWidth()/2;
        location.y = 600/2 - sprite.getHeight()/2;
        maxSpeed = 150;
    }

    //Return the x direction of the player.
    private void getXVelocity(){
        if(rightPressed && leftPressed){
            xVelocity = 0;
        }
        else if(rightPressed){
            xVelocity = maxSpeed;
        }
        else if(leftPressed){
            xVelocity = -maxSpeed;
        }
        else
            xVelocity = 0;
    }

    //Return the y direciton of the player.
    private void getYVelocity(){
        if(upPressed && downPressed){
            yVelocity = 0;
        }
        else if(upPressed){
            yVelocity = -maxSpeed;
        }
        else if(downPressed){
            yVelocity = maxSpeed;
        }
        else
            yVelocity = 0;
    }

    //Set flags for current key controls
    public void moveRight(){rightPressed = true;}
    public void moveLeft(){leftPressed = true;}
    public void moveDown(){ downPressed = true; }
    public void moveUp(){ upPressed = true; }

    public void stopRight(){rightPressed = false; }
    public void stopLeft() {leftPressed = false; }
    public void stopUp() {upPressed = false; }
    public void stopDown() {downPressed = false; }


    @Override
    public void update(double timeSinceUpdate) {
        //Get the current velocity of the player based on its movement.
        getXVelocity();
        getYVelocity();

        if (xVelocity != 0 && yVelocity != 0) {
            //If moving diagonally, normalize velocity to maintain consistent speed
            float magnitude = (float)Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
            xVelocity = (xVelocity / magnitude) * maxSpeed;
            yVelocity = (yVelocity / magnitude) * maxSpeed;
        }

        //Move the player.
        move(xVelocity, yVelocity, timeSinceUpdate);

        //If at the edge of the screen, bound the player to the screen size.
        if(location.x + sprite.getWidth() > 800){
            location.x = 800 - sprite.getWidth();
        }
        else if(location.x < 0){
            location.x = 0;
        }
        if(location.y + sprite.getHeight() > 600){
            location.y = 600 - sprite.getHeight();
        }
        else if(location.y < 0){
            location.y = 0;
        }
    }
}
