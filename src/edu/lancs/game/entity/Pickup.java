package edu.lancs.game.entity;

import edu.lancs.game.Window;

import static edu.lancs.game.Constants.PLAYER_BASE_MOVEMENT;

public class Pickup extends Entity {

    public enum Type {
        HEALTH, BATTERY, SPEED, GOLD_COIN, SILVER_COIN, KEY
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

            case GOLD_COIN:
                setTexture(getWindow().getResourceManager().getTextures("coin_gold"));
                break;

            case SPEED:
                setTexture(getWindow().getResourceManager().getTextures("boost_speed"));
                break;

            case SILVER_COIN:
                setTexture(getWindow().getResourceManager().getTextures("coin_silver"));
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
                    player.setBatteryLevel(player.getBatteryLevel() + 100);
                    setUsed(true);
                }
                break;

            case GOLD_COIN:
                player.addScore(1000);
                setUsed(true);
                break;

            case SILVER_COIN:
                player.addScore(500);
                setUsed(true);
                break;

            case SPEED:
                // FIXME: Not actually added setSpeed yet ;/
                player.setSpeed(PLAYER_BASE_MOVEMENT + 1);
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
