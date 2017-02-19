package edu.lancs.game;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;

public class InputHandler {
    private static RenderWindow window;

    public InputHandler(RenderWindow window) {
        this.window = window;
    }

    public static void processInputs() {
        if(Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
            window.close();
        }
    }
}
