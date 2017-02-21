package edu.lancs.game.gui;

import edu.lancs.game.Window;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Decoration extends RectangleShape {
    /***
     * Creates a rectangle with a set texture, position, and dimension.
     * TODO: Could do with a re-write to add other functionality, although this is just a basic rectangle and should need much
     *
     * @param window - Window being passed around
     * @param textureName - The name of the texture to be used
     * @param xPos - X position on screen
     * @param yPos - Y position of screen
     * @param width - Width of the decoration
     * @param height - Height of the decoration
     */
    public Decoration(Window window, String textureName, float xPos, float yPos, float width, float height) {
        setTexture(window.getResourceManager().getTextures(textureName));
        setSize(new Vector2f(width, height));
        setPosition(new Vector2f(xPos, yPos));
    }
}
