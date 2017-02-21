package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.buttons.Button;
import edu.lancs.game.gui.buttons.MenuButton;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Text;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.gui.buttons.MenuButton.Type.*;

public class MenuScene extends Scene {

    private ArrayList<Button> buttons;
    private ArrayList<Decoration> decorations;
    private Text text;

    public MenuScene(Window window) {
        super(window);
        setTitle("Main Menu");
        setMusic("menu_music");
        setBackgroundColour(Color.BLACK);

        buttons = new ArrayList<>();
        decorations = new ArrayList<>();

        // buttons and decorations to be displayed in the MenuScene
        buttons.add(new MenuButton(window, this, "New Game", NEW_GAME, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230));
        buttons.add(new MenuButton(window, this, "High Scores", HIGH_SCORES, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 1.1f));
        buttons.add(new MenuButton(window, this, "Tutorial", TUTORIAL, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 2.2f));
        buttons.add(new MenuButton(window, this, "Quit", EXIT, getWindow().getWidth() / 2 - (MENU_BUTTON_WIDTH / 2), 230 + MENU_BUTTON_HEIGHT * 3.3f));
        decorations.add(new Decoration(window, "menu_wood_background", 0, 0, getWindow().getWidth(), getWindow().getHeight()));
        decorations.add(new Decoration(window, "menu_scroll", getWindow().getWidth() / 2 - ((TITLE_BANNER_WIDTH + 200) / 2), 10, TITLE_BANNER_WIDTH + 200, TITLE_BANNER_HEIGHT + 30));
        decorations.add(new Decoration(window, "title_banner", getWindow().getWidth() / 2 - (TITLE_BANNER_WIDTH / 2), 20, TITLE_BANNER_WIDTH, TITLE_BANNER_HEIGHT));
    }

    /***
     * Draws the decorations and buttons for the MenuScene.
     *
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        decorations.forEach(window::draw);
        buttons.forEach(window::draw);
    }

    /***
     * Handles mouse movement when on the MenuScene
     *
     * @param event - Event that has been triggered
     */
    @Override
    public void executeEvent(Event event) {
        switch (event.type) {
            case MOUSE_MOVED:
                for (Button button : buttons) {
                    // checks if the mouse intersects with any of the buttons (somewhat inefficient way, but it works perfectly fine)
                    //FIXME: Ideally, this should be in InputHandler, but it's fine here for now until we need move detection around the game
                    //FIXME: Also, when resizing, this seems to mess the menu up
                    if (button.getGlobalBounds().intersection(new FloatRect(getMousePosition().x, getMousePosition().y, 1.0f, 1.0f)) != null) {
                        button.setSelected(true); // sets the button to selected
                    } else {
                        button.setSelected(false); // sets the button to normal
                    }
                }
                break;
            case MOUSE_BUTTON_PRESSED:
                // checks which button is currently selected and performs the click() action
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
