package edu.lancs.game;

import edu.lancs.game.scenes.Scene;
import jdk.internal.util.xml.impl.Input;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;

public class Window extends RenderWindow {
    private int width;
    private int height;

    private ResourceManager resourceManager;
    private InputHandler inputHandler;

    private ArrayList<Scene> scenes;
    private int currentScene;

    /***
     * Creates a window with set game variables such as resolution, display type, and frame rate
     * @param width
     * @param height
     * @param title
     * @param isFullscreen
     * @param frameRate
     */
    public Window(int width, int height, String title, boolean isFullscreen, int frameRate) {
        this.width = width;
        this.height = height;

        this.resourceManager = new ResourceManager(RESOURCE_PATH);
        this.inputHandler = new InputHandler(this);

        this.scenes = new ArrayList<>();

        if (isFullscreen)
            this.create(new VideoMode(width, height), title, WindowStyle.FULLSCREEN);
        else
            this.create(new VideoMode(width, height), title, WindowStyle.DEFAULT);

        this.setFramerateLimit(frameRate);
        this.setCurrentScene(0);
    }

    public void drawScene() {
        while (true) {
            System.out.println(currentScene);
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
        return scenes.size() - 1;
    }

    public Scene getScene(int sceneIndex) {
        return scenes.get(sceneIndex);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(int currentScene) {
        this.currentScene = currentScene;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}
