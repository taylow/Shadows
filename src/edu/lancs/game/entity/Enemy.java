package edu.lancs.game.entity;

import edu.lancs.game.InputHandler;
import edu.lancs.game.Window;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.entity.Actor.State.*;

public class Enemy extends Actor {

    // actual player variables
    private int score;

    // the entity variables
    private InputHandler inputHandler;

    private Actor targetActor;

    public Enemy(Window window, int positionX, int positionY, int health) {
        super(window, "knight", positionX, positionY, true, health, health);
        // initialise player stats (health, score, etc)
        score = 0;
        inputHandler = getWindow().getInputHandler();
        setColor(new Color(180, 180, 180)); //TODO: You can add a colour overlay to reuse textures
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

        if (targetActor != null) {
            Vector2f diff = Vector2f.sub(targetActor.getPosition(), getPosition());
            setVelocity(Vector2f.div(diff, 130.0f));

            if (diff.x < 0)
                setScale(-1.f, 1.f); // flip the sprite to face left
            else
                setScale(1.f, 1.f); // flip the sprite to face right

            if (Math.abs(diff.x) > 90 || Math.abs(diff.y) > 90) {
                move(getVelocity());
                if (getState() != RUNNING)
                    setState(RUNNING);
            } else {
                if (getState() != ATTACKING)
                    attack();
            }
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

    /***
     * Sets the target actor for the enemy to attack.
     *
     * @param targetActor - Enemy the target will attack
     */
    public void setTargetActor(Actor targetActor) {
        this.targetActor = targetActor;
    }
}
