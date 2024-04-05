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
 * Dog (Child of Enemy)
 * Description:
 * Resprentation of the Skeleton enemies.
 *
 * Future Updates/Refactor:
 * Could arguably just be of type enemy but I had planned to have different speeds for different enemy types and behaviours.
 * Would also like to use the update method here to call for the AIManger to run dodge(). Main issue with this right now is
 * tracking a reference to the player and mouse location and I definitely do not want to use a custom update method.
 */
public class Skeleton extends Enemy{

    public Skeleton(BufferedImage sprite){
        super(sprite);
        maxSpeed = 60;

    }

    @Override
    public void update(double timeSinceUpdate) {

    }
}
