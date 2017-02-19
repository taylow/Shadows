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
    private Decoration titleBanner;

    public MenuScene(Window window) {
        super(window);
        setTitle("Main Menu");
        setBackgroundColour(Color.CYAN);
        buttons = new ArrayList<>();
        buttons.add(new MenuButton(window, this, "New Game", NEW_GAME, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230));
        buttons.add(new MenuButton(window, this, "High Scores", HIGH_SCORES, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 1.1f));
        buttons.add(new MenuButton(window, this, "Level Editor", LEVEL_EDITOR, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 2.2f));
        buttons.add(new MenuButton(window, this, "Quit", EXIT, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 3.3f));
        titleBanner = new Decoration(window, "title_banner", getWindow().getWidth() / 2 - (TITLE_BANNER_WIDTH / 2), 10, TITLE_BANNER_WIDTH, TITLE_BANNER_HEIGHT);
    }

    @Override
    public void draw(Window window) {
        buttons.forEach(window::draw);
        window.draw(titleBanner);
    }

    @Override
    public void executeEvent(Event event) {
        switch(event.type) {
            case MOUSE_MOVED:
                for(Button button : buttons) {
                    if(button.getGlobalBounds().intersection(new FloatRect(getMousePosition().x, getMousePosition().y, 1.0f, 1.0f)) != null) {
                        button.setSelected(true);
                        break;
                    } else {
                        button.setSelected(false);
                    }
                }
                break;
            case MOUSE_BUTTON_PRESSED:
                for(Button button : buttons) {
                    if(button.isSelected()) {
                        button.performEvent();
                        break;
                    }
                }
                break;
        }
    }
}
