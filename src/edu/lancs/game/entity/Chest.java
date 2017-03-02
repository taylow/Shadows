package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.audio.Sound;

import java.util.Random;

public class Chest extends Entity{
    private boolean isOpen;
    private Pickup loot;

    public Chest(Window window, int xPos, int yPos) {
        super(window, "chest", xPos, yPos, true);
        isOpen = false;
        Random random = new Random(); // TODO: Add random
        int randomPickupId = random.nextInt(Pickup.Type.values().length - 1); // key is not in chest yet
        loot = new Pickup(getWindow(), getPosition().x, getPosition().y + 40, randomPickupId);
        changeScale(1.5f, 1.5f);
    }

    public Chest(Window window, int xPos, int yPos, int lootId) {
        super(window, "chest_metal", xPos, yPos, true);
        isOpen = false;
        loot = new Pickup(getWindow(), getPosition().x, getPosition().y + 40, lootId);
        changeScale(1.5f, 1.5f);
    }

    @Override
    public void performAction() {

    }

    @Override
    public void update() {
        if(isOpen && getFrame() < getAnimation().size() - 1){
            nextFrame(); // animate the open then leave it open
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public Pickup open() {
        if(loot.getType() == Pickup.Type.KEY) {
            Sound sound = getWindow().getResourceManager().getSound("metal_chest");
            sound.play();
        } else {
            Sound sound = getWindow().getResourceManager().getSound("chest_opening");
            sound.play();
        }
        isOpen = true;
        return loot;
    }
}