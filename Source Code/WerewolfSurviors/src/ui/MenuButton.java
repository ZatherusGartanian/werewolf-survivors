package ui;
import java.awt.*;
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
 * MenuButton
 * Description:
 * Custom button since the JButton class could not easily be used with the way the ViewPort is set up. Adding further
 * elements to the panel and frame would be difficult.
 *
 * Future Updates/Refactor:
 * See the input controller comments as the input concerns are reflected with buttons since they directly care about mouse
 * input. Clicked visual is also fairly useless with the current build in retrospect.
 */

public class MenuButton {
    //Visuals
    private BufferedImage normal;
    private BufferedImage hover;
    private BufferedImage pressed;
    private BufferedImage currentImage;

    //Location
    private int xLocation;
    private int yLocation;

    //Hitbox
    private Rectangle hitBox;

    //Sets if pressed. Irrelevent in the current build as a clicked button is never visually seen.
    boolean clicked = false;

    public MenuButton(BufferedImage normal, BufferedImage hover, BufferedImage pressed, int xLocation, int yLocation) {
        this.normal = normal;
        this.hover = hover;
        this.pressed = pressed;

        this.xLocation = xLocation;
        this.yLocation = yLocation;

        this.currentImage = normal;
        hitBox = new Rectangle(xLocation, yLocation, normal.getWidth(), normal.getHeight());
    }

    public void draw(Graphics g) {
        g.drawImage(currentImage, xLocation, yLocation, null);
    }

    //If clicked set the image visual to the pressed visual and sets clicked. Never seen with current game.
    public void click(){
        clicked = true;
        currentImage = pressed;
    }

    //Switches to the hover image. If the button was clicked it should stay clicked instead of hovered. Not important anymore.
    public void hover(){
        if(!clicked)
            currentImage = hover;
    }

    //Resets the buttons visual state.
    public void resetButton(){
        clicked = false;
        currentImage = normal;
    }

    //Checks if the mouse is intersceting the hitbox of the button.
    public boolean mouseIntersect(MouseEvent e){
        return hitBox.contains(e.getPoint());
    }
}
