package edu.lancs.game.generation;

import edu.lancs.game.Window;
import org.jsfml.graphics.Color;

public class Wall extends Tile {

    public Wall(Window window, String name, Direction direction, int number, int positionX, int positionY, Color color) {
        super(window, name, direction, number, positionX, positionY, true, color); // make a collidable tile
    }

    @Override
    public void collide() {

    }
}
