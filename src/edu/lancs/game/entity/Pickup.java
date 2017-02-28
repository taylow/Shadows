package edu.lancs.game.entity;

import edu.lancs.game.Constants;
import edu.lancs.game.Window;

import java.util.ConcurrentModificationException;

public class Pickup extends Entity {

    public enum Type {
        HEALTH, BATTERY, COINS, SPEED, COINS2, KEY
    }

    private boolean isUsed;
    private Type type;

    public Pickup(Window window, float xPos, float yPos, int pickupId) {
        super(window, "heart_full", xPos, yPos, false);
        setScale(0.1f, 0.1f);
        //FIXME: Doesn't check whether it's valid, possible NullPointer
        this.type = Type.values()[pickupId];
        isUsed = false;
        switch (type) {
            case HEALTH:
                setTexture(getWindow().getResourceManager().getTextures("heart_full"));
                break;

            case BATTERY:
                setTexture(getWindow().getResourceManager().getTextures("battery"));
                break;

            case COINS:
                setTexture(getWindow().getResourceManager().getTextures("coin"));
                break;

            case SPEED:
                setTexture(getWindow().getResourceManager().getTextures("boost_speed"));
                break;

            case COINS2:
                setTexture(getWindow().getResourceManager().getTextures("coin2"));
                break;

            case KEY:
                setTexture(getWindow().getResourceManager().getTextures("boss_door_key"));
                break;
        }
    }

    @Override
    public void performAction() {

    }

    @Override
    public void update() {

    }

    public void givePerk(Player player) {
        switch (type) {
            case HEALTH:
                if(player.getHealth() < player.getHearts()) {
                    player.setHealth(player.getHealth() + 2);
                    setUsed(true);
                }
                break;

            case BATTERY:
                if(player.getBatteryLevel() < 100) {
                    player.setBatteryLevel(player.getBatteryLevel() + 25);
                    setUsed(true);
                }
                break;

            case COINS:
                player.addScore(1000);
                setUsed(true);
                break;

            case COINS2:
                player.addScore(2000);
                setUsed(true);
                break;

            case SPEED:
                player.setSpeed(Constants.PLAYER_BASE_MOVEMENT + (float) 0.5);
                setUsed(true);
                break;

            case KEY:
                player.addScore(1000);
                setUsed(true);
                break;
        }
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
