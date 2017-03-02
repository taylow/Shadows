package edu.lancs.game.entity;

import edu.lancs.game.InputHandler;
import edu.lancs.game.Window;
import org.jsfml.audio.Sound;
import org.jsfml.system.Vector2f;

import static edu.lancs.game.Constants.*;
import static edu.lancs.game.entity.Actor.State.ATTACKING;
import static edu.lancs.game.entity.Actor.State.IDLE;
import static edu.lancs.game.entity.Actor.State.RANGING;

public class Player extends Actor {

    // actual player variables
    private int score;
    private int batteryLevel;
    private int gold;
    private int kills;

    private long startTime;
    private long currentTime;
    private long lastUpdate;

    private boolean hasBossKey;
    private int speedBoostPickups;
    private int runePickups;

    // the entity variables
    private InputHandler inputHandler;

    public Player(Window window) {
        super(window, "knight", PLAYER_STARTING_X, PLAYER_STARTING_Y, true, PLAYER_STARTING_HEALTH, PLAYER_STARTING_HEALTH, PLAYER_WEAPON_DAMAGE, PLAYER_BASE_MOVEMENT);
        // initialise player stats (health, score, etc)
        score = 0;
        batteryLevel = 100;
        speedBoostPickups = 0;
        kills = 0;
        runePickups = PLAYER_STARTING_RUNES;
        inputHandler = getWindow().getInputHandler();
        startTime = System.currentTimeMillis();
        currentTime = startTime;
        lastUpdate = startTime;
        hasBossKey = false;
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
        currentTime = (System.currentTimeMillis() - startTime) / 1000;
        if(currentTime != lastUpdate && batteryLevel != 0) {
            setBatteryLevel(batteryLevel - 1);
            lastUpdate = currentTime;
        }
    }

    /***
     * Checks if directional keys are bing pressed and moves the character in that direction is so.
     * Support for diagonal movement is present as each if condition is on its own line.
     */
    public void handleMovement() {
        if (inputHandler.isMoveing() && !inputHandler.isSpaceKeyPressed()) {
            if (inputHandler.iswKeyPressed())
                moveUp();
            if (inputHandler.isaKeyPressed()) {
                moveLeft();
                //setBatteryLevel(batteryLevel + 1); //TODO: Just to test the battery. This will increment with the pickups
            }
            if (inputHandler.issKeyPressed())
                moveDown();
            if (inputHandler.isdKeyPressed()) {
                moveRight();
                //setBatteryLevel(batteryLevel - 1); //TODO: Just to test the battery. This will decrement with the game time
            }
        } else if (inputHandler.isSpaceKeyPressed()) {
            if (getState() != ATTACKING)
                attack();
        } else if (inputHandler.isMouseClicked()) {
            if(getState() != RANGING) {
                range(true);
            }
        } else if (inputHandler.isTriggerPressed()) {
            if(getState() != RANGING) {
                range(false);
            }
        } else {
            if (getState() != IDLE) {
                setState(IDLE);
            }
        }
    }

    /***
     * Performs a range attack and sets the Players state to RANGING.
     */
    public void range(boolean mouse) {
        setState(RANGING);
        if(runePickups > 0) {
            setSound(new Sound(getWindow().getResourceManager().getSound("projectile")));
            getSound().setPitch(0.8f);
            getSound().play();
            if(mouse)
                getProjectiles().add(new Projectile(getWindow(), getWindow().getInputHandler().getMousePosition(), getPosition(), PLAYER_MAGIC_DAMAGE, 10, true));
            else
                getProjectiles().add(new Projectile(getWindow(), new Vector2f(getWindow().getView().getCenter().x + inputHandler.getrAxisX() * 5, getWindow().getView().getCenter().y + inputHandler.getrAxisY() * 5), getPosition(), PLAYER_MAGIC_DAMAGE, 10, false));
            runePickups--;
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
     * Adds a score onto the Players score.
     *
     * @param score - Score to add
     */
    public void addScore(int score) {
        this.score += score;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    /***
     * Returns the battery level of the player for the player's light source.
     *
     * @return - Light source battery level
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if(batteryLevel >= 0) {
            if(batteryLevel <= 100)
                this.batteryLevel = batteryLevel;
            else
                this.batteryLevel = 100;
        } else {
            this.batteryLevel = 0;
        }
    }

    public long getTimeAlive() {
        return currentTime;
    }

    public boolean hasBossKey() {
        return hasBossKey;
    }

    public void setHasBossKey(boolean hasBossKey) {
        this.hasBossKey = hasBossKey;
    }

    public int getSpeedBoostPickups() {
        return speedBoostPickups;
    }

    public void setSpeedBoostPickups(int speedBoostPickups) {
        this.speedBoostPickups = speedBoostPickups;
    }

    public int getRunePickups() {
        return runePickups;
    }

    public void setRunePickups(int runePickups) {
        this.runePickups = runePickups;
    }

    public int getKills() {
        return kills;
    }
}
