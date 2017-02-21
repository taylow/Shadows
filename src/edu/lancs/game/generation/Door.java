package edu.lancs.game.generation;

import edu.lancs.game.Window;

public class Door extends Tile {

    private int destinationRow;
    private int destinationColumn;
    private boolean isLocked;
    //TODO: Add a key system

    public Door(Window window, String name, Direction direction, int number, int positionX, int positionY, int destinationRow, int destinationColumn) {
        super(window, name, direction, number, positionX, positionY);
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        isLocked = true;
    }

    public void lock() {
        isLocked = true;
        //TODO: Set texture to the locked one
    }

    public void unlock() {
        isLocked = false;
        //TODO: Set texture to the unlocked one
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
}
