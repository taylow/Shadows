package edu.lancs.game.entity.behaviour;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

public class AIBehaviour {
    ColliderBehaviour collider;
    Sprite aiSprite;
    Sprite targetSprite;

    /**
     * Constructs the AI behaviour of a game entity.
     *
     * @param collider     the collider behaviour of the current game entity.
     * @param aiSprite     the sprite of the current game entity.
     * @param targetSprite the sprite of the targeted game entity.
     */
    public AIBehaviour(ColliderBehaviour collider, Sprite aiSprite, Sprite targetSprite) {
        this.collider = collider;
        this.aiSprite = aiSprite;
        this.targetSprite = targetSprite;
    }

    public void update() {
        chaseTarget();
    }

    /**
     * chases the target that was specified when the constructor was called.
     */
    public void chaseTarget() {
        Vector2f diff = Vector2f.sub(targetSprite.getPosition(), aiSprite.getPosition());
        collider.velocity = Vector2f.div(diff, 130.0f);
    }
}
