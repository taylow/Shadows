package edu.lancs.game;

import edu.lancs.game.gui.Button;
import javafx.animation.Animation;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;

import java.util.ArrayList;

public class ResourceManager {
    private ArrayList<Texture> textures;
    private ArrayList<Animation> animations;
    private ArrayList<Text> texts;
    private ArrayList<Button> buttons;


    public ResourceManager() {
        textures = new ArrayList<>();
        animations = new ArrayList<>();
        texts = new ArrayList<>();
        buttons = new ArrayList<>();
    }

    public ArrayList<Texture> getTextures() {
        return textures;
    }

    public ArrayList<Animation> getAnimations() {
        return animations;
    }
}
