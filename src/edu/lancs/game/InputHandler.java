package edu.lancs.game;

import org.jsfml.window.Keyboard;

public class InputHandler {

    private Window window;

    public InputHandler(Window window) {
        this.window = window;
    }

    public void processInputs(Keyboard.Key key) {
        switch(key) {
            case W:
                //Keyboard.isKeyPressed(key);
                //TODO: Movement needs to be completely rewritten
        }
    }
}
