package edu.lancs.game.entity.behaviour;

import edu.lancs.game.Window;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.w3c.dom.Entity;

/**
 * Handles movement of entity.
 */
public class ColliderBehaviour {
    private final float SPEED_NORMAL = 1.0f;
    public Vector2f velocity = new Vector2f(0, 0);
    public float speed = SPEED_NORMAL;
    public boolean hasCollidedAlong[] = new boolean[2];
    private Sprite sprite;

    private Window window;

    public ColliderBehaviour(Window window, Sprite sprite, float speed) {
        this.window = window;
        this.sprite = sprite;
        this.speed = speed;
    }

    /**
     * Moves the entity to some direction by some velocity.
     */
    public void update() {
        hasCollidedAlong[Axis.X.ordinal()] = false;
        hasCollidedAlong[Axis.Y.ordinal()] = false;
    }

    public void inCollisionWith(Entity entity, Axis axis) {
        //boolean collision = axis == Axis.X ? collidedAlongX : collidedAlongY;
        if (hasCollidedAlong[axis.ordinal()] == false) {
            Vector2f move;
            if (axis == Axis.X) {
                move = new Vector2f(velocity.x, 0);
            } else move = new Vector2f(0, velocity.y);
            sprite.setPosition(Vector2f.sub(sprite.getPosition(), move));
            hasCollidedAlong[axis.ordinal()] = true;
        }
    }

    /**
     * Checks if an entity is out of the screen boundaries.
     *
     * @return true if its out of screen, false otherwise.
     */
    public boolean isOutOfScreenBoundaries() {
        FloatRect screenRect = new FloatRect(new Vector2f(0, 0), new Vector2f(window.getSize()));
        return screenRect.intersection(sprite.getGlobalBounds()) == null;
    }

    public enum Axis {X, Y}
}
