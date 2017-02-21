package edu.lancs.game.generation;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import java.util.Random;

import static edu.lancs.game.Constants.MAP_TILE_HEIGHT;
import static edu.lancs.game.Constants.MAP_TILE_WIDTH;
import static edu.lancs.game.generation.Tile.Direction.NONE;

public class Tile extends Sprite {

    private boolean canCollide;

    public Tile(Window window, String name, Direction direction, int number, int positionX, int positionY, boolean canCollide) {
        String textureName = name + "_" + (direction == NONE ? "" : direction + "_") + number;
        Debug.print(textureName);
        setTexture(window.getResourceManager().getTextures(textureName)); // loads the tile with the set naming sceme
        setPosition(new Vector2f(positionX * MAP_TILE_WIDTH, positionY * MAP_TILE_HEIGHT));
        this.canCollide = canCollide;
        //TODO: add functionality into here such as door, etc (or extend this into a door)
        Random random = new Random();
        setColor(new Color(random.nextInt(254),random.nextInt(254), random.nextInt(254)));
    }

    /***
     * Direction of the tile texture (none = floor tile)
     */
    public enum Direction {
        N, NE, E, SE, S, SW, W, NW, NONE
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
     * Returns whether or not Entities can collide with this Tile.
     *
     * @return - Whether or not Entities can collide with this Tile
     */
    public boolean isCanCollide() {
        return canCollide;
    }
}
