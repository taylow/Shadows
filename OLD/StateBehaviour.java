package edu.lancs.game.entity.behaviour;

import edu.lancs.game.entity.Entity;
import org.jsfml.graphics.Sprite;

import java.util.ArrayList;


/**
 * State machine for npcs.
 */
public abstract class StateBehaviour
{
    public State currentState = State.IDLE;
    State previousState;
    Sprite sprite;
    ColliderBehaviour collider;
    ArrayList<AnimationBehaviour> anims;
    public AnimationBehaviour currentAnim;

    public enum State
    {
        IDLE, MOVE, ATTACK, DEAD
    }

    public StateBehaviour(Sprite sprite, ColliderBehaviour collider, ArrayList<AnimationBehaviour> anims )
    {
        this.sprite = sprite;
        this.collider = collider;
        this.anims = anims;
        this.currentAnim = anims.get( 0 );
    }

    public void changeAnimationTo( State requestedAnim )
    {
        if( currentState != previousState )
        {
            currentAnim = anims.get( requestedAnim.ordinal() );
            currentAnim.reset();
            previousState = currentState;
            System.out.println("animation changed to " + requestedAnim.ordinal());
        }
    }


    public boolean isAttacking( Entity entity )
    {
        return currentState == StateBehaviour.State.ATTACK && currentAnim.isEnding()
        && World.isCollision( sprite.getGlobalBounds(), entity.sprite.getGlobalBounds() );
    }

    abstract void handleInput();

    public void update()
    {
        handleInput();
        changeAnimationTo( currentState );
        currentAnim.update();
    }
}
