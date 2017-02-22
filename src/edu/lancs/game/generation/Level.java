package edu.lancs.game.generation;

import edu.lancs.game.Window;

import java.util.ArrayList;
import java.util.Random;

import static edu.lancs.game.generation.Tile.Direction.*;

public class Level {

    private int width;
    private int height;
    private int complexity;
    private String textureName;

    private Tile[][] tiles;

    private ArrayList<Door> doors; // 0 = N, 1 = E, 2 = S, 3 = W

    private Window window;

    /***
     * Creates a level of a set width and height and generates enemies based on complexity.
     *
     * @param width - Width of the map in tiles
     * @param height - Height of the map in tiles
     * @param complexity - Complexity of the level (0 = no enemies, enemies scale with complexity)
     * @param textureName - The name of the texture using the prefix NAME_DIRECTION_NUMBER
     */
    public Level(Window window, int width, int height, int complexity, String textureName) {
        this.window = window;
        this.width = width;
        this.height = height;
        this.complexity = complexity;
        this.textureName = textureName;
        tiles = new Tile[height][width];
        doors = new ArrayList<>();
        generateTiles(textureName);
    }

    /***
     * Generates a rectangle room using a texture name by filling the tiles array with the correct time based on where they are.
     * TODO: Need to add more randomisation.
     * TODO: This functionality could be passed into the Tile/Door itself to prevent this much code, but this works perfectly fine.
     *
     * @param name - The name of the texture (e.g. green_stone)
     */
    public void generateTiles(String name) {
        Random random = new Random();

        // north side
        for (int column = 0; column < width; column++) {
            // first piece (NW corner)
            if (column == 0)
                tiles[0][column] = new Wall(getWindow(), textureName + "_wall", NW, 1, column, 0);

                // last piece (NE corner)
            else if (column == width - 1)
                tiles[0][column] = new Wall(getWindow(), textureName + "_wall", NE, 1, column, 0);

                // if it's the middle, add a door
            else if (column == (width - 1) / 2) {
                Door door = new Door(getWindow(), textureName + "_door_round", N, 1, column, 0, 0, 0, false);
                tiles[0][column] = door;
                doors.add(door);
            }

            // middle piece (N wall)
            else
                tiles[0][column] = new Wall(getWindow(), textureName + "_wall", N, random.nextInt(5) + 1, column, 0);
        }

        // middle section
        for (int column = 0; column < width; column++) {
            for (int row = 1; row < height - 1; row++) {
                // first piece (W wall)
                if (column == 0)
                    if (row == (height - 1) / 2)
                        tiles[row][column] = new Door(getWindow(), textureName + "_door_round", W, 1, column, row, 0, 0, true);
                    else
                        tiles[row][column] = new Wall(getWindow(), textureName + "_wall", W, random.nextInt(2) + 1, column, row);

                    // last piece (E wall)
                else if (column + 1 == width)
                    if (row == (height - 1) / 2)
                        tiles[row][column] = new Door(getWindow(), textureName + "_door_round", E, 1, column, row, 0, 0, true);
                    else
                        tiles[row][column] = new Wall(getWindow(), textureName + "_wall", E, random.nextInt(2) + 1, column, row);

                    // middle piece (floor tile)
                else
                    tiles[row][column] = new Floor(getWindow(), textureName + "_floor", NONE, random.nextInt(7) + 1, column, row);
            }
        }

        // south side
        for (int column = 0; column < width; column++) {
            // first piece (SW corner)
            if (column == 0)
                tiles[height - 1][column] = new Wall(getWindow(), textureName + "_wall", SW, 1, column, height - 1);

                // last piece (SE corner)
            else if (column + 1 == width)
                tiles[height - 1][column] = new Wall(getWindow(), textureName + "_wall", SE, 1, column, height - 1);

                // if it's the middle, add a door
            else if (column == (width - 1) / 2)
                tiles[height - 1][column] = new Door(getWindow(), textureName + "_door_round", S, 1, column, height - 1, 0, 0, true);

                // middle piece (S wall)
            else
                tiles[height - 1][column] = new Wall(getWindow(), textureName + "_wall", S, random.nextInt(2) + 1, column, height - 1);
        }
    }

    /***
     * Returns the whole level in a 2D array of Tiles (sprites) that can be drawn to a Scene.
     *
     * @return - 2D array of Tiles that makes up the level
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /***
     * Returns an ArrayList of Doors in the room.
     *
     * @return - Doors in the room
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }

    public Window getWindow() {
        return window;
    }
}
