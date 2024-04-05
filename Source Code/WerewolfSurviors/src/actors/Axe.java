package actors;
import game.Vector2D;

import java.awt.image.BufferedImage;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * Axe (Child of GameObject)
 * Description:
 * Represents the axe weapon within the game.
 *
 * Future Updates/Refactor:
 * Should rotate as it moves to add visual flare. Currently only using one axe so it will disappear when it resets for
 * the next spawn. This is intentional but could add an impact visual for collisions or end point to add game feel.
 * Is there a way to avoid having a custom update? If the axe had a reference to a mouse Vector2D locations and a reference
 * to the player the custom update could be dropped. Since mouse was not a Vector2D yet this was not an option.
 */

public class Axe extends GameObject{
    private long timerLastAxeThrow;//Timer for the last release of the axe
    private final int AXE_TIMER = 3000; //3 Second timer between throws

    public Axe(BufferedImage sprite){
        super(sprite);

        //Set set time
        timerLastAxeThrow = System.currentTimeMillis();

        //Set an off screen location to start. Will move to the players location when first thrown.
        location.x = -100;
        location.y = -100;

        maxSpeed = 250;
    }

    //Custom update as mouse and player information is needed
    public void update(Player player, float mouseX, float mouseY, double timeSinceUpdate){
        long currentTime = System.currentTimeMillis();

        //If the axe timer has passed update the axe
        if(currentTime - timerLastAxeThrow > AXE_TIMER) {
            timerLastAxeThrow = currentTime; //reset the spawn timer

            //Set the position to the position of the player. Could update to use Vector2D.
            setPosition(player.getXLocation(), player.getYLocation());

            //Get the vector towards the mouse
            Vector2D direction = Vector2D.getVectorBetweenPoints(location.x, location.y, mouseX, mouseY);
            direction.normalize();
            direction.scalarMultiply(maxSpeed);

            //In the future this forced return to old location design would be removed.
            xVelocity = direction.x;
            yVelocity = direction.y;
        }

        move(xVelocity, yVelocity, timeSinceUpdate);
    }

    //CUSTOM UPDATE REQUIRED FOR AXE. Not used.
    @Override
    public void update(double timeSinceUpdate) {

    }
}
