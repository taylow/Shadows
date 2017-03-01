package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.buttons.BackButton;
import edu.lancs.game.gui.buttons.Button;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.window.Joystick;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

import static edu.lancs.game.Constants.MENU_BUTTON_HEIGHT;

public class CreditScene extends Scene {

    private ArrayList<Decoration> decorations;
    private ArrayList<Button> buttons;

    public CreditScene(Window window, Scene returnScene) {
        super(window);
        setTitle("Tutorial");
        setBackgroundColour(Color.CYAN);

        buttons = new ArrayList<>();
        decorations = new ArrayList<>();


        // decorations and buttons to be added
        decorations.add(new Decoration(window, "menu_dungeon_background", 0, 0, getWindow().getWidth(), getWindow().getHeight()));
        decorations.add(new Decoration(window, "menu_scroll1", getWindow().getWidth() / 2 - (600 / 2), getWindow().getHeight() / 2 - (600 / 2), 600, 600));

        // display the correct controls for when a controller is plugged in
        if(Joystick.isConnected(0) || Joystick.isConnected(1) || Joystick.isConnected(2) || Joystick.isConnected(3)) {
            decorations.add(new Decoration(window, "tutorial_controls_controller", getWindow().getWidth() / 2 - (800 / 2), getWindow().getHeight() / 2 - (400 / 2), 800, 400));
        } else {
            decorations.add(new Decoration(window, "tutorial_controls", getWindow().getWidth() / 2 - (800 / 2), getWindow().getHeight() / 2 - (400 / 2), 800, 400));
        }
        buttons.add(new BackButton(window, returnScene, 20, getWindow().getHeight() - (MENU_BUTTON_HEIGHT / 2) - 20));
    }

    /***
     * Draws the decorations and buttons for the TutorialScene.
     *jy7rhdfgmhf,gm,tmnujhqa   swzaqsxwz
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        decorations.forEach(window::draw);
        buttons.forEach(window::draw);
    }

    /***
     * Handles mouse movement when on the TutorialScene
     *
     * @param event - Event that has been triggered
     */
    @Override
    public void executeEvent(Event event) {
        switch (event.type) {
            case MOUSE_MOVED:
                for (Button button : buttons) {
                    if (button.getGlobalBounds().intersection(new FloatRect(getMousePosition().x, getMousePosition().y, 1.0f, 1.0f)) != null) {
                        button.setSelected(true);
                    } else {
                        button.setSelected(false);
                    }
                }
                break;
            case MOUSE_BUTTON_PRESSED:
                for (Button button : buttons) {
                    if (button.isSelected()) {
                        button.click();
                        break;
                    }
                }
                break;
        }
    }
}
