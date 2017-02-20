package edu.lancs.game.entity.behaviour;

import edu.lancs.game.InputHandler;
import edu.lancs.game.Window;
import edu.lancs.game.entity.Entity;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

public class PlayerStateBehaviour extends StateBehaviour {
    InputHandler input;
    Entity entity;
    Clock debug = new Clock();

    public PlayerStateBehaviour(Window window, InputHandler input, Entity entity) {
        super(entity.sprite, entity.collider, entity.anims);
        this.window = window;
        this.input = input;
        this.entity = entity;
    }

    public void handleInput() {
        // Handle state switch.
        switch (currentState) {
            case IDLE:
                if ((boolean) input.keyState.get(Keyboard.Key.A) || (boolean) input.keyState.get(Keyboard.Key.D)
                        || (boolean) input.keyState.get(Keyboard.Key.W) || (boolean) input.keyState.get(Keyboard.Key.S)) {
                    currentState = StateBehaviour.State.MOVE;
                }
                if ((boolean) input.keyState.get(Keyboard.Key.SPACE)) {
                    currentState = StateBehaviour.State.ATTACK;
                }
                break;

            case MOVE:
                if (input.keyState.containsValue(true) == false) {
                    currentState = StateBehaviour.State.IDLE;
                }
                if ((boolean) input.keyState.get(Keyboard.Key.SPACE)) {
                    //App.resources.getSound( "Projectile" ).play();
                    currentState = StateBehaviour.State.ATTACK;
                }
                break;
            case ATTACK:
                if (currentAnim.isEnding()) {
                    if ((boolean) input.keyState.get(Keyboard.Key.A) || (boolean) input.keyState.get(Keyboard.Key.D)
                            || (boolean) input.keyState.get(Keyboard.Key.W) || (boolean) input.keyState.get(Keyboard.Key.S)) {
                        currentState = StateBehaviour.State.MOVE;
                    } else if (input.keyState.containsValue(true) == false) {
                        currentState = StateBehaviour.State.IDLE;
                    }
                }
                break;
        }

        if (input.getKeyState(Keyboard.Key.R) && debug.getElapsedTime().asSeconds() > 0.2f) {
            debug.restart();
            if (currentState.ordinal() == State.values().length - 1) {
                currentState = State.IDLE;
            } else currentState = State.values()[currentState.ordinal() + 1];
        }
        if (input.getKeyState(Keyboard.Key.G)) {
            currentAnim.setAnimationSpeed(0.01f);
        }
        if (input.getKeyState(Keyboard.Key.H)) {
            currentAnim.setAnimationSpeed(1.0f);
        }

        if (entity.health.get() <= 0) {
            currentState = State.DEAD;
        }

        //Logic
        if (currentState == State.IDLE || currentState == State.ATTACK || currentState == State.DEAD) {
            collider.velocity = new Vector2f(0, 0);
        }

        if (currentState == State.MOVE) {
            collider.velocity = new Vector2f(0, 0);
            if (input.getKeyState(Keyboard.Key.A)) {
                if (sprite.getScale().x > 0) {
                    Utility.turnLeft(sprite);
                }
                collider.velocity = Vector2f.add(collider.velocity, new Vector2f(-collider.speed, 0));
            }

            if ((boolean) input.keyState.get(Keyboard.Key.D)) {
                if (sprite.getScale().x < 0) {
                    Utility.turnRight(sprite);
                }
                collider.velocity = Vector2f.add(collider.velocity, new Vector2f(collider.speed, 0));
            }

            if ((boolean) input.keyState.get(Keyboard.Key.W)) {
                collider.velocity = Vector2f.add(collider.velocity, new Vector2f(0, -collider.speed));
            }

            if ((boolean) input.keyState.get(Keyboard.Key.S)) {
                collider.velocity = Vector2f.add(collider.velocity, new Vector2f(0, collider.speed));
            }
        }

        if (currentState == State.DEAD) {
            if (currentAnim.isEnding()) {
                entity.status = GameObject.Status.INACTIVE;
            }
        }
    }
}
