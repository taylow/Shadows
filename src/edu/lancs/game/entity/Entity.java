package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public abstract class Entity extends RectangleShape{
    private Sprite sprite;
    private Texture texture;

    public Entity(Window window, String textureName, float xPos, float yPos, float width, float height) {
        setTexture(window.getResourceManager().getTextures(textureName));
        setSize(new Vector2f(width, height));
        setPosition(new Vector2f(xPos, yPos));
    }

    public abstract void performAction();
}
