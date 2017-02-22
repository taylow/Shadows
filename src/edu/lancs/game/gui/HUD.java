package edu.lancs.game.gui;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.View;

import java.util.ArrayList;

import static edu.lancs.game.Constants.HUD_HEART_DIMENSION;

public class HUD {

    private Window window;
    private Player player;

    private float offsetX;
    private float offsetY;

    private ArrayList<Decoration> decorations;
    private ArrayList<Decoration> hearts;

    private ArrayList<Text> texts;
    private Text scoreText;

    public HUD(Window window, Player player) {
        this.window = window;
        this.player = player;

        decorations = new ArrayList<>();
        texts = new ArrayList<>();
        hearts = new ArrayList<>();
        offsetX = 0;
        offsetY = 0;

        scoreText = new Text("000000", getWindow().getResourceManager().getFont("BLKCHCRY"));
        scoreText.setPosition(getWindow().getWidth() - 150, 10); // TODO: Sorta redundant now and I have updatePositions
        scoreText.setColor(Color.YELLOW);
        texts.add(scoreText); // adds this text to the ArrayList of texts (means for multiple texts with one one draw)
    }

    /***
     * Updates all the images based off new variables
     */
    public void update() {
        updatePositions();
        updateHealth();
        scoreText.setString(String.format("%06d", player.getScore())); // updates the players score
    }

    /***
     * Updates the HUD element positions for when the View changes.
     */
    private void updatePositions() {
        offsetX = getWindow().getView().getCenter().x - (getWindow().getView().getSize().x / 2);
        offsetY = getWindow().getView().getCenter().y - (getWindow().getView().getSize().y / 2);
        scoreText.setPosition(offsetX + getWindow().getWidth() - 150, offsetY + 10);
    }

    /***
     * Clears the hearts Decoration ArrayList and repopulates it with the correct hearts.
     */
    private void updateHealth() {
        hearts = new ArrayList<>();
        int fullHearts = (int) Math.floor(player.getHealth() / 2); // number of full hearts
        int halfHearts = player.getHealth() % 2; // should either equal 1 or 0 as it would turn into empty/full
        int emptyHearts = (player.getHearts() - player.getHealth()) / 2; // number of empty hearts

        // draw full hearts
        for (int heart = 0; heart < fullHearts; heart++)
            hearts.add(new Decoration(window, "heart_full", offsetX + 20 + (heart * (HUD_HEART_DIMENSION + 5)), offsetY + 20, HUD_HEART_DIMENSION, HUD_HEART_DIMENSION));

        // draw half heart
        if (halfHearts == 1)
            hearts.add(new Decoration(window, "heart_half", offsetX + 20 + (fullHearts * (HUD_HEART_DIMENSION + 5)), offsetY + 20, HUD_HEART_DIMENSION, HUD_HEART_DIMENSION));

        // draw empty hearts
        for (int heart = 0; heart < emptyHearts; heart++)
            hearts.add(new Decoration(window, "heart_empty", offsetX + 20 + ((fullHearts + halfHearts + heart) * (HUD_HEART_DIMENSION + 5)), offsetY + 20, HUD_HEART_DIMENSION, HUD_HEART_DIMENSION));
    }

    /***
     * Returns all Decorations used in the HUD, excluding hearts.
     *
     * @return - All Decorations used in the HUD
     */
    public ArrayList<Decoration> getDecorations() {
        return decorations;
    }

    /***
     * Returns the ArrayList of hearts
     *
     * @return - Hearts for he health bar
     */
    public ArrayList<Decoration> getHearts() {
        return hearts;
    }

    /***
     * Returns all texts used in the HUD.
     *
     * @return - All text in the HUD
     */
    public ArrayList<Text> getTexts() {
        return texts;
    }

    public Window getWindow() {
        return window;
    }
}
