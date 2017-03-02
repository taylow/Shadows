package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.audio.Sound;
import org.jsfml.graphics.Color;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.util.Random;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.entity.Actor.State.*;

public class Enemy extends Actor {

    private int runes;
    private Clock timing;

    private int magicCooldown;
    private boolean canRange;

    private Actor targetActor;

    public Enemy(Window window, int positionX, int positionY, int health, Color recolour) {
        super(window, "knight", positionX, positionY, true, health, health, ENEMY_WEAPON_DAMAGE, ENEMY_BASE_MOVEMENT);
        setColor(recolour);
        Random random = new Random();
        runes = random.nextInt(ENEMY_STARTING_RUNES);
        timing = new Clock();
        magicCooldown = random.nextInt(1000) + ENEMY_RANGE_COOLDOWN_TIME;
        canRange = false;
        changeScale(ENEMY_SCALE_WIDTH, ENEMY_SCALE_HEIGHT);
    }

    /***
     * Special actions should be performed in here.
     */
    @Override
    public void performAction() {
    }

    @Override
    public void range() {
        setState(RANGING);
        if(runes > 0) {
            setSound(new Sound(getWindow().getResourceManager().getSound("magic_fire")));
            getSound().setPitch(1f);
            getSound().play();
            Vector2f estimatedPosition = new Vector2f(targetActor.getPosition().x + (targetActor.getVelocity().x * ENEMY_RANGE_ACCURACY), targetActor.getPosition().y + (targetActor.getVelocity().y * ENEMY_RANGE_ACCURACY)); // increases the accuracy of enemies
            getProjectiles().add(new Projectile(getWindow(), estimatedPosition, getPosition(), ENEMY_MAGIC_DAMAGE, 10, false));
            runes--;
        }
    }

    /***
     * This updates the players variables every iteration.
     */
    @Override
    public void update() {
        handleDeath();
        handleMovement();
        nextFrame();
    }

    /***
     * Checks if directional keys are bing pressed and moves the character in that direction is so.
     * Support for diagonal movement is present as each if condition is on its own line.
     */
    public void handleMovement() {
        if(getState() != DYING) {
            if(getState() == ATTACKING || getState() == RANGING) {
                if (getFrame() == getAnimation().size() - 1) {
                    setState(IDLE);
                }
            } else if (targetActor != null) {
                Vector2f diff = Vector2f.sub(targetActor.getPosition(), getPosition());
                setVelocity(Vector2f.div(diff, 130.0f));

                if (diff.x < 0)
                    setScale(-getScaleX(), getScaleY()); // flip the sprite to face left
                else
                    setScale(getScaleX(), getScaleY()); // flip the sprite to face right

                //TODO: Add line of site so the Enemy is facing you
                if(timing.getElapsedTime().asMilliseconds() > magicCooldown && runes > 0 && canRange) {
                    range();
                    timing.restart();
                } else {
                    if (Math.abs(diff.x) > 90 || Math.abs(diff.y) > 90) {
                        move(getVelocity());
                        if (getState() != RUNNING)
                            setState(RUNNING);
                    } else {
                        if (getState() != ATTACKING)
                            attack();
                    }
                }
            } else {
                if (getState() != IDLE) {
                    setState(IDLE);
                }
            }
        }
    }

    /***
     * Sets the target actor for the enemy to attack.
     *
     * @param targetActor - Enemy the target will attack
     */
    public void setTargetActor(Actor targetActor) {
        this.targetActor = targetActor;
    }

    public void handleDeath() {
        if(getHealth() == 0 && getState() != DYING) {
            setState(DYING);
        }

        // if the dying animation is on the last frame
        if(getState() == DYING && getFrame() == getAnimation().size() - 2)
            setDead(true); // set to dead
    }

    public void setCanRange(boolean canRange) {
        this.canRange = canRange;
    }

    public int getRuneCount() {
        return runes;
    }

    public Pickup getRunes() {
        return new Pickup(getWindow(), getPosition().x, getPosition().y, 5, runes);
    }
}
