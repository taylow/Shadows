package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.Random;

import static edu.lancs.game.entity.Actor.State.*;

public abstract class Actor extends Entity {

    // actual player variables
    private int health;
    private int hearts; // different to health as this is the amount they can have (health / hearts)
    private int weaponDamage;
    private boolean isDead;
    private float speed;

    private Vector2f velocity;

    private ArrayList<Projectile> projectiles;

    private boolean isCollidingLeft = false;
    private boolean isCollidingRight = false;
    private boolean isCollidingUp = false;
    private boolean isCollidingDown = false;

    private Sound sound;

    private ArrayList<Texture> idleAnimation;
    private ArrayList<Texture> runAnimation;
    private ArrayList<Texture> attackAnimation;
    private ArrayList<Texture> deathAnimation;
    private ArrayList<Texture> rangeAnimation;

    private State state;

    public Actor(Window window, String actorName, float xPos, float yPos, boolean isAnimated, int maxHealth, int currentHealth, int weaponDamage, float speed) {
        super(window, actorName + "_idle", xPos, yPos, isAnimated);
        // initialise player stats (health, score, etc)
        health = currentHealth;
        hearts = maxHealth;
        isDead = false;
        this.speed = speed;
        this.weaponDamage = weaponDamage;
        velocity = new Vector2f(0, 0);
        projectiles = new ArrayList<>();

        // load animations
        idleAnimation = getWindow().getResourceManager().getAnimations(actorName + "_idle");
        runAnimation = getWindow().getResourceManager().getAnimations(actorName + "_run");
        attackAnimation = getWindow().getResourceManager().getAnimations(actorName + "_melee");
        deathAnimation = getWindow().getResourceManager().getAnimations(actorName + "_dead");
        rangeAnimation = getWindow().getResourceManager().getAnimations(actorName + "_melee");

        // set state to IDLE as the actor has just been created
        setState(IDLE);
    }

    /***
     * Special actions should be performed in here.
     */
    @Override
    public abstract void performAction();

    /***
     * This updates the players variables every iteration.
     */
    @Override
    public abstract void update();

    /***
     * Checks if directional keys are bing pressed and moves the character in that direction is so.
     * Support for diagonal movement is present as each if condition is on its own line.
     */
    public abstract void handleMovement();

    /***
     * Moves the player left by PLAYER_BASE_MOVEMENT
     */
    public void moveLeft() {
        velocity = new Vector2f(-speed, 0);
        if (!isCollidingLeft) {
            move(velocity);
            setCollidingRight(false);
        }
        setScale(-1.f, 1.f); // flip the sprite to face right
        if (state != RUNNING)
            setState(RUNNING);
    }

    /***
     * Moves the player right by PLAYER_BASE_MOVEMENT
     */
    public void moveRight() {
        velocity = new Vector2f(speed, 0);
        if (!isCollidingRight) {
            move(velocity);
            setCollidingLeft(false);
        }
        setScale(1.f, 1.f); // flip the sprite to face left
        if (state != RUNNING)
            setState(RUNNING);
    }

    /***
     * Moves the player up by PLAYER_BASE_MOVEMENT
     */
    public void moveUp() {
        velocity = new Vector2f(0, -speed);
        if (!isCollidingUp) {
            move(velocity);
            setCollidingDown(false);
        }
        if (state != RUNNING)
            setState(RUNNING);
    }

    /***
     * Moves the player down by PLAYER_BASE_MOVEMENT
     */
    public void moveDown() {
        velocity = new Vector2f(0, speed);
        if (!isCollidingDown) {
            move(velocity);
            setCollidingUp(false);
        }
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

        // stops all sound when the state changes
        if(sound != null)
            sound.stop();

        switch (state) {
            case IDLE:
                setAnimation(idleAnimation);
                setOrigin(42, 91);
                break;

            case RUNNING:
                setAnimation(runAnimation);
                setOrigin(49, 98);
                break;

            case ATTACKING:
                Random random = new Random();
                setAnimation(attackAnimation);
                setOrigin(50, 94);
                // plays the sword sound on loop FIXME: Really temporary, needs to be improved. Could use the "knight" in the file name so each type has its own attack sound (knight_melee_attack.wav)
                sound = new Sound(getWindow().getResourceManager().getSound("melee_sword"));
                sound.setLoop(true);
                sound.setPitch(random.nextFloat() + 0.8f);
                sound.play();
                break;

            case RANGING:
                setAnimation(attackAnimation);
                setOrigin(50, 94);
                break;

            case DYING:
                setAnimation(deathAnimation);
                setOrigin(138, 94);
                break;
        }
        //Debug.print("Actor state: " + state);
    }

    /***
     * Returns the current velocity of the character.
     *
     * @return - Current velocity of the character
     */
    public Vector2f getVelocity() {
        return velocity;
    }

    /***
     * Sets the velocity of the Actor for it to move.
     *
     * @param velocity - Velocity of where to move the Actor
     */
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    /***
     * Checks whether or not the Player is colliding with a Sprite.
     *
     * @param sprite - Sprite the player is checking against
     * @return - Whether or not it is colliding
     */
    public boolean checkCollision(Sprite sprite) {
        return (getGlobalBounds().intersection(new FloatRect(sprite.getPosition().x, sprite.getPosition().x, 1, 1)) != null);
    }

    /***
     * Performs an attack and sets the Players state to ATTACKING.
     * TODO: Add hit detection in here. Should check the current frame syncs up with the hit blow.
     */
    public void attack() {
        setState(ATTACKING);
    }


    /***
     * Performs a range attack and sets the Players state to RANGING.
     */
    public void range() {
        setState(RANGING);
        projectiles.add(new Projectile(getWindow(), getWindow().getInputHandler().getMousePosition(), getPosition(), 10, 10, true));
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
     * Sets the Actors current health
     *
     * @param health - Current health
     */
    public void setHealth(int health) {
        if (health >= 0) {
            if(health <= hearts)
                this.health = health;
            else
                this.health = hearts;
        } else {
            this.health = 0;
        }
    }

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float speed){
        if (speed >= 0 ){
            this.speed = speed;
        }else{
            this.speed = 0;
        }
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
     * Returns whether or not the Player is colliding currently.
     *
     * @return - Whether or not the Player is currently colliding
     */
    public boolean isColliding() {
        return isCollidingLeft || isCollidingRight || isCollidingUp || isCollidingDown;
    }

    /***
     * Sets whether or not the Player is currently colliding left.
     *
     * @param collidingLeft - Whether or not the Player is currently colliding left
     */
    public void setCollidingLeft(boolean collidingLeft) {
        isCollidingLeft = collidingLeft;
    }

    /***
     * Sets whether or not the Player is currently colliding right.
     *
     * @param collidingRight - Whether or not the Player is currently colliding right
     */
    public void setCollidingRight(boolean collidingRight) {
        isCollidingRight = collidingRight;
    }

    /***
     * Sets whether or not the Player is currently colliding up.
     *
     * @param collidingUp - Whether or not the Player is currently colliding up
     */
    public void setCollidingUp(boolean collidingUp) {
        isCollidingUp = collidingUp;
    }

    /***
     * Sets whether or not the Player is currently colliding down.
     *
     * @param collidingDown - Whether or not the Player is currently colliding down
     */
    public void setCollidingDown(boolean collidingDown) {
        isCollidingDown = collidingDown;
    }

    /***
     * Resets all collision to false.
     */
    public void resetCollision() {
        isCollidingUp = false;
        isCollidingDown = false;
        isCollidingLeft = false;
        isCollidingRight = false;
    }

    /***
     * Returns whether or not the Actor is in range of an attack of another Actor.
     *
     * @param actor - Actor you are trying to attack
     * @return - Whether or not you can attack that actor
     */
    public boolean canAttackReach(Actor actor) {
        Vector2f diff = Vector2f.sub(actor.getPosition(), this.getPosition());
        return ((Math.abs(diff.y) > 0 && Math.abs(diff.y) < 40) && (Math.abs(diff.x) > 30 && Math.abs(diff.x) < 100));
    }

    /***
     * States for the players animation/actions
     */
    public enum State {
        IDLE, RUNNING, ATTACKING, DYING, RANGING
    }

    public void damage(int damage) {
        setHealth(health - damage);
    }

    public ArrayList<Texture> getDeathAnimation() {
        return deathAnimation;
    }

    public ArrayList<Texture> getAttackAnimation() {
        return attackAnimation;
    }

    public ArrayList<Texture> getIdleAnimation() {
        return idleAnimation;
    }

    public ArrayList<Texture> getRunAnimation() {
        return runAnimation;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }
}
