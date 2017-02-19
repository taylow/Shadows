package edu.lancs.game.entity;

import edu.lancs.game.Window;

import static edu.lancs.game.Constants.*;

public class Player extends Entity {
    public Player(Window window, String textureName) {
        super(window, textureName, PLAYER_STARTING_X, PLAYER_STARTING_Y, PLAYER_WIDTH, PLAYER_HEIGHT);

    }

    @Override
    public void performAction() {
        System.out.println("TEST");
    }

    public void moveLeft() {
        move(-PLAYER_BASE_MOVEMENT, 0f);
    }

    public void moveRight() {
        move(PLAYER_BASE_MOVEMENT, 0f);
    }

    public void moveUp() {
        move(0f, -PLAYER_BASE_MOVEMENT);
    }

    public void moveDown() {
        move(0f, PLAYER_BASE_MOVEMENT);
    }
}
