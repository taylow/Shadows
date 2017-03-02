package edu.lancs.game;

import edu.lancs.game.scenes.MenuScene;
import edu.lancs.game.scenes.Scene;
import org.jsfml.audio.Sound;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

import java.util.ArrayList;

import static edu.lancs.game.Constants.RESOURCE_PATH;

public class Window extends RenderWindow {
    private int width;
    private int height;

    private Sound sound;

    private ResourceManager resourceManager;
    private InputHandler inputHandler;

    private ArrayList<Scene> scenes;
    private int currentScene;

    /***
     * Creates a window with set game variables such as resolution, display type, and frame rate
     *
     * @param width - Width of the Window
     * @param height - Height of the Window
     * @param title - Title of the Window
     * @param isFullscreen - Whether or not the Window is in fullscreen mode
     * @param frameRate - The frame rate limit for the Window
     */
    public Window(int width, int height, String title, boolean isFullscreen, int frameRate) {
        this.width = width;
        this.height = height;

        this.resourceManager = new ResourceManager(RESOURCE_PATH); // load all resources
        this.inputHandler = new InputHandler(this); // add an InputHandler with the Window as the focus

        this.scenes = new ArrayList<>();

        this.setIcon(resourceManager.getTextures("game_icon").copyToImage());

        // sets the game to full screen or default
        if (isFullscreen)
            this.create(new VideoMode(width, height), title, WindowStyle.FULLSCREEN);
        else
            this.create(new VideoMode(width, height), title, WindowStyle.DEFAULT);

        this.setFramerateLimit(frameRate); // set the frame rate
        this.setCurrentScene(0); // first scene to be loaded
    }

    /***
     * Draws the currently active scene until deactivated. If multiple Scenes are activated, the
     * one to be activated first will be drawn until others are deactivated.
     */
    public void drawActiveScene() {
        while (true) {
            Debug.print("Drawing scene: " + currentScene + " - " + getCurrentScene().getClass().getName());
            scenes.get(currentScene).display();
        }
    }

    /***
     * Adds a scene to the Windows Scene ArrayList and returns the index value.
     *
     * @param scene - The Scene to be added
     * @return The Scene index value
     */
    public int addScene(Scene scene) {
        scenes.add(scene);
        Debug.print("Adding scene to scene index " + (scenes.size() - 1));
        return scenes.size() - 1;
    }

    /***
     * Returns a Scene at a specific index value in the ArrayList.
     *
     * @param sceneIndex - Index value of Scene
     * @return - Scene at specified index
     */
    public Scene getScene(int sceneIndex) {
        return scenes.get(sceneIndex);
    }

    /***
     * Returns width of the Window.
     *
     * @return - Width of the Window
     */
    public int getWidth() {
        return width;
    }

    /***
     * Returns height of the Window.
     *
     * @return - Height of the Window
     */
    public int getHeight() {
        return height;
    }

    /***
     * Returns the current Scene index value in the ArrayList.
     *
     * @return - Current Scene index value
     */
    public int getCurrentSceneIndex() {
        return currentScene;
    }

    /***
     * Returns the Scene that is currently activated.
     *
     * @return - Currently activated Scene
     */
    public Scene getCurrentScene() {
        return scenes.get(getCurrentSceneIndex());
    }

    /***
     * Deactivates old scenes and activates the one at an index.
     *
     * @param currentScene - Scene to be activated
     */
    public void setCurrentScene(int currentScene) {
        // checks if there are actually scenes to work with
        if (scenes.size() != 0) {
            if (scenes.get(getCurrentSceneIndex()).isActive())
                Debug.print("Deactivating scene: " + getCurrentSceneIndex() + " - " + getCurrentScene().getClass().getName());
            scenes.get(getCurrentSceneIndex()).deactivate(); // deactivates old scene if it's active

            this.currentScene = currentScene; // sets current scene index
            scenes.get(getCurrentSceneIndex()).activate(); // activates current scene

            // deletes all other scenes when the menu scene is drawn (prevents memory leaks)
            if(currentScene == 0) {
                MenuScene menuScene = (MenuScene) getScene(0);
                scenes.clear();
                scenes.add(menuScene);
                inputHandler = new InputHandler(this);
            }
        }
    }

    /***
     * Provides Scenes with the ResourceManager to use resources from.
     *
     * @return - ResourceManager full of Resources
     */
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    /***
     * Provides Scenes with the InputHandler.
     *
     * @return - InputHandler for the Window
     */
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Sound getSound() {
        return sound;
    }
}
