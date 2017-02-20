package edu.lancs.game.gui.buttons;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import edu.lancs.game.scenes.GameScene;
import edu.lancs.game.scenes.Scene;
import edu.lancs.game.scenes.TestScene;
import org.jsfml.audio.Sound;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static edu.lancs.game.Constants.*;

public class MenuButton extends Button {

    public enum Type {
        NEW_GAME, HIGH_SCORES, TUTORIAL, EXIT
    }

    private Window window;

    private Type type;


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

            case TUTORIAL:
                setDefaultTexture(window.getResourceManager().getTextures("tutorial_default"));
                setSelectTexture(window.getResourceManager().getTextures("tutorial_hover"));
                break;

            case EXIT:
                setDefaultTexture(window.getResourceManager().getTextures("quit_default"));
                setSelectTexture(window.getResourceManager().getTextures("quit_hover"));
                break;
        }
    }

    @Override
    public void click() {
        switch (type) {
            case NEW_GAME:
                Debug.print("[Button] New Game");
                GameScene gameScene = new GameScene(getWindow());
                int gameSceneIndex = getWindow().addScene(gameScene);
                gameScene.activate();
                if(getParentScene().getMusic() != null)
                    getParentScene().getMusic().stop();
                getWindow().setCurrentScene(gameSceneIndex);
                getParentScene().deactivate();
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

            case TUTORIAL:
                Debug.print("[Button] Level Editor ");
                TestScene testScene = new TestScene(getWindow(), getParentScene());
                int testSceneIndex = getWindow().addScene(testScene);
                testScene.activate();
                getWindow().setCurrentScene(testSceneIndex);
                getParentScene().deactivate();
                break;

            case EXIT:
                Debug.print("[Button] Exit");
                System.exit(0);
                break;
        }
    }

    @Override
    public void mouseOver() {
        getWindow().getResourceManager().getSound("menu_click").play();
    }
}
