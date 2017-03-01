package edu.lancs.game.generation;

import edu.lancs.game.Window;
import org.jsfml.graphics.Color;

import static edu.lancs.game.generation.Tile.Direction.NONE;

public class Door extends Tile {

    private int destinationRow;
    private int destinationColumn;
    private boolean isLocked;
    private boolean requiresKey;
    //TODO: Add a key system

    public Door(Window window, String name, Direction direction, int number, int positionX, int positionY, int destinationColumn, int destinationRow, boolean isLocked, Color color) {
        super(window, name + (isLocked ? "_closed" : "_open"), direction, number, positionX, positionY, true, color); // create a collidable tile
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.isLocked = isLocked;
        requiresKey = false;
    }

    /***
     * Locks the door so Entities cannot pass through. Also changed the texture of the door to an locked state.
     */
    public void lock() {
        isLocked = true;
        setName(getName().replace("open", "closed"));
        String textureName = getName() + "_" + (getDirection() == NONE ? "" : getDirection() + "_") + getNumber();
        setTexture(getWindow().getResourceManager().getTextures(textureName));
    }

    /***
     * Unlocks the door so Entities can pass through. Also changes the texture of the door to an unlocked state.
     */
    public void unlock() {
        isLocked = false;
        setName(getName().replace("closed", "open"));
        String textureName = getName() + "_" + (getDirection() == NONE ? "" : getDirection() + "_") + getNumber();
        setTexture(getWindow().getResourceManager().getTextures(textureName));
    }

    /***
     * Returns the row in the 2D array of Levels that the door will take the player to.
     *
     * @return - Destination row of levels
     */
    public int getDestinationRow() {
        return destinationRow;
    }

    /***
     * Returns the column in the 2D array of Levels that the door will take the player to.
     *
     * @return - Destination column of levels
     */
    public int getDestinationColumn() {
        return destinationColumn;
    }

    /***
     * Returns the current lock state of the door
     *
     * @return - Current lock state
     */
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public void collide() {

    }

    public boolean requiresKey() {
        return requiresKey;
    }

    public void setRequiresKey(boolean requiresKey) {
        this.requiresKey = requiresKey;
    }
}
