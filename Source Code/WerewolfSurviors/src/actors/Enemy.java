package actors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * Enemy (Child of GameObject)
 * Description:
 * Defines additional information for enemies. Applies a random spawn at creation and includes collision resolution logic.
 *
 * Future Updates/Refactor:
 * Current collision resolution doesnt use Vector2D. The methods in here could also be tweaked to be static methods within
 * the AIManager instead of within enemies. AIManager.getRandomSpawnLocationOutofBounds() and return a Vector2D. Resolve
 * collisions could just take in two enemies and then set both positions at the end.
 */
public class Enemy extends GameObject{

    public Enemy(BufferedImage sprite){
        super(sprite);
        randomSpawnLocation();
    }

    //Shift enemies apart when colliding.Takes in the enemy being collided with and moves them apart depending on the location
    //of the collision.
    public void resolveEnemyCollision(Enemy other) {
        //Get the intersecting hitbox to determine the type of overlap and distances.
        Rectangle intersection = hitBox.intersection(other.hitBox);
        //Amount to shift to resolve collision
        int xShift = 0;
        int yShift = 0;

        //if the intersection hitbox is less wide then tall then it is a horizontal collision.
        if (intersection.width < intersection.height) {
            //Horizontal collision
            xShift = intersection.width / 2; //Both enemies will shift half of the intersection width

            //Check which enemy is farther left
            if (location.x < other.location.x) {
                //Negate direction to move left
                xShift = -xShift;
            } else {
                //Moving right so keep positive result
            }
        } else {
            // Vertical collision or perfect square
            yShift = intersection.height / 2; //Both enemies will shift half of the intersection height

            //Check which enemy is higher up
            if (location.y < other.location.y) {
                //Negate direction to move upwards
                yShift = -yShift;
            } else {
                //Moving down so keep positive result
            }
        }
        //Move both enemies away from each other
        setPosition(location.x + xShift, location.y + yShift);
        other.setPosition(other.location.x - xShift, other.location.y - yShift);
    }

    //Gets a random spawn location outside the bounds of the screen. USES HARDCODED SCREEN DIMENSIONS
    protected void randomSpawnLocation(){
        Random random = new Random();

        //Check if the enemy should spawn on either the sides or the top and bottom.
        boolean spawnOffScreenHorizontally = random.nextBoolean();
        if(spawnOffScreenHorizontally){
            //If spawning horizontally check if left or right side and then set X accordingly.
            boolean spawnLeftSide = random.nextBoolean();
            if(spawnLeftSide){
                location.x = 0 - sprite.getWidth();
            }
            else {
                location.x = 800;
            }

            //Randomize y within the bounds
            location.y = random.nextInt(601);
        }
        //Otherwise spawn the enemy on the top or bottom
        else {
            //If spawning vertically check if top or bottom and then set Y accordingly.
            boolean spawnTopSide = random.nextBoolean();
            if(spawnTopSide){
                location.y = 0 - sprite.getHeight();
            }
            else {
                location.y = 600;
            }

            //Randomize x within the bounds
            location.x = random.nextInt(801);
        }
    }

    //Check if intersecting another enemy
    public boolean intersects(Enemy other) {
        return hitBox.intersects(other.hitBox);
    }

    @Override
    public void update(double timeSinceUpdate) {
        //Not used
    }
}
