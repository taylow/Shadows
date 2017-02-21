package edu.lancs.game.generation;

import edu.lancs.game.Window;

import static edu.lancs.game.generation.Tile.Direction.*;

public class Level {

    private int width;
    private int height;
    private int complexity;
    private String textureName;

    private Tile[][] tiles;

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
        generateTiles(textureName);
    }

    /***
     * Generates a rectangle room using a texture name by filling the tiles array with the correct time based on where they are.
     *
     * @param name - The name of the texture (e.g. green_stone)
     */
    public void generateTiles(String name) {
        // north side
        for(int column = 0; column < width; column++) {
            // first piece (NW corner)
            if(column == 0)
                tiles[0][column] = new Tile(getWindow(), textureName + "_wall", NW, 1, column, 0);

            // last piece (NE corner)
            else if(column == width - 1)
                tiles[0][column] = new Tile(getWindow(), textureName + "_wall", NE, 1, column, 0);

            // middle piece (N wall)
            else
                tiles[0][column] = new Tile(getWindow(), textureName + "_wall", N, 1, column, 0);
        }

        // middle section
        for(int column = 0; column < width; column++) {
            for(int row = 1; row < height - 1; row++) {
                // first piece (W wall)
                if(column == 0)
                    tiles[row][column] = new Tile(getWindow(), textureName + "_wall", W, 1, column, row);

                // last piece (E wall)
                else if(column + 1 == width)
                    tiles[row][column] = new Tile(getWindow(), textureName + "_wall", E, 1, column, row);

                // middle piece (floor tile)
                else
                    tiles[row][column] = new Tile(getWindow(), textureName + "_floor", NONE, 1, column, row);
            }
        }

        // south side
        for(int column = 0; column < width; column++) {
            // first piece (SW corner)
            if(column == 0)
                tiles[height - 1][column] = new Tile(getWindow(), textureName + "_wall", SW, 1, column, height - 1);

            // last piece (SE corner)
            else if(column + 1 == width)
                tiles[height - 1][column] = new Tile(getWindow(), textureName + "_wall", SE, 1, column, height - 1);

            // middle piece (S wall)
            else
                tiles[height - 1][column] = new Tile(getWindow(), textureName + "_wall", S, 1, column, height - 1);
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile[] testTile() {
        return tiles[0];
    }

    public Window getWindow() {
        return window;
    }
}
