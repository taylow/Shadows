package edu.lancs.game.generation;

import edu.lancs.game.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import java.util.Random;

import static edu.lancs.game.Constants.MAP_TILE_HEIGHT;
import static edu.lancs.game.Constants.MAP_TILE_WIDTH;
import static edu.lancs.game.generation.Tile.Direction.NONE;

public abstract class Tile extends Sprite {

    private Window window;
    private String name;
    private Direction direction;
    private int number;

    private boolean canCollide;

    public Tile(Window window, String name, Direction direction, int number, int positionX, int positionY, boolean canCollide, Color colour) {
        String textureName = name + "_" + (direction == NONE ? "" : direction + "_") + number;
        setTexture(window.getResourceManager().getTextures(textureName)); // loads the tile with the set naming sceme
        setPosition(new Vector2f(positionX * MAP_TILE_WIDTH, positionY * MAP_TILE_HEIGHT));

        this.window = window;
        this.name = name;
        this.direction = direction;
        this.number = number;
        this.canCollide = canCollide;

        setColor(colour);
    }

    /***
     * Returns whether or not Entities can collide with this Tile.
     *
     * @return - Whether or not Entities can collide with this Tile
     */
    public boolean isCanCollide() {
        return canCollide;
    }

    /***
     * Sets whether or not Entities can collide with this tile.
     *
     * @param canCollide - Whether Entities can collide with this Tile
     */
    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    /***
     * An abstract method that will trigger when an entity collides with this tile (e.g. Door would teleport)
     */
    public abstract void collide();

    /***
     * Returns the name of the texture being used (e.g. green_stone_wall).
     *
     * @return - The name of the texture being used
     */
    public String getName() {
        return name;
    }

    /***
     * Sets the name of the texture being used.
     *
     * @param name - Name of the texture being used (not the full name, just the NAME prefix)
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Returns the direction of the tile (N, E, S, W, etc).
     *
     * @return - Direction of the tile
     */
    public Direction getDirection() {
        return direction;
    }

    /***
     * Returns the number of the texture being used (_NUMBER).
     *
     * @return - Number of the texture being used
     */
    public int getNumber() {
        return number;
    }

    public Window getWindow() {
        return window;
    }

    /***
     * Direction of the tile texture (none = floor tile)
     */
    public enum Direction {
        N, NE, E, SE, S, SW, W, NW, NONE
    }
}
