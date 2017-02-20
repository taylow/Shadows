package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.graphics.Texture;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.entity.Player.State.*;

public class Player extends Entity {

    private ArrayList<Texture> currentAnimation;
    private ArrayList<Texture> idleAnimation;
    private ArrayList<Texture> runAnimation;
    private ArrayList<Texture> attackAnimation;
    private ArrayList<Texture> deathAnimation;

    private int frame;
    private State state;

    public enum State {
        IDLE, RUNNING, ATTACKING, DYING
    }

    public Player(Window window, String textureName) {
        super(window, textureName, PLAYER_STARTING_X, PLAYER_STARTING_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
        frame = 0;
        state = RUNNING;
        idleAnimation = getWindow().getResourceManager().getAnimations("idle_knight");
        runAnimation = getWindow().getResourceManager().getAnimations("run_knight");
        attackAnimation = getWindow().getResourceManager().getAnimations("attack_knight");
        deathAnimation = getWindow().getResourceManager().getAnimations("dead_knight");
    }

    @Override
    public void performAction() {
        System.out.println("TEST");
    }

    @Override
    public void update() {
        switch (state) {
            case IDLE:
                currentAnimation = idleAnimation;
                break;

            case RUNNING:
                currentAnimation = runAnimation;
                break;

            case ATTACKING:
                currentAnimation = attackAnimation;
                break;

            case DYING:
                currentAnimation = deathAnimation;
                break;
        }
        setTexture(currentAnimation.get(frame));
        frame++;
        if(frame >= currentAnimation.size())
            frame = 0;
    }

    public void moveLeft() {
        move(-PLAYER_BASE_MOVEMENT, 0f);
    }

    public void moveRight() {
        move(PLAYER_BASE_MOVEMENT, 0f);
    }

    public void moveUp() {
        move(0f, -PLAYER_BASE_MOVEMENT);
    }

    public void moveDown() {
        move(0f, PLAYER_BASE_MOVEMENT);
    }

}
