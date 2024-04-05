package ui;
import game.ResourceLoader;
import game.Vector2D;
import scenes.GameCore;

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
 * GameUI
 * Description:
 * Draws the games UI elements
 *
 * Future Updates/Refactor:
 * Could be nice to create a UI element class that stores a draw location, stored image reference, and any other details.
 * Tough to execute properly though as many elements are unique like the display of hearts or the inclusion of text for
 * the score. Also uses the hard coded dimension values. The heart drawing for loops could have been easier by just counting
 * up once and swapping the draw type.
 */
public class GameUI {
    //Reference Variables
    private GameCore game;

    //Heart UI information
    private BufferedImage emptyHeart;
    private BufferedImage fullHeart;
    private Vector2D heartStartLocation;

    //Score background UI.
    private BufferedImage uiBackground;
    private final int SCOREUI_X = 800/2 - 120;
    private final int SCOREUI_Y = 0;
    private final int SCOREUI_WIDTH = 240;
    private final int SCOREUI_HEIGHT = 50;


    public GameUI(GameCore game){
        this.game = game;
        //Load images
        emptyHeart = ResourceLoader.loadImage(ResourceLoader.EMPTY_HEART);
        fullHeart = ResourceLoader.loadImage(ResourceLoader.FULL_HEART);
        uiBackground = ResourceLoader.loadImage(ResourceLoader.MENU_BACKGROUND);

        //Set the location of the first heart. HARD CODED DIMENSION USED
        heartStartLocation = new Vector2D(800/2 - (float)(emptyHeart.getWidth()*1.5), 60);
    }

    public void draw(Graphics g){
        //Set the font for the score
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        //Get the font metrics to calculate text width and height
        FontMetrics fm = g.getFontMetrics();

        //Draw the score UI
        g.drawImage(uiBackground, SCOREUI_X, SCOREUI_Y, SCOREUI_WIDTH, SCOREUI_HEIGHT, null);
        String scoreString = "SCORE: " + game.getScore();
        //Calculate the position to draw the text using the font metrics
        int textXLocation = (800 - fm.stringWidth(scoreString)) / 2;
        g.drawString(scoreString, textXLocation, SCOREUI_Y + 30);

        //Two loops to draw the hearts to the screen. One loop counts up and draws a full heart for each hit not taken.
        for(int i = 0; i < game.MAX_HITS - game.getHitsTaken(); i++){
            g.drawImage(fullHeart, (int)heartStartLocation.x + (i*fullHeart.getWidth()), (int)heartStartLocation.y, null);
        }
        //Draws empty hearts based on the number of hits taken.
        for(int i = 1; i <= game.getHitsTaken(); i++){
            g.drawImage(emptyHeart, (int)heartStartLocation.x + ((game.MAX_HITS-i)*emptyHeart.getWidth()), (int)heartStartLocation.y, null);
        }
    }
}

