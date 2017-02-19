package edu.lancs.game.gui;

import edu.lancs.game.Window;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Decoration extends RectangleShape {
    public Decoration(Window window, String textureName, float xPos, float yPos, float width, float height) {
        setTexture(window.getResourceManager().getTextures(textureName));
        setSize(new Vector2f(width, height));
        setPosition(new Vector2f(xPos, yPos));
    }
}
