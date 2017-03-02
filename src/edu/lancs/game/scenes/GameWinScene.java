package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.buttons.BackButton;
import edu.lancs.game.gui.buttons.Button;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.View;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

import static edu.lancs.game.Constants.MENU_BUTTON_HEIGHT;

public class GameWinScene extends Scene {

    private ArrayList<Decoration> decorations;
    private ArrayList<Button> buttons;
    private ArrayList<Text> texts;
    private Player player;
    private Text scoreText;

    public GameWinScene(Window window, Scene returnScene, Player player) {
        super(window);
        this.player = player;
        setTitle("Winner!");
        getWindow().getResourceManager().stopSound("boss_music");
        getWindow().getResourceManager().getSound("menu_music").play(); // TODO: Gameover sound, then menu sound

        setBackgroundColour(Color.CYAN);

        buttons = new ArrayList<>();
        decorations = new ArrayList<>();
        texts = new ArrayList<>();

        // decorations and buttons to be added
        decorations.add(new Decoration(window, "game_win", getWindow().getWidth() / 2 - (1280 / 2), getWindow().getHeight() / 2 - (720 / 2), 1280, 720));
        buttons.add(new BackButton(window, returnScene, 20, getWindow().getHeight() - (MENU_BUTTON_HEIGHT / 2) - 20));

        scoreText = new Text("000000", getWindow().getResourceManager().getFont("BLKCHCRY"));
        scoreText.setPosition(getWindow().getWidth() - 150, 10); // TODO: Sorta redundant now and I have updatePositions
        scoreText.setColor(Color.YELLOW);
        texts.add(scoreText); // adds this text to the ArrayList of texts (means for multiple texts with one one draw)
        scoreText.setString(String.format("%06d", player.getScore())); // updates the players score
    }

    /***
     * Draws the decorations and buttons for the TutorialScene.
     *
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        getWindow().setView(new View(new FloatRect(0.0f, 0.0f, getWindow().getSize().x, getWindow().getSize().y)));
        decorations.forEach(window::draw);
        buttons.forEach(window::draw);
        texts.forEach(window::draw);
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