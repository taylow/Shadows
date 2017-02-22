package edu.lancs.game.generation;

import edu.lancs.game.Window;

public class Wall extends Tile {

    public Wall(Window window, String name, Direction direction, int number, int positionX, int positionY) {
        super(window, name, direction, number, positionX, positionY, true); // make a collidable tile
    }

    @Override
    public void collide() {

    }
}
