package edu.lancs.game.entity;

import edu.lancs.game.Window;

import java.util.Random;

public class Chest extends Entity{
    private boolean isOpen;
    private Pickup loot;

    public Chest(Window window, int xPos, int yPos) {
        super(window, "chest_closed", xPos, yPos, false);
        isOpen = false;
        Random random = new Random(); // TODO: Add random
        int randomPickupId = random.nextInt(Pickup.Type.values().length - 1); // key is not in chest yet
        loot = new Pickup(getWindow(), getPosition().x, getPosition().y + 40, randomPickupId);
    }

    public Chest(Window window, int xPos, int yPos, int lootId) {
        super(window, "chest_closed", xPos, yPos, false);
        isOpen = false;
        loot = new Pickup(getWindow(), getPosition().x, getPosition().y + 40, lootId);
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
        isOpen = true;
        setTexture(getWindow().getResourceManager().getTextures("chest_open"));
        return loot;
    }
}
