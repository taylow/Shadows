package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import org.jsfml.audio.Sound;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import static edu.lancs.game.Constants.GAME_TITLE;

public abstract class Scene {
    private Window window;

    // scene information
    private String title;
    private Color backgroundColour;
    private boolean isActive;

    private Vector2i mousePosition;

    private Sound music;

    public Scene(Window window) {
        this.window = window;
        this.title = "";
        this.backgroundColour = Color.BLACK; // default colour is black - use setBackgroundColour to change
        this.mousePosition = new Vector2i(1, 1);
    }

    /***
     * Displays the current Scene onto the game Window and handles events.
     */
    public void display() {
        activate(); // sets this scene to the active scene
        getWindow().setTitle(GAME_TITLE + (title.isEmpty() ? "" : " - " + title)); // adds title if the scene has one

        // while the scene is active, draw everything and handle all the events
        while (isActive()) {
            getWindow().clear(getBackgroundColour()); // clear the background to the background colour
            draw(getWindow()); // draw to the Window

            for (Event event : getWindow().pollEvents()) {
                // check if default events happen (close, etc)
                switch (event.type) {
                    case CLOSED: // presse
                        window.close();
                        System.exit(0); // closes the system
                        break;
                    case MOUSE_MOVED:
                        mousePosition = event.asMouseEvent().position; // updates mouse position on current scene TODO: finish the InputHandler part
                        getWindow().getInputHandler().updateMouseMovement(event);
                    default:
                        executeEvent(event); // any other action that isn't listed here will be handled in executeEvent() in the sub class
                        break;
                }
            }
            getWindow().display(); // draws all the changed to the Window
        }
    }

    /***
     * An abstract method to draw vectors to the Window.
     *
     * @param window - Window that the objects will be drawn to
     */
    public abstract void draw(Window window);

    /***
     * An abstract method to handle events specific to this Scene.
     *
     * @param event - Event that has been triggered
     */
    public abstract void executeEvent(Event event);

    /***
     * Sets the current scene to active and will draw to the Window until deactivated.
     */
    public void activate() {
        isActive = true;
    }

    /***
     * Sets the current scene to inactive which will stop it from being drawn to the Window.
     */
    public void deactivate() {
        isActive = false;
    }

    /***
     * Returns whether or not the scene is active.
     *
     * @return - Current activity state of the scene
     */
    public boolean isActive() {
        return isActive;
    }

    /***
     * Returns the Window this scene is being drawn to.
     *
     * @return - Window that owns this scene
     */
    public Window getWindow() {
        return window;
    }

    /***
     * Returns the clear colour set in the scene.
     *
     * @return - Clear colour
     */
    public Color getBackgroundColour() {
        return backgroundColour;
    }

    /***
     * Sets the clear colour that will colour the background on update
     *
     * @param backgroundColour - Clear colour
     */
    public void setBackgroundColour(Color backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    /***
     * Returns the title of the scene (appended to the original title as TITLE - SCENE_TITLE).
     *
     * @return - Scene title
     */
    public String getTitle() {
        return title;
    }

    /***
     * Sets the title of the scene to be displayed on the Window(appended to the original title as TITLE - SCENE_TITLE).
     *
     * @param title - Scene title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /***
     * Gets the current mouse position on the scene in a Vector2i format.
     *
     * @return - Vector2i mouse coordinate
     */
    public Vector2i getMousePosition() {
        return mousePosition;
    }

    /***
     * Returns the current background music.
     *
     * @return - Current background music
     */
    public Sound getMusic() {
        return music;
    }

    /***
     * Sets the background music to a Sound
     *
     * @param music - Music to be played in the background
     */
    public void setMusic(String music) {
        this.music = getWindow().getResourceManager().getSound(music);
        this.music.setLoop(true);
        this.music.play();
    }
}
