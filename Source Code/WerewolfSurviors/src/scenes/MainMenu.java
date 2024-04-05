package scenes;
import game.GameManager;
import game.GameState;
import game.ResourceLoader;
import ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * MainMenu (Child of Scene)
 * Description:
 * Displays the main menu for the game.
 *
 * Future Updates/Refactor:
 * Adding a save/load state for high score would be a nice addition. This scene would need to access that data. Currently
 * relies on hardcoded screen dimension values.
 */

public class MainMenu extends Scene {
    //Reference Variables
    private GameManager gameManager;

    //UI Elements
    private BufferedImage background;
    private BufferedImage gameTitle;
    private MenuButton playButton;

    //Stores the location to draw the game title
    //SHOULD UPDATE TO USE VECTOR2D
    private int titleX;
    private int titleY;

    public MainMenu(GameManager gameManager){
        this.gameManager = gameManager;

        //Load images
        background = ResourceLoader.loadImage(ResourceLoader.BACKGROUND);
        gameTitle = ResourceLoader.loadImage(ResourceLoader.GAME_TITLE);

        //Center the game title image. USING HARD CODED DIMENSIONS
        titleX = (800/2) - (gameTitle.getWidth()/2);
        titleY = 50;

        //Load images for the button and create the button. USING HARD CODED SCREEN DIMENSIONS.
        BufferedImage buttonNormal = ResourceLoader.loadImage(ResourceLoader.MENU_BUTTON_NORMAL);
        BufferedImage buttonHover = ResourceLoader.loadImage(ResourceLoader.MENU_BUTTON_HOVER);
        BufferedImage buttonActive = ResourceLoader.loadImage(ResourceLoader.MENU_BUTTON_ACTIVE);
        playButton = new MenuButton(buttonNormal, buttonHover, buttonActive, (800/2) - (buttonNormal.getWidth()/2), (600/2) + 100);
    }

    //Draws each element to the screen
    @Override
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(gameTitle, titleX, titleY, null);
        playButton.draw(g);
    }

    //No update necessary.
    @Override
    public void update(double deltaTime) {

    }

    //If the mouse is clicked check if its inside the button. If more buttons existed, would simply check against an array
    //of buttons to see which one it intersects if at all.
    @Override
    public void mouseClicked(MouseEvent e) {
        if (playButton.mouseIntersect(e)) {
            playButton.click(); //In retrospect a pressed visual is useless as the scene changes and the button is reset
            //If the button is clicked launch the game.
            gameManager.changeState(GameState.GAME);
            playButton.resetButton();
        }
    }

    //Check if the mouse is moving in and out of the button to create the hover effect.
    @Override
    public void mouseMoved(MouseEvent e) {
        if (playButton.mouseIntersect(e)) {
            playButton.hover();
        }
        else
            playButton.resetButton();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
