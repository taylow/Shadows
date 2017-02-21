package edu.lancs.game.generation;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.generation.Tile.Direction.NONE;

public class Tile extends Sprite {

    /***
     * Direction of the tile texture (none = floor tile)
     */
    public enum Direction {
        N, NE, E, SE, S, SW, W, NW, NONE
    }

    public Tile(Window window, String name, Direction direction, int number, int positionX, int positionY) {
        String textureName = name + "_" + (direction == NONE ? "" : direction + "_") + number;
        Debug.print(textureName);
        setTexture(window.getResourceManager().getTextures(textureName)); // loads the tile with the set naming sceme
        setPosition(new Vector2f(positionX * MAP_TILE_WIDTH, positionY * MAP_TILE_HEIGHT));
        //setOrigin(Vector2f.div(new Vector2f(getTexture().getSize()), 2)); // sets the origin to the centre
        //TODO: add functionality into here
    }
}
