package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.buttons.BackButton;
import edu.lancs.game.gui.buttons.Button;
import edu.lancs.game.gui.buttons.MenuButton;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Text;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

import static edu.lancs.game.Constants.MENU_BUTTON_HEIGHT;
import static edu.lancs.game.Constants.MENU_BUTTON_WIDTH;
import static edu.lancs.game.gui.buttons.MenuButton.Type.NEW_GAME;
import static edu.lancs.game.gui.buttons.MenuButton.Type.PLAY_GAME;

public class UserNameScene extends Scene {

    private String name;

    private ArrayList<Decoration> decorations;
    private Text nameText;
    private ArrayList<Button> buttons;

    public UserNameScene(Window window, Scene returnScene) {
        super(window);
        setTitle("Enter Name");
        setBackgroundColour(Color.BLACK);

        name = "";

        buttons = new ArrayList<>();
        decorations = new ArrayList<>();

        // decorations and buttons to be added
        decorations.add(new Decoration(window, "menu_dungeon_background", 0, 0, getWindow().getWidth(), getWindow().getHeight()));
        decorations.add(new Decoration(window, "username_entry", getWindow().getWidth() / 2 - (901 / 2), getWindow().getHeight() / 2 - (164 / 2), 901, 164));
        buttons.add(new MenuButton(window, this, "New Game", PLAY_GAME, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), getWindow().getHeight() / 2 - (164 / 2) + 200));
        buttons.add(new BackButton(window, returnScene, 20, getWindow().getHeight() - (MENU_BUTTON_HEIGHT / 2) - 20));
        nameText = new Text(name, getWindow().getResourceManager().getFont("8-BIT"));
        nameText.setPosition(getWindow().getWidth() / 2 - (901 / 2) + 10, getWindow().getHeight() / 2 - (164 / 2) + 95);
        nameText.setColor(Color.WHITE);
    }

    /***
     * Draws the decorations and buttons for the TutorialScene.
     *
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        decorations.forEach(window::draw);
        buttons.forEach(window::draw);
        window.draw(nameText);
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
                        button.setText(name);
                        button.click();
                        break;
                    }
                }
                break;
            case KEY_PRESSED:
                // inputs into the nameText
                Keyboard.Key key = event.asKeyEvent().key;
                if(name.length() < 25) {
                    if (key.toString().toLowerCase().matches("a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z")) {
                        name += key.toString();
                    }
                }

                if(key == Keyboard.Key.BACKSPACE) {
                    if(name.length() > 0)
                        name = name.substring(0, name.length() - 1);
                }

                if(name.length() > 0)
                    buttons.get(0).setDisabled(false);
                else
                    buttons.get(0).setDisabled(true);

                nameText.setString(name);
                break;
        }
    }
}
