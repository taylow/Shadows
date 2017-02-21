package edu.lancs.game.entity;

import edu.lancs.game.Debug;
import edu.lancs.game.InputHandler;
import edu.lancs.game.Window;
import org.jsfml.graphics.Texture;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.entity.Player.State.*;

public class Player extends Entity {

    // actual player variables
    private int health;
    private int hearts; // different to health as this is the amount they can have (health / hearts)
    private int score;

    private int testHealth = -1; // TODO: Remove once finished with HUD testing

    // the entity variables
    private InputHandler inputHandler;

    private ArrayList<Texture> idleAnimation;
    private ArrayList<Texture> runAnimation;
    private ArrayList<Texture> attackAnimation;
    private ArrayList<Texture> deathAnimation;

    private State state;

    public Player(Window window) {
        super(window, "idle_knight", PLAYER_STARTING_X, PLAYER_STARTING_Y, true);
        // initialise player stats (health, score, etc)
        health = PLAYER_STARTING_HEALTH;
        hearts = PLAYER_STARTING_HEALTH; //FIXME: fix it so odd numbers don't cause issues
        score = 0;

        inputHandler = getWindow().getInputHandler();

        // load animations
        idleAnimation = getWindow().getResourceManager().getAnimations("idle_knight");
        runAnimation = getWindow().getResourceManager().getAnimations("run_knight");
        attackAnimation = getWindow().getResourceManager().getAnimations("attack_knight");
        deathAnimation = getWindow().getResourceManager().getAnimations("dead_knight");

        // set state to IDLE as the player has just been created
        setState(IDLE);
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
        // test HUD system (HUD TEST TODO: REMOVE THIS ONCE HUD TESTING IS FINISHED)
        health += testHealth;
        if (health == 0)
            testHealth = 1;
        else if (health == hearts)
            testHealth = -1;
        score++;

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
            if (getState() != IDLE)
                setState(IDLE);
        }
    }

    /***
     * Moves the player left by PLAYER_BASE_MOVEMENT
     */
    public void moveLeft() {
        move(-PLAYER_BASE_MOVEMENT, 0f);
        setScale(-1.f, 1.f); // flip the sprite to face right
        if (state != RUNNING)
            setState(RUNNING);
    }

    /***
     * Moves the player right by PLAYER_BASE_MOVEMENT
     */
    public void moveRight() {
        move(PLAYER_BASE_MOVEMENT, 0f);
        setScale(1.f, 1.f); // flip the sprite to face left
        if (state != RUNNING)
            setState(RUNNING);
    }

    /***
     * Moves the player up by PLAYER_BASE_MOVEMENT
     */
    public void moveUp() {
        move(0f, -PLAYER_BASE_MOVEMENT);
        if (state != RUNNING)
            setState(RUNNING);
    }

    /***
     * Moves the player down by PLAYER_BASE_MOVEMENT
     */
    public void moveDown() {
        move(0f, PLAYER_BASE_MOVEMENT);
        if (state != RUNNING)
            setState(RUNNING);
    }

    /***
     * Returns the current State of the Player.
     *
     * @return - Current State of the Player
     */
    public State getState() {
        return state;
    }

    /***
     * Sets the current state of the Player and changes the animation accordingly.
     *
     * @param state - Current state of the Player
     */
    public void setState(State state) {
        this.state = state;
        switch (state) {
            case IDLE:
                setAnimation(idleAnimation);
                break;

            case RUNNING:
                setAnimation(runAnimation);
                break;

            case ATTACKING:
                setAnimation(attackAnimation);
                break;

            case DYING:
                setAnimation(deathAnimation);
                break;
        }
        Debug.print("Player state: " + state);
    }

    /***
     * Performs an attack and sets the Players state to ATTACKING.
     * TODO: Add hit detection in here. Should check the current frame syncs up with the hit blow.
     */
    public void attack() {
        setState(ATTACKING);
    }

    /***
     * Returns the players health.
     *
     * @return - Players health
     */
    public int getHealth() {
        return health;
    }

    /***
     * Returns the players score.
     *
     * @return - Players score
     */
    public int getScore() {
        return score;
    }

    /***
     * Returns the players hearts.
     *
     * @return - Players hearts
     */
    public int getHearts() {
        return hearts;
    }

    /***
     * States for the players animation/actions
     */
    public enum State {
        IDLE, RUNNING, ATTACKING, DYING
    }
}
