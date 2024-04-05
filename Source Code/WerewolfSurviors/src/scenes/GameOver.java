package scenes;
import game.GameState;
import game.ResourceLoader;
import game.GameManager;
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
 * GameOver (Child of Scene)
 * Description:
 * Displays the game over screen
 *
 * Future Updates/Refactor:
 * Not sure if I like this being its own scene but until I find a better solution for input handling this was the cleanest
 * solution. Uses hard coded dimensions for screen size.
 */
public class GameOver extends Scene{
    //Reference Variables
    private GameCore game;
    private GameManager gameManager;

    //Back splash of the text displayed during game over
    private BufferedImage uiBackground;
    private int uiBackgroundX;
    private int uiBackgroundY;

    //Button used to return to the main menu
    private MenuButton quitButton;

    //TEXT DISPLAYED ON GAME OVER SCREEN
    private final String GAME_OVER_STRING = "GAME OVER";
    private final String FINAL_SCORE_STRING = "FINAL SCORE";

    public GameOver(GameCore game, GameManager gameManager){
        this.game = game;
        this.gameManager = gameManager;

        //Load the image and set its location. HARD CODED DIMENSION USED
        uiBackground = ResourceLoader.loadImage(ResourceLoader.MENU_BACKGROUND);
        uiBackgroundX = (800/2) - (uiBackground.getWidth()/2);
        uiBackgroundY = (600/2) - (uiBackground.getHeight()/2);

        //Load the images for the button and creates the button
        BufferedImage buttonNormal = ResourceLoader.loadImage(ResourceLoader.MENU_BUTTON_NORMAL);
        BufferedImage buttonHover = ResourceLoader.loadImage(ResourceLoader.MENU_BUTTON_HOVER);
        BufferedImage buttonActive = ResourceLoader.loadImage(ResourceLoader.MENU_BUTTON_ACTIVE);
        quitButton = new MenuButton(buttonNormal, buttonHover, buttonActive, (800/2) - (buttonNormal.getWidth()/2), (600/2) + 100);
    }

    @Override
    public void draw(Graphics g) {
        game.draw(g); //Draws the game screen first to overlay the game over screen over top.

        //Set the font for the strings
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        //Get the font metrics to calculate text width and height for centering
        FontMetrics fm = g.getFontMetrics();

        //Center each string of text by using the font metrics.
        int gameOverX = (800 - fm.stringWidth(GAME_OVER_STRING)) / 2;
        g.drawImage(uiBackground, uiBackgroundX, uiBackgroundY, null);
        g.drawString(GAME_OVER_STRING, gameOverX, (600/2) - 30);

        int finalScoreX = (800 - fm.stringWidth(FINAL_SCORE_STRING)) / 2;
        g.drawString(FINAL_SCORE_STRING, finalScoreX, (600/2)+20);

        String highScoreString = "" + game.getScore();
        int scoreStringX = (800 - fm.stringWidth(highScoreString)) / 2;
        g.drawString(highScoreString, scoreStringX, (600/2) + 60);

        quitButton.draw(g);
    }

    @Override
    public void update(double deltaTime) {

    }

    //Chcek if the mouse is clicked over top of the button. If so, switch back to menu.
    @Override
    public void mouseClicked(MouseEvent e) {
        if (quitButton.mouseIntersect(e)) {
            quitButton.click();
            gameManager.changeState(GameState.MENU);
            quitButton.resetButton();
        }
    }

    //Create the hover effect of hovering over a button.
    @Override
    public void mouseMoved(MouseEvent e) {
        if (quitButton.mouseIntersect(e)) {
            quitButton.hover();
        }
        else
            quitButton.resetButton();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
