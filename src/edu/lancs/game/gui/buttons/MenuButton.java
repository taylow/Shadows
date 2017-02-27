package edu.lancs.game.gui.buttons;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import edu.lancs.game.scenes.GameScene;
import edu.lancs.game.scenes.Scene;
import edu.lancs.game.scenes.TutorialScene;
import edu.lancs.game.scenes.UserNameScene;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static edu.lancs.game.Constants.MENU_BUTTON_HEIGHT;
import static edu.lancs.game.Constants.MENU_BUTTON_WIDTH;

public class MenuButton extends Button {

    private Type type;

    public MenuButton(Window window, Scene parentScene, String text, Type type, float xPos, float yPos) {
        super(window, parentScene, xPos, yPos, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        setText(text);
        this.type = type;

        // chooses what textures to use based on what type of button it is (multi-function button)
        switch (this.type) {
            case NEW_GAME:
                setDefaultTexture(window.getResourceManager().getTextures("new_game_default"));
                setSelectTexture(window.getResourceManager().getTextures("new_game_hover"));
                break;

            case RESUME_GAME:
                setDefaultTexture(window.getResourceManager().getTextures("resume_default"));
                setSelectTexture(window.getResourceManager().getTextures("resume_hover"));
                setDisabledTexture(window.getResourceManager().getTextures("resume_disabled"));
                setDisabled(true);
                break;

            case PLAY_GAME:
                setDefaultTexture(window.getResourceManager().getTextures("play_default"));
                setSelectTexture(window.getResourceManager().getTextures("play_hover"));
                setDisabledTexture(window.getResourceManager().getTextures("play_disabled"));
                setDisabled(true);
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

    /***
     * As this is a multi-function button, this performs an action based off what type of button it is.
     */
    @Override
    public void click() {
        if(!isDisabled()) {
            switch (type) {
                case NEW_GAME:
                    Debug.print("[Button] New Game");

                    // creates the GameScene for the users to play
                    UserNameScene userNameScene = new UserNameScene(getWindow(), getWindow().getScene(0));
                    int userNameSceneIndex = getWindow().addScene(userNameScene);
                    userNameScene.activate();

                    // deactivate current scene (should be menu at this point)
                    getWindow().setCurrentScene(userNameSceneIndex);
                    getParentScene().deactivate();
                    break;

                case RESUME_GAME:
                    // TODO: Add loading here
                    System.out.println("RESUME GAME");
                    break;

                case PLAY_GAME:
                    // creates the GameScene for the users to play
                    GameScene gameScene = new GameScene(getWindow(), getText());
                    int gameSceneIndex = getWindow().addScene(gameScene);
                    gameScene.activate();

                    // if there is music, stop said music
                    if (getParentScene().getMusic() != null)
                        getParentScene().getMusic().stop();

                    // deactivate current scene (should be menu at this point)
                    getWindow().setCurrentScene(gameSceneIndex);
                    getParentScene().deactivate();
                    break;

                case HIGH_SCORES:
                    Debug.print("[Button] High Scores ");

                    // opens the users browser to the high scores page (live web-based high scores)
                    try {
                        java.awt.Desktop.getDesktop().browse(new URI("http://protaytoe.uk/highscores")); // open this URL
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                    break;

                case TUTORIAL:
                    Debug.print("[Button] Level Editor ");

                    // adds the tutorial scene an sets it to the active scene
                    //FIXME: This technically adds the as a new scene every time. Could be fixed by having the BackButton delete the TutorialScene from Window
                    TutorialScene tutorialScene = new TutorialScene(getWindow(), getParentScene());
                    int testSceneIndex = getWindow().addScene(tutorialScene);
                    tutorialScene.activate();
                    getWindow().setCurrentScene(testSceneIndex);
                    getParentScene().deactivate();
                    break;

                case EXIT:
                    Debug.print("[Button] Exit");
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void mouseOver() {
        getWindow().getResourceManager().getSound("menu_click").play(); // plays the menu click sound upon mouseover
    }

    public enum Type {
        NEW_GAME, RESUME_GAME, PLAY_GAME, HIGH_SCORES, TUTORIAL, EXIT
    }
}
