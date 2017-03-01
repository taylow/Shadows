package edu.lancs.game.entity;

import edu.lancs.game.Window;

import java.util.Random;

import static edu.lancs.game.Constants.PLAYER_BASE_MOVEMENT;

public class Pickup extends Entity {

    public enum Type {
        HEALTH, BATTERY, SPEED, GOLD_COIN, SILVER_COIN, RUNE, KEY // make sure key is at the end
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

            case RUNE:
                setTexture(getWindow().getResourceManager().getTextures("fire_rune"));
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
                }
                break;

            case BATTERY:
                if(player.getBatteryLevel() < 100) {
                    player.setBatteryLevel(player.getBatteryLevel() + 100);
                }
                break;

            case GOLD_COIN:
                player.addScore(1000);
                break;

            case SILVER_COIN:
                player.addScore(500);
                break;

            case SPEED:
                player.setSpeedBoostPickups(player.getSpeedBoostPickups() + 1);
                break;

            case RUNE:
                Random random = new Random();
                player.setRunePickups(player.getRunePickups() + random.nextInt(10) + 1);
                break;

            case KEY:
                player.addScore(1000);
                player.setHasBossKey(true);
                break;
        }
        setUsed(true);
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
