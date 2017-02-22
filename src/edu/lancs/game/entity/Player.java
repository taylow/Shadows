package edu.lancs.game.entity;

import edu.lancs.game.InputHandler;
import edu.lancs.game.Window;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.entity.Actor.State.ATTACKING;
import static edu.lancs.game.entity.Actor.State.IDLE;

public class Player extends Actor {

    // actual player variables
    private int score;

    private int testHealth = -1; // TODO: Remove once finished with HUD testing

    // the entity variables
    private InputHandler inputHandler;

    public Player(Window window) {
        super(window, "knight", PLAYER_STARTING_X, PLAYER_STARTING_Y, true, PLAYER_STARTING_HEALTH, PLAYER_STARTING_HEALTH);
        // initialise player stats (health, score, etc)
        score = 0;
        inputHandler = getWindow().getInputHandler();
    }

    /***
     * Special actions should be performed in here.
     */
    @Override
    public void performAction() {
    }

    /***
     * This updates the players variables every iteration.
     */
    @Override
    public void update() {
        handleMovement();
        nextFrame();
    }

    /***
     * Checks if directional keys are bing pressed and moves the character in that direction is so.
     * Support for diagonal movement is present as each if condition is on its own line.
     */
    public void handleMovement() {
        if (inputHandler.isMoveing() && !inputHandler.isSpaceKeyPressed()) {
            if (inputHandler.iswKeyPressed())
                moveUp();
            if (inputHandler.isaKeyPressed())
                moveLeft();
            if (inputHandler.issKeyPressed())
                moveDown();
            if (inputHandler.isdKeyPressed())
                moveRight();
        } else if (inputHandler.isSpaceKeyPressed()) {
            if (getState() != ATTACKING)
                attack();
        } else {
            if (getState() != IDLE) {
                setState(IDLE);
            }
        }
    }

    /***
     * Returns the players score.
     *
     * @return - Players score
     */
    public int getScore() {
        return score;
    }
}
