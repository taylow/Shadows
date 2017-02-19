package edu.lancs.game.scenes;

import edu.lancs.game.Window;
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

    public Scene(Window window) {
        this.window = window;
        this.title = "";
        this.backgroundColour = Color.BLACK; // default colour is black - use setBackgroundColour to change

        mousePosition = new Vector2i(1, 1);
    }

    /***
     * Displays the current Scene onto the game Window.
     */
    public void display() {
        activate(); // sets this scene to the active scene
        getWindow().setTitle(GAME_TITLE + (title.isEmpty() ? "" : " - " + title)); // adds title if the scene has one

        // while the scene is active, draw everything and handle all the events
        while (isActive()) {
            getWindow().clear(getBackgroundColour());
            draw(getWindow());

            for (Event event : getWindow().pollEvents()) {
                // check if default events happen (close, etc)
                switch (event.type) {
                    case CLOSED:
                        window.close();
                        System.exit(0);
                        break;
                    case MOUSE_MOVED:
                        mousePosition = event.asMouseEvent().position;
                    default:
                        executeEvent(event);
                        break;
                }
            }
            getWindow().display();
        }
    }

    public abstract void draw(Window window);

    public abstract void executeEvent(Event event);

    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public Window getWindow() {
        return window;
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(Color backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Vector2i getMousePosition() {
        return mousePosition;
    }
}
