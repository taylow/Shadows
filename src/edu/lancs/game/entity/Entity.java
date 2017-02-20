package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public abstract class Entity extends RectangleShape{
    private Sprite sprite;
    private Texture texture;
    private ArrayList<Texture> animation;
    private Window window;

    public Entity(Window window, String textureName, float xPos, float yPos, float width, float height) {
        this.window = window;
        setTexture(window.getResourceManager().getTextures(textureName));
        setSize(new Vector2f(width, height));
        setPosition(new Vector2f(xPos, yPos));
    }

    public abstract void performAction();

    public abstract void update();

    public Window getWindow() {
        return window;
    }
}
