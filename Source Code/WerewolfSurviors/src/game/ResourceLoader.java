package game;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/*
 * WEREWOLF SURVIVORS GAME
 *
 * AUTHOR:   Keith Mitchell
 * SID:      3178513
 * DATE:     March 27
 * COURSE:   COMP452 - AI for Game Developers (Athabasca University)
 *
 * Resource Loader
 * Description:
 * Used for easier loading of assets into the game. Currently only contains methods for loading images.
 *
 * Future Updates/Refactor:
 * In the future this class could include other methods for loading different asset types. Sound will need to be loaded.
 *
 */

public class ResourceLoader {
    public static final String PLAYER_SPRITE = "rogue.png";
    public static final String BACKGROUND = "background.jpg";
    public static final String DOG = "dog.png";
    public static final String AXE = "axe.png";
    public static final String MENU_BUTTON_NORMAL = "playbutton_normal.png";
    public static final String MENU_BUTTON_ACTIVE = "playbutton_active.png";
    public static final String MENU_BUTTON_HOVER = "playbutton_hover.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String SKELETON = "skeleton.png";
    public static final String FULL_HEART = "HeartFull.png";
    public static final String EMPTY_HEART = "HeartEmpty.png";
    public static final String GAME_TITLE = "title.png";

    //Takes in string name (see above constant list), and returns the image.
    //The file path name here potentially could cause issues depending on how the compiler uses path names. If images are not
    //being found you may need to customize a path to the assets folder and replace that portion of the string. If following
    //the compile instructions of using the IDE ItelliJ, then this should not be a concern at all.
    public static BufferedImage loadImage(String assetName){
        BufferedImage image = null;
        try {
            // Load the player bitmap image
            image = ImageIO.read(new File("assets/" + assetName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
