package edu.lancs.game.gui;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;

import java.util.ArrayList;

import static edu.lancs.game.Constants.HUD_HEART_DIMENSION;

public class HUD {

    private Window window;
    private Player player;

    private ArrayList<Decoration> decorations;
    private ArrayList<Decoration> hearts;

    private ArrayList<Text> texts;
    private Decoration healthBar;
    private Decoration armour;
    private Text scoreText;

    public HUD(Window window, Player player) {
        this.window = window;
        this.player = player;

        decorations = new ArrayList<>();
        hearts = new ArrayList<>();

        scoreText = new Text("000000", getWindow().getResourceManager().getFont("BLKCHCRY"));
        scoreText.setPosition(getWindow().getWidth() - 150, 10);
        scoreText.setColor(Color.YELLOW);

        //texts.add(scoreText);
    }

    /***
     * Updates all the images based off new variables
     */
    public void update() {
        updateHealth();
        scoreText.setString(String.format("%06d", player.getScore())); // updates the players score
    }

    /***
     * Clears the hearts Decoration ArrayList and repopulates it with the correct hearts.
     */
    private void updateHealth() {
        hearts = new ArrayList<>();
        int fullHearts = (int) Math.floor(player.getHealth() / 2);
        int halfHearts = player.getHealth() % 2;
        int emptyHearts = (player.getHearts() - player.getHealth()) / 2;

        for(int heart = 0; heart < fullHearts; heart++)
            hearts.add(new Decoration(window, "heart_full", 20 + (heart * (HUD_HEART_DIMENSION + 5)), 20, HUD_HEART_DIMENSION, HUD_HEART_DIMENSION));

        if(halfHearts == 1)
            hearts.add(new Decoration(window, "heart_half", 20 + (fullHearts * (HUD_HEART_DIMENSION + 5)), 20, HUD_HEART_DIMENSION, HUD_HEART_DIMENSION));

        for(int heart = 0; heart < emptyHearts; heart++)
            hearts.add(new Decoration(window, "heart_empty", 20 + ((fullHearts + halfHearts + heart) * (HUD_HEART_DIMENSION + 5)), 20, HUD_HEART_DIMENSION, HUD_HEART_DIMENSION));
    }

    public ArrayList<Decoration> getDecorations() {
        return decorations;
    }

    public ArrayList<Decoration> getHearts() {
        return hearts;
    }

    public ArrayList<Text> getTexts() {
        return texts;
    }

    public Window getWindow() {
        return window;
    }

    public Text getScoreText() {
        return scoreText;
    }
}
