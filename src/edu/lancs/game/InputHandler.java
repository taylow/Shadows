package edu.lancs.game;

import org.jsfml.window.Keyboard;

public class InputHandler {

    private Window window;
    private boolean wKeyPressed;
    private boolean aKeyPressed;
    private boolean sKeyPressed;
    private boolean dKeyPressed;
    private boolean spaceKeyPressed;

    public InputHandler(Window window) {
        this.window = window;

        // sets the current directions and space to none
        wKeyPressed = false;
        aKeyPressed = false;
        sKeyPressed = false;
        dKeyPressed = false;
        sKeyPressed = false;

        //TODO: Move mouse movement into here. Easy job, but it's not as important at the minute.
    }

    /***
     * Sets the current key event to whether or not it is currently being pressed.
     * This makes movement smooth as the movement speed is not linked to the refresh rate of the keyboard.
     *
     * @param key - Key currently performing an event
     */
    public void processInputs(Keyboard.Key key) {
        switch(key) {
            case W:
                wKeyPressed = Keyboard.isKeyPressed(key);
                break;

            case A:
                aKeyPressed = Keyboard.isKeyPressed(key);
                break;

            case S:
                sKeyPressed = Keyboard.isKeyPressed(key);
                break;

            case D:
                dKeyPressed = Keyboard.isKeyPressed(key);
                break;

            case SPACE:
                spaceKeyPressed = Keyboard.isKeyPressed(key);
                break;
        }
    }

    public boolean isaKeyPressed() {
        return aKeyPressed;
    }

    public boolean isdKeyPressed() {
        return dKeyPressed;
    }

    public boolean issKeyPressed() {
        return sKeyPressed;
    }

    public boolean iswKeyPressed() {
        return wKeyPressed;
    }

    public boolean isSpaceKeyPressed() {
        return spaceKeyPressed;
    }

    public boolean isMoveing() {
        return wKeyPressed || aKeyPressed || sKeyPressed || dKeyPressed;
    }


}
