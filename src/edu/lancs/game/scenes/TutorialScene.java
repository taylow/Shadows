package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.buttons.BackButton;
import edu.lancs.game.gui.buttons.Button;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;

public class TutorialScene extends Scene {

    private ArrayList<Decoration> decorations;
    private ArrayList<Button> buttons;

    public TutorialScene(Window window, Scene returnScene) {
        super(window);
        setTitle("Tutorial");
        setBackgroundColour(Color.CYAN);
        buttons = new ArrayList<>();
        decorations = new ArrayList<>();
        decorations.add(new Decoration(window, "menu_wood_background", 0, 0, getWindow().getWidth(), getWindow().getHeight()));
        decorations.add(new Decoration(window, "menu_scroll", getWindow().getWidth() / 2 - (600 / 2), getWindow().getHeight() / 2 - (600 / 2), 600, 600));
        decorations.add(new Decoration(window, "tutorial_controls", getWindow().getWidth() / 2 - (800 / 2), getWindow().getHeight() / 2 - (400 / 2), 800, 400));
        buttons.add(new BackButton(window, returnScene, 20, getWindow().getHeight() - (MENU_BUTTON_HEIGHT / 2) - 20));
    }

    @Override
    public void draw(Window window) {
        decorations.forEach(window::draw);
        buttons.forEach(window::draw);
    }

    @Override
    public void executeEvent(Event event) {
        switch(event.type) {
            case MOUSE_MOVED:
                for(Button button : buttons) {
                    if(button.getGlobalBounds().intersection(new FloatRect(getMousePosition().x, getMousePosition().y, 1.0f, 1.0f)) != null) {
                        button.setSelected(true);
                    } else {
                        button.setSelected(false);
                    }
                }
                break;
            case MOUSE_BUTTON_PRESSED:
                for(Button button : buttons) {
                    if(button.isSelected()) {
                        button.click();
                        break;
                    }
                }
                break;
        }
    }
}
