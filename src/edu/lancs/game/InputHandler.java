package edu.lancs.game;

import org.jsfml.window.Keyboard;

public class InputHandler {
    private static Window window;

    public InputHandler(Window window) {
        this.window = window;
    }

    public static void processInputs() {
        if(Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
            window.close();
        }
    }
}
