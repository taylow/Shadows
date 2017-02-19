package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.buttons.BackButton;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.window.event.Event;

public class TestScene extends Scene {

    private Decoration testImage;
    private BackButton backButton;

    public TestScene(Window window, Scene returnScene) {
        super(window);
        setTitle("Test Scene");
        setBackgroundColour(Color.CYAN);
        testImage = new Decoration(window, "test_scene", getWindow().getWidth() / 2 - (600 / 2), getWindow().getHeight() / 2 - (600 / 2), 600, 600);
        backButton = new BackButton(window, returnScene, 20, 20);
    }

    @Override
    public void draw(Window window) {
        window.draw(testImage);
        window.draw(backButton);
    }

    @Override
    public void executeEvent(Event event) {
        switch (event.type) {
            case MOUSE_MOVED:
                if (backButton.getGlobalBounds().intersection(new FloatRect(getMousePosition().x, getMousePosition().y, 1.0f, 1.0f)) != null) {
                    backButton.setSelected(true);
                } else {
                    backButton.setSelected(false);
                }
                break;
            case MOUSE_BUTTON_PRESSED:
                if(backButton.isSelected()) {
                    backButton.click();
                    break;
                }
        }
    }
}
