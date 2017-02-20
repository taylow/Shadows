package edu.lancs.game.entity.behaviour;

import edu.lancs.game.entity.Entity;
import engine2d.*;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

public class NPCStateBehaviour extends StateBehaviour {
    private Sprite player;
    private float fightingDistance;
    private Clock attackCooldownCounter = new Clock();
    private float attackCooldown = 1.0f;
    private int currentPatrolPointIndex;
    private ArrayList<Vector2f> patrolPoints = new ArrayList<>();
    private Entity entity;
    private float lineOfSight = 300.0f;

    private Move subState = Move.PATROL;

    public NPCStateBehaviour(GameObject object, Sprite player) {
        super(object.sprite, object.collider, object.anims);
        this.object = object;
        this.player = player;
        fightingDistance = player.getGlobalBounds().width / 2 + sprite.getGlobalBounds().width / 2;

        patrolPoints.add(new Vector2f(0, 0));
        patrolPoints.add(new Vector2f(110, 110));
    }

    public void handleInput() {
        Vector2f diff = Vector2f.sub(player.getPosition(), sprite.getPosition());
        float diffX = (diff.x) > 0 ? diff.x - fightingDistance : diff.x + fightingDistance;
        Vector2f nearPlayer = Vector2f.add(sprite.getPosition(), new Vector2f(diffX, diff.y));

        // Handle state switch.
        switch (currentState) {
            case IDLE:

                if (Vec2f.length(Vector2f.sub(sprite.getPosition(), player.getPosition())) < lineOfSight && hasArrived(nearPlayer) == false)//hasArrived( nearPlayer ) == false )
                {
                    currentState = StateBehaviour.State.MOVE;
                }
                if (hasArrived(nearPlayer) && attackCooldownCounter.getElapsedTime().asSeconds() > attackCooldown) {
                    currentState = State.ATTACK;
                }
                break;

            case MOVE:

                //patrol( patrolPoints );

                if (hasArrived(nearPlayer))//&& Math.abs( player.getPosition().y - sprite.getPosition().y ) <= 10.0f )
                {
                    currentState = StateBehaviour.State.ATTACK;
                }
                if (hasArrived(nearPlayer) && attackCooldownCounter.getElapsedTime().asSeconds() < 1.0f || Vec2f.length(Vector2f.sub(sprite.getPosition(), player.getPosition())) > lineOfSight)//&& Math.abs( player.getPosition().y - sprite.getPosition().y ) <= 10.0f )
                {
                    currentState = StateBehaviour.State.IDLE;
                }

                break;

            case ATTACK:
                if (currentAnim.isEnding()) {
                    if (hasArrived(nearPlayer) && attackCooldownCounter.getElapsedTime().asSeconds() < 1.0f) {
                        currentState = State.IDLE;
                    }
                    if (hasArrived(nearPlayer) == false) {
                        currentState = State.MOVE;
                    }
                }
                break;
        }

        if (object.health.get() <= 0) {
            currentState = State.DEAD;
        }

        System.out.println(currentState);
        //System.out.println( hasArrived(  nearPlayer ));

        if (currentState == State.IDLE) {
            collider.velocity = new Vector2f(0, 0);
        }

        if (currentState == State.ATTACK) {
            collider.velocity = new Vector2f(0, 0);
            attackCooldownCounter.restart();
        }

        if (currentState == State.MOVE) {
            collider.velocity = new Vector2f(0, 0);
            headTowards(nearPlayer);
            turnTowards(player.getPosition());
        }

        if (currentState == State.DEAD) {
            if (currentAnim.isEnding()) {
                object.status = GameObject.Status.INACTIVE;
            }
        }
    }

    /**
     * The point at which the entity will be moving.
     */
    void headTowards(Vector2f dest) {
        Vector2f diff = Vec2f.normalize(Vector2f.sub(dest, sprite.getPosition()));
        collider.velocity = new Vector2f(diff.x, diff.y);
    }

    void turnTowards(Vector2f dest) {
        Vector2f diff = Vec2f.normalize(Vector2f.sub(dest, sprite.getPosition()));
        if (diff.x < 0.0f) {
            Utility.turnLeft(sprite);
        } else Utility.turnRight(sprite);
    }

    boolean hasArrived(Vector2f dest) {
        Vector2f diff = Vector2f.sub(dest, sprite.getPosition());
        if (Vec2f.length(diff) < 5) {
            return true;
        }
        return false;
    }

    // Patrols around the points passed to the function.
    public void patrol(ArrayList<Vector2f> patrolPoints) {
        //This is the enemy's current position.
        Vector2f pos = sprite.getPosition();

        //Find destination point
        int destinationPatrolPoint = (currentPatrolPointIndex == patrolPoints.size() - 1) ? 0 : currentPatrolPointIndex + 1;

        //Find the direction.
        float distance = Vec2f.length(Vector2f.sub(patrolPoints.get(destinationPatrolPoint), pos)); //direction is from "here" to "destination".
        object.collider.velocity = (Vec2f.normalize(Vector2f.sub(patrolPoints.get(destinationPatrolPoint), pos)));

        pos = Vector2f.add(pos, object.collider.velocity); //Move the player towards that direction with a certain speed.
        sprite.setPosition(pos); //Set the new position.

        if (distance < 0.1f) //If within a certain threshold
        {
            //Go to the next point, if no more points, go back to 0.
            if (++currentPatrolPointIndex == patrolPoints.size()) {
                currentPatrolPointIndex = 0;
            }
        }
    }

    /**
     * Rotates toward a specified point.
     *
     * @param dest The point at which the entity is going to look at.
     */
    void lookAt(Vector2i dest) {
        Vector2f diff = Vector2f.sub(new Vector2f(dest), sprite.getPosition());
        sprite.setRotation((float) (Math.atan2(-diff.x, diff.y) / Math.PI * 180.0 + 90.0));
    }

    public enum Move {PATROL, CHASE}
}
