package edu.lancs.game.gui.buttons;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import edu.lancs.game.scenes.*;

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

            case CREDITS:
                setDefaultTexture(window.getResourceManager().getTextures("credits_default"));
                setSelectTexture(window.getResourceManager().getTextures("credits_hover"));
                break;

            case PLAY_GAME:
                setDefaultTexture(window.getResourceManager().getTextures("play_default"));
                setSelectTexture(window.getResourceManager().getTextures("play_hover"));
                setDisabledTexture(window.getResourceManager().getTextures("play_disabled"));
                setDisabled(true);
                break;

            case EASY:
                setDefaultTexture(window.getResourceManager().getTextures("easy_default"));
                setSelectTexture(window.getResourceManager().getTextures("easy_hover"));
                setDisabledTexture(window.getResourceManager().getTextures("easy_disabled"));
                setDisabled(true);
                break;

            case MEDIUM:
                setDefaultTexture(window.getResourceManager().getTextures("medium_default"));
                setSelectTexture(window.getResourceManager().getTextures("medium_hover"));
                setDisabledTexture(window.getResourceManager().getTextures("medium_disabled"));
                setDisabled(true);
                break;

            case HARD:
                setDefaultTexture(window.getResourceManager().getTextures("hard_default"));
                setSelectTexture(window.getResourceManager().getTextures("hard_hover"));
                setDisabledTexture(window.getResourceManager().getTextures("hard_disabled"));
                setDisabled(true);
                break;

            case IMPOSSIBLE:
                setDefaultTexture(window.getResourceManager().getTextures("impossible_default"));
                setSelectTexture(window.getResourceManager().getTextures("impossible_hover"));
                setDisabledTexture(window.getResourceManager().getTextures("impossible_disabled"));
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

                case CREDITS:
                    // TODO: Add loading here
                    Debug.print("[Button] Credits");
                    CreditScene creditScene = new CreditScene(getWindow(), getParentScene());
                    int creditSceneIndex = getWindow().addScene(creditScene);
                    creditScene.activate();
                    getWindow().setCurrentScene(creditSceneIndex);
                    getParentScene().deactivate();
                    break;

                case EASY:
                case MEDIUM:
                case HARD:
                case IMPOSSIBLE:
                case PLAY_GAME:
                    Debug.print("[Button] Play Game");
                    // creates the GameScene for the users to play
                    GameScene gameScene;

                    switch (type) {
                        case EASY:
                            gameScene = new GameScene(getWindow(), getText(), GameScene.GameMode.EASY);
                            break;
                        case MEDIUM:
                            gameScene = new GameScene(getWindow(), getText(), GameScene.GameMode.MEDIUM);
                            break;
                        case HARD:
                            gameScene = new GameScene(getWindow(), getText(), GameScene.GameMode.HARD);
                            break;
                        case IMPOSSIBLE:
                            gameScene = new GameScene(getWindow(), getText(), GameScene.GameMode.IMPOSSIBLE);
                            break;
                        default:
                            gameScene = new GameScene(getWindow(), getText(), GameScene.GameMode.IMPOSSIBLE);
                            break;
                    }

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
        NEW_GAME, CREDITS, PLAY_GAME, EASY, MEDIUM, HARD, IMPOSSIBLE, HIGH_SCORES, TUTORIAL, EXIT
    }
}
