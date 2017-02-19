package edu.lancs.game.entity;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Entity {
    private Sprite sprite;
    private Texture texture;

    /***
     * Creates an entity at a certain position with a certain size.
     *
     * @param width
     * @param height
     * @param xPos
     * @param yPos
     */
    public Entity(float width, float height, float xPos, float yPos, String textureDir) {
        texture = new Texture();
        sprite = new Sprite();
        sprite.setTexture(texture);
    }

    public void draw(RenderWindow window) {
        window.draw(sprite);
    }
}
