package scenes;

import actors.*;
import game.*;
import ui.GameUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * GameCore (Child of Scene)
 * Description:
 * Main game play scene that has all the logic for the game loop.
 *
 * Future Updates/Refactor:
 * Vectors and Input Controls need to have a final refactor. Enemies could maybe be handled in the AIManager to isolate
 * the AI logic and handling to one location. Collision logic could also be reworked a touch so that each game object
 * can handle their own collisions and are simply told they collided with something and what that something is.
 */

public class GameCore extends Scene{
    //Game Objects
    private List<Enemy> enemies;
    private Player player;
    private Axe axe;

    //Sprite References
    private BufferedImage background;
    private BufferedImage dogSprite;
    private BufferedImage skeletonSprite;

    //Class References
    private GameManager gameManager;

    //Game Control Variables
    private int spawnTimer = 4000; //4 Seconds
    private long lastSpawnTime = 0;
    private int dogsToSpawn = 5;

    public final int MAX_HITS = 3; //Max number of hits the player can take (Lives)
    private int hitsTaken = 0; //Tracker for the current number of hits the player has taken.
    private int collisionCooldown = 2000; //2 Seconds
    private long collisionTime = 0; //Last collision time.
    private boolean collided = false; //Player collision flag

    private int score = 0; //Score for the game

    private boolean gameOver = false; //Game over flag to avoid updates (COULD POSSIBLY BE REMOVED)

    //UI Elements
    private GameUI gameUI;

    //Mouse Location
    private float mouseX;
    private float mouseY;

    public GameCore(GameManager gameManager){
        this.gameManager = gameManager;

        initializeGame();
    }

    //Initialization for the game elements.
    private void initializeGame(){
        //Prep the player class
        BufferedImage sprite = ResourceLoader.loadImage(ResourceLoader.PLAYER_SPRITE);
        player = new Player(sprite);

        //Prep the initial enemies
        enemies = new ArrayList<>();
        dogSprite = ResourceLoader.loadImage(ResourceLoader.DOG);
        for(int i = 0; i < 8; i++) {
            enemies.add(new Dog(dogSprite));
        }
        skeletonSprite = ResourceLoader.loadImage(ResourceLoader.SKELETON);
        for(int i = 0; i < 2; i++){
            enemies.add(new Skeleton(skeletonSprite));
        }

        //Prep the axe
        sprite = ResourceLoader.loadImage(ResourceLoader.AXE);
        axe = new Axe(sprite);

        //Prep the UI
        background = ResourceLoader.loadImage(ResourceLoader.BACKGROUND);
        gameUI = new GameUI(this);
    }

    //Draw each element of the game. Order matters. Lowest layer elements first (backgrounds), to highest layer (UI).
    @Override
    public void draw(Graphics g) {

        g.drawImage(background, 0, 0, null);

        player.draw(g);

        for(Enemy enemy: enemies){
            enemy.draw(g);
        }

        axe.draw(g);

        gameUI.draw(g);
    }

    @Override
    public void update(double deltaTime) {
        //GameOver check to make sure the game doesnt keep trying to update. Not super necessary as the scene switched
        //But in place as a protection in case the gameCore update gets called.
        if(gameOver){
            return;
        }
        //Check timer to see if a new wave should spawn. If so, spawns the enemies and resets the timer.
        if(System.currentTimeMillis() - lastSpawnTime >= spawnTimer){
            for(int i = 0; i < dogsToSpawn; i++){
                enemies.add(new Dog(dogSprite));
            }

            enemies.add(new Skeleton(skeletonSprite));

            lastSpawnTime = System.currentTimeMillis();
        }

        //Update the player and weapon
        player.update(deltaTime);
        axe.update(player, mouseX, mouseY, deltaTime);

        //Check for weapon and player collisions with enemies to save on CPU time. If an enemy dies here it saves having to
        //check all its movement and collision and then removing it anyways.
        collisionDetection();

        //Handle Enemy movement
        for(Enemy enemy: enemies) {
            if(enemy instanceof Dog){
                AIManager.seek(enemy, player, deltaTime);
            }
            else if(enemy instanceof Skeleton){
                AIManager.dodge(enemy, player, mouseX, mouseY, deltaTime);
            }
        }

        //Check for enemy collisions
        AIManager.aiCollisionChecks(enemies);
    }

    //Basic collision detection of two hitboxes. Cycles through every enemy and checks against the player and axe.
    private void collisionDetection(){

        //Cycle through the enemy array and check for collisions
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            //If intersecting with the player
            if(player.getHitBox().intersects(enemy.getHitBox())){
                //Reset collided cooldown if enough time has passed since last hit.
                if(System.currentTimeMillis() > collisionTime + collisionCooldown){
                    collided = false;
                }
                //If not on cooldown, do the collision
                if(!collided){
                    collided = true;
                    collisionTime = System.currentTimeMillis();
                    hitsTaken++;
                    //If total hits are taken end the game.
                    if(hitsTaken == MAX_HITS){
                        gameOver = true;
                        gameManager.changeState(GameState.GAMEOVER);
                        return;
                    }
                }
            }

            //Check if the axe hit the enemy.
            if (enemy.getHitBox().intersects(axe.getHitBox())) {
                //Check which type of enemy and increase score accordingly.
                if(enemy instanceof Dog){
                    score += 100;
                }
                else if(enemy instanceof Skeleton){
                    score += 500;
                }

                //Remove the enemy from the list for simple kill design.
                iterator.remove();
            }
        }
    }

    //Store the mouse position.
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    //Handle key presses for player movement
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_W){
            player.moveUp();
        }
        if(keyCode == KeyEvent.VK_S){
            player.moveDown();
        }
        if(keyCode == KeyEvent.VK_D){
            player.moveRight();
        }
        if(keyCode == KeyEvent.VK_A){
            player.moveLeft();
        }
    }

    //Handle key releases for player movement
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_W){
            player.stopUp();
        }
        if(keyCode == KeyEvent.VK_S){
            player.stopDown();
        }
        if(keyCode == KeyEvent.VK_D){
            player.stopRight();
        }
        if(keyCode == KeyEvent.VK_A){
            player.stopLeft();
        }
    }

    //GETTERS
    public int getHitsTaken(){return hitsTaken;}
    public int getScore(){return score;}

    //NOT USED
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
