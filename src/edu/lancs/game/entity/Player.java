package edu.lancs.game.entity;

import edu.lancs.game.Window;

import static edu.lancs.game.Constants.*;

public class Player extends Entity {
    public Player(Window window, String textureName, float xPos, float yPos) {
        super(window, textureName, PLAYER_STARTING_X, PLAYER_STARTING_Y, PLAYER_WIDTH, PLAYER_HEIGHT);

    }

    @Override
    public void performAction() {
        System.out.println("TEST");
    }
}
