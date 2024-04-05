package actors;
import game.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * GameObject Astract Class
 * Description:
 * Defines the structure for objects that will exist within the game.
 *
 * Future Updates/Refactor:
 * Should include animation state information and use sprite sheets instead of static images. Velocity and movement
 * also need to be refactored to use Vector2D and solve for max speed here instead of outside the class. This will be
 * important if ever acceleration is used instead of one speed. Would also be nice to allow for custom hitboxs instead of
 * the sprite size
 */
public abstract class GameObject {
    protected Vector2D location; //Location to draw the sprite from
    protected BufferedImage sprite; //Visual of object
    protected Rectangle hitBox; //Hitbox for the object

    //Velocity x and y (Should update to use Vector2D)
    protected float xVelocity = 0;
    protected float yVelocity = 0;
    protected int maxSpeed; //Maximum Speed of the object. (Pixels per second)

    protected GameObject(BufferedImage sprite){
        this.sprite = sprite;
        location = new Vector2D(300, 300);

        hitBox = new Rectangle((int)location.x, (int)location.y, sprite.getWidth(), sprite.getHeight());
    }

    //All objects should have a way of updating themselves
    public abstract void update(double timeSinceUpdate);

    //Move the object using the provided velocity and delta time.
    public void move(float xVelocity, float yVelocity, double timeSinceUpdate){
        location.x += (float)(xVelocity * timeSinceUpdate);
        location.y += (float)(yVelocity * timeSinceUpdate);

        hitBox.setLocation((int)location.x, (int)location.y);
    }

    //Draws the visual for the object. Includes visual for hitbox drawing with removal of comments.
    public void draw(Graphics g){
        //HITBOX TESTING
        //g.setColor(Color.PINK);
        //g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

        g.drawImage(sprite, (int)location.x, (int)location.y, null);
    }

    //Sets a new position for the object
    protected void setPosition(float newX, float newY) {
        location.x = newX;
        location.y = newY;
        hitBox.setLocation((int)newX, (int)newY);
    }

    //GETTERS
    public Rectangle getHitBox(){return hitBox;}
    public float getXLocation(){return location.x;}
    public float getYLocation(){return location.y;}
    public int getMaxSpeed() {return maxSpeed;}

    //Future use to return the center of the object. Would be used for seeking and axe spawning instead of the top left
    //draw coordinates.
    public Vector2D getCenter(){
        return new Vector2D(location.x + (float)(sprite.getWidth()/2), location.y + (float)(sprite.getHeight()/2));
    }
}
