package edu.lancs.game.gui;

import edu.lancs.game.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;

import java.util.ArrayList;

public class HUD {

    private Window window;

    private ArrayList<Decoration> decorations;
    private ArrayList<Text> texts;
    private Decoration healthBar;
    private Decoration armour;
    private Text scoreText;

    public HUD(Window window) {
        decorations = new ArrayList<>();
       /* scoreText = new Text("TEST", getWindow().getResourceManager().getFont("BLKCHCRY"));
        scoreText.setPosition(100, 100);
        scoreText.setColor(Color.YELLOW);*/
        //texts.add(scoreText);
        //decorations.add(new Decoration())
    }

    public ArrayList<Decoration> getDecorations() {
        return decorations;
    }

    public ArrayList<Text> getTexts() {
        return texts;
    }

    public Window getWindow() {
        return window;
    }
}
