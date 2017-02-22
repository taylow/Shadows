package edu.lancs.game.generation;

import edu.lancs.game.Window;
import org.jsfml.graphics.Color;

public class Floor extends Tile {

    public Floor(Window window, String name, Direction direction, int number, int positionX, int positionY, Color color) {
        super(window, name, direction, number, positionX, positionY, false, color); // make a non-collidable tile
    }

    @Override
    public void collide() {

    }
}
