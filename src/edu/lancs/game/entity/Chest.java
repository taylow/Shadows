package edu.lancs.game.entity;

import edu.lancs.game.Window;

import java.util.Random;

public class Chest extends Entity{
    private boolean isOpen;

    public Chest(Window window, int xPos, int yPos) {
        super(window, "chest_closed", xPos, yPos, false);
        isOpen = false;
    }

    @Override
    public void performAction() {

    }

    @Override
    public void update() {

    }

    public boolean isOpen() {
        return isOpen;
    }

    public Pickup open() {
        Random random = new Random(); // TODO: Add random
        int randomPickupId = random.nextInt(Pickup.Type.values().length);
        isOpen = true;
        setTexture(getWindow().getResourceManager().getTextures("chest_open"));
        return new Pickup(getWindow(), getPosition().x, getPosition().y + 40, randomPickupId);
    }
}
