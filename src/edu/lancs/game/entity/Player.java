package edu.lancs.game.entity;

import edu.lancs.game.Debug;
import edu.lancs.game.InputHandler;
import edu.lancs.game.Window;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.entity.Actor.State.ATTACKING;
import static edu.lancs.game.entity.Actor.State.IDLE;

public class Player extends Actor {

    // actual player variables
    private int score;
    private int batteryLevel;
    private int gold;

    // the entity variables
    private InputHandler inputHandler;

    public Player(Window window) {
        super(window, "knight", PLAYER_STARTING_X, PLAYER_STARTING_Y, true, PLAYER_STARTING_HEALTH, PLAYER_STARTING_HEALTH);
        // initialise player stats (health, score, etc)
        score = 0;
        batteryLevel = 100;
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
            if (inputHandler.isaKeyPressed()) {
                moveLeft();
                //setBatteryLevel(batteryLevel + 1); //TODO: Just to test the battery. This will increment with the pickups
            }
            if (inputHandler.issKeyPressed())
                moveDown();
            if (inputHandler.isdKeyPressed()) {
                moveRight();
                //setBatteryLevel(batteryLevel - 1); //TODO: Just to test the battery. This will decrement with the game time
            }
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    /***
     * Returns the battery level of the player for the player's light source.
     *
     * @return - Light source battery level
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if(batteryLevel >= 0 && batteryLevel <= 100)
            this.batteryLevel = batteryLevel;
        else
            Debug.error("Invalid battery level (" + batteryLevel + ") should be between 0% and 100%");
    }
}
