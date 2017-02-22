package edu.lancs.game.generation;

import edu.lancs.game.Window;

public class Floor extends Tile {

    public Floor(Window window, String name, Direction direction, int number, int positionX, int positionY) {
        super(window, name, direction, number, positionX, positionY, false); // make a non-collidable tile
    }

    @Override
    public void collide() {

    }
}
