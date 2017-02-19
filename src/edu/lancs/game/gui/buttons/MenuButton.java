package edu.lancs.game.gui.buttons;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import edu.lancs.game.scenes.Scene;
import edu.lancs.game.scenes.TestScene;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static edu.lancs.game.Constants.*;

public class MenuButton extends Button {

    public enum Type {
        NEW_GAME, HIGH_SCORES, LEVEL_EDITOR, EXIT
    }

    private Window window;

    private Type type;

    private TestScene testScene;
    private int testSceneIndex;


    public MenuButton(Window window, Scene parentScene, String text, Type type, float xPos, float yPos) {
        super(window, parentScene, xPos, yPos, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        setText(text);
        this.type = type;
        switch (this.type) {
            case NEW_GAME:
                setDefaultTexture(window.getResourceManager().getTextures("new_game_default"));
                setSelectTexture(window.getResourceManager().getTextures("new_game_hover"));
                break;

            case HIGH_SCORES:
                setDefaultTexture(window.getResourceManager().getTextures("high_scores_default"));
                setSelectTexture(window.getResourceManager().getTextures("high_scores_hover"));
                break;

            case LEVEL_EDITOR:
                setDefaultTexture(window.getResourceManager().getTextures("level_editor_default"));
                setSelectTexture(window.getResourceManager().getTextures("level_editor_hover"));
                break;

            case EXIT:
                setDefaultTexture(window.getResourceManager().getTextures("quit_default"));
                setSelectTexture(window.getResourceManager().getTextures("quit_hover"));
                break;
        }

        testScene = new TestScene(getWindow(), getParentScene());
        testSceneIndex = getWindow().addScene(testScene);
    }

    @Override
    public void click() {
        switch (type) {
            case NEW_GAME:
                Debug.print("[Button] New Game");
                break;

            case HIGH_SCORES:
                Debug.print("[Button] High Scores ");
                try {
                    java.awt.Desktop.getDesktop().browse(new URI("http://protaytoe.uk/highscores"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;

            case LEVEL_EDITOR:
                Debug.print("[Button] Level Editor ");
                getWindow().setCurrentScene(testSceneIndex);
                break;

            case EXIT:
                Debug.print("[Button] Exit");
                System.exit(0);
                break;
        }
    }
}
