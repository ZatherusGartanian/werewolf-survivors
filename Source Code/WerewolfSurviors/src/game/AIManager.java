package game;
import actors.*;
import java.util.List;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * AIManager
 * Description:
 * Contains methods used for solving movement of AI. Currently contains seek and dodge methods. Also includes collision
 * detection method for looping through all enemies checking for hitbox interactions between them.
 *
 * Future Updates/Refactor:
 * In future updates I may use the AIManager as an "enemy" manager. This would isolate all the enemy logic into one location.
 * Spawn timers, enemy lists, enemy sprite so that only one sprite is loaded into memory, etc. Essentially all the AI code
 * could be removed from the core game class and put here. Currently this class is just a series of static method calls
 * to solve for enemy logic. I also want to change the seek and dodge methods to take in vectors instead of the objects.
 * If seek took in a target location vector instead of the player it could be used to seek towards any location.
 * Same with the mouse location for dodging. Dodging is slightly more specific though as it is not a dodge target location,
 * its dodge out of vision cone of the player so it does still have more specific meaning.
 *
 */

public class AIManager {

    public static void seek(Enemy enemy, Player target, double deltaTime){

        //Calculate the vector towards the target
        Vector2D direction = Vector2D.getVectorBetweenPoints(enemy.getXLocation(), enemy.getYLocation(), target.getXLocation(), target.getYLocation());

        //Normalize the vector and multiply by speed of unit
        direction.normalize();
        direction.scalarMultiply(enemy.getMaxSpeed());

        //Move the enemy using created vector
        enemy.move(direction.x, direction.y, deltaTime);
    }

    public static void dodge(Enemy enemy, Player player, float mouseX, float mouseY, double deltaTime) {
        //Calculate the angle between the mouse and the player
        double anglePlayerMouse = Math.atan2(mouseY - player.getYLocation(), mouseX - player.getXLocation());

        //Calculate the angle between the enemy and the player
        double angleEnemyPlayer = Math.atan2(enemy.getYLocation() - player.getYLocation(), enemy.getXLocation() - player.getXLocation());

        //Find the difference between the two angles
        double angleDifference = anglePlayerMouse - angleEnemyPlayer;

        //Check for wrapping (359 Degrees vs 1 degree are actually 2 degrees apart, not 358) aka, normalize the angleDifference
        if(angleDifference > Math.PI){
            angleDifference -= 2*Math.PI;
        }
        else if(angleDifference < -Math.PI){
            angleDifference += 2*Math.PI;
        }

        //Check if the difference between angles falls within the range (Currently hardcoded to 20 degree cone)
        //If it does, then the enemy needs to dodge out of the cone.
        if (Math.abs(angleDifference) < Math.toRadians(20)) {

            //If angle difference is positive or negative the enemy should go clockwise or counterclockwise
            //Find the perpendicular angle from the angle to the player to get most direct route away from center of cone.
            double moveAngle;
            if(angleDifference > 0){
                moveAngle = angleEnemyPlayer - Math.PI / 2; //90 degrees clockwise
            }
            else {
                moveAngle = angleEnemyPlayer + Math.PI / 2; //90 degrees counterclockwise
            }

            //Calcualte the avoidance direction using the perpendicular angle to the center of the aiming cone.
            Vector2D avoidanceDirection = new Vector2D((float)Math.cos(moveAngle), (float)Math.sin(moveAngle));
            avoidanceDirection.normalize();

            //Calculate the seek vector towards the target and normalize it
            Vector2D seekDirection = Vector2D.getVectorBetweenPoints(enemy.getXLocation(), enemy.getYLocation(), player.getXLocation(), player.getYLocation());
            seekDirection.normalize();

            //Blend the normalized avoidance and seek directional vectors
            Vector2D blendedDirection = Vector2D.blendVectors(avoidanceDirection, seekDirection, 0.6f);
            blendedDirection.normalize(); //Technically unnecessary but some precision is lost during blending floats so normalizing after helps

            //Multiply the vector by the enemies speed
            blendedDirection.scalarMultiply(enemy.getMaxSpeed());

            //Move the enemy
            enemy.move(blendedDirection.x, blendedDirection.y, deltaTime);
        }
        else{
            //Do a regular seek as dodging isn't necessary
            seek(enemy, player, deltaTime);
        }
    }

    //Compares each enemy in the List against one another and checks for collisions and resolves the collision
    public static void aiCollisionChecks(List<Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = i + 1; j < enemies.size(); j++) {
                Enemy enemy1 = enemies.get(i);
                Enemy enemy2 = enemies.get(j);
                if (enemy1.intersects(enemy2)) {
                    enemy1.resolveEnemyCollision(enemy2);
                }
            }
        }
    }
}
