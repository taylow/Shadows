package edu.lancs.game.generation;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Enemy;
import org.jsfml.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

import static edu.lancs.game.Constants.GAME_LEVEL_WIDTH;
import static edu.lancs.game.Constants.MAP_TILE_HEIGHT;
import static edu.lancs.game.Constants.MAP_TILE_WIDTH;
import static edu.lancs.game.generation.Tile.Direction.*;

public class Level {

    private int width;
    private int height;
    private int complexity;
    private String textureName;
    private int levelColumnPosition;
    private int levelRowPosition;
    private Color levelColour;
    private boolean isDiscovered;
    private boolean isCurrentLevel;

    private Tile[][] tiles;
    private ArrayList<Enemy> enemies;

    private Door northDoor;
    private Door eastDoor;
    private Door southDoor;
    private Door westDoor;

    private ArrayList<Door> doors;

    private Window window;

    /***
     * Creates a level of a set width and height and generates enemies based on complexity.
     *
     * @param width - Width of the map in tiles
     * @param height - Height of the map in tiles
     * @param complexity - Complexity of the level (0 = no enemies, enemies scale with complexity)
     * @param textureName - The name of the texture using the prefix NAME_DIRECTION_NUMBER
     */
    public Level(Window window, int width, int height, int complexity, String textureName, Color levelColour, int levelColumnPosition, int levelRowPosition) {
        this.window = window;
        this.width = width;
        this.height = height;
        this.complexity = complexity;
        this.textureName = textureName;
        this.levelColumnPosition = levelColumnPosition;
        this.levelRowPosition = levelRowPosition;
        this.levelColour = levelColour;
        isDiscovered = false;
        isCurrentLevel = false;

        tiles = new Tile[height][width];
        enemies = new ArrayList<>();
        doors = new ArrayList<>();

        generateTiles(textureName);
        generateEnemies(complexity);
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
        //FIXME: For soem reason, when generating smaller room sizes, the doors teleport you to odd places. Maybe update player position before loading next room?
        // north side
        for (int column = 0; column < width; column++) {
            // first piece (NW corner)
            if (column == 0)
                tiles[0][column] = new Wall(getWindow(), textureName + "_wall", NW, 1, column, 0, levelColour);

                // last piece (NE corner)
            else if (column == width - 1)
                tiles[0][column] = new Wall(getWindow(), textureName + "_wall", NE, 1, column, 0, levelColour);

                // if it's the middle and is not the top room, add a door
            else if (column == (width - 1) / 2 && levelRowPosition != 0) {
                northDoor = new Door(getWindow(), textureName + "_door_round", N, 1, column, 0, levelRowPosition - 1, levelColumnPosition, true, levelColour);
                doors.add(northDoor);
                tiles[0][column] = northDoor;
            }

            // middle piece (N wall)
            else
                tiles[0][column] = new Wall(getWindow(), textureName + "_wall", N, random.nextInt(5) + 1, column, 0, levelColour);
        }

        // middle section
        for (int column = 0; column < width; column++) {
            for (int row = 1; row < height - 1; row++) {
                // first piece (W wall)
                if (column == 0)
                    if (row == (height - 1) / 2 && levelColumnPosition != 0) {
                        westDoor = new Door(getWindow(), textureName + "_door_round", W, 1, column, row, levelRowPosition, levelColumnPosition - 1, true, levelColour);
                        doors.add(westDoor);
                        tiles[row][column] = westDoor;
                    } else {
                        tiles[row][column] = new Wall(getWindow(), textureName + "_wall", W, random.nextInt(2) + 1, column, row, levelColour);
                    }

                    // last piece (E wall)
                else if (column + 1 == width)
                    if (row == (height - 1) / 2 && levelColumnPosition != GAME_LEVEL_WIDTH - 1) {
                        eastDoor = new Door(getWindow(), textureName + "_door_round", E, 1, column, row, levelRowPosition, levelColumnPosition + 1, true, levelColour);
                        doors.add(eastDoor);
                        tiles[row][column] = eastDoor;
                    } else {
                        tiles[row][column] = new Wall(getWindow(), textureName + "_wall", E, random.nextInt(2) + 1, column, row, levelColour);
                    }

                    // middle piece (floor tile)
                else
                    tiles[row][column] = new Floor(getWindow(), textureName + "_floor", NONE, random.nextInt(7) + 1, column, row, levelColour);
            }
        }

        // south side
        for (int column = 0; column < width; column++) {
            // first piece (SW corner)
            if (column == 0)
                tiles[height - 1][column] = new Wall(getWindow(), textureName + "_wall", SW, 1, column, height - 1, levelColour);

                // last piece (SE corner)
            else if (column + 1 == width)
                tiles[height - 1][column] = new Wall(getWindow(), textureName + "_wall", SE, 1, column, height - 1, levelColour);

                // if it's the middle, add a door
            else if (column == (width - 1) / 2 && levelRowPosition != GAME_LEVEL_WIDTH - 1) {
                southDoor = new Door(getWindow(), textureName + "_door_round", S, 1, column, height - 1, levelRowPosition + 1, levelColumnPosition, true, levelColour);
                doors.add(southDoor);
                tiles[height - 1][column] = southDoor;

                // middle piece (S wall)
            } else {
                tiles[height - 1][column] = new Wall(getWindow(), textureName + "_wall", S, random.nextInt(2) + 1, column, height - 1, levelColour);
            }
        }
    }

    /***
     * Populates the ArrayList of Enemies with a random amount of Enemies and types for a given room.
     */
    public void generateEnemies(int complexity) {
        //TODO: This just basically generates a room with complexity x amount of Enemies. Needs writing so that it is balanced.
        Random random = new Random();
        for(int enemyIndex = 0; enemyIndex < complexity; enemyIndex++) {
            int randomX = (random.nextInt(width - 2) + 1) * MAP_TILE_WIDTH + (MAP_TILE_WIDTH / 2);
            int randomY = (random.nextInt(height - 2) + 1) * MAP_TILE_HEIGHT + (MAP_TILE_HEIGHT / 2);

            int randomHealth = random.nextInt(4) + 2;
            enemies.add(new Enemy(getWindow(), randomX, randomY, randomHealth));
        }
    }

    public void unloadLevel() {
        for(Enemy enemy : enemies)
            enemy.setTargetActor(null);
    }

    /***
     * Returns the whole level in a 2D array of Tiles (sprites) that can be drawn to a Scene.
     *
     * @return - 2D array of Tiles that makes up the level
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    public Window getWindow() {
        return window;
    }

    public int getLevelColumnPosition() {
        return levelColumnPosition;
    }

    public int getLevelRowPosition() {
        return levelRowPosition;
    }

    public Color getLevelColour() {
        return levelColour;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void discoverLevel() {
        isDiscovered = true;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public boolean isCurrentLevel() {
        return isCurrentLevel;
    }

    public void setCurrentLevel(boolean currentLevel) {
        isCurrentLevel = currentLevel;
    }

    /***
     * Returns a door in the room at the direction passed in as a parameter.
     *
     * @param direction - The location of the door in the room
     * @return - The door at said direction
     */
    public Door getDoor(Tile.Direction direction) {
        switch (direction) {
            case N:
                return northDoor;
            case E:
                return eastDoor;
            case S:
                return southDoor;
            case W:
                return westDoor;
            default:
                return null;
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }
}
