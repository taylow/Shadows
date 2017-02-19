package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.gui.Decoration;
import org.jsfml.graphics.Color;
import org.jsfml.window.event.Event;

public class TestScene extends Scene {

    private Decoration testImage;

    public TestScene(Window window) {
        super(window);
        setTitle("Test Scene");
        setBackgroundColour(Color.CYAN);
        testImage = new Decoration(window, "test_scene", getWindow().getWidth() / 2 - (600 / 2), getWindow().getHeight() / 2 - (600 / 2), 600, 600);
    }

    @Override
    public void draw(Window window) {
        window.draw(testImage);
    }

    @Override
    public void executeEvent(Event event) {

    }
}
