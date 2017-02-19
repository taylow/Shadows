package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.gui.buttons.Button;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.buttons.MenuButton;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.gui.buttons.MenuButton.Type.*;

public class MenuScene extends Scene {

    private ArrayList<Button> buttons;
    private ArrayList<Decoration> decorations;

    public MenuScene(Window window) {
        super(window);
        setTitle("Main Menu");
        setBackgroundColour(Color.BLACK);
        buttons = new ArrayList<>();
        decorations = new ArrayList<>();
        buttons.add(new MenuButton(window, this, "New Game", NEW_GAME, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230));
        buttons.add(new MenuButton(window, this, "High Scores", HIGH_SCORES, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 1.1f));
        buttons.add(new MenuButton(window, this, "Level Editor", LEVEL_EDITOR, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 2.2f));
        buttons.add(new MenuButton(window, this, "Quit", EXIT, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 3.3f));
        decorations.add(new Decoration(window, "menu_wood_background", 0, 0, getWindow().getWidth(), getWindow().getHeight()));
        decorations.add(new Decoration(window, "title_banner", getWindow().getWidth() / 2 - (TITLE_BANNER_WIDTH / 2), 20, TITLE_BANNER_WIDTH, TITLE_BANNER_HEIGHT));
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
