package edu.lancs.game.generation;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Actor;
import edu.lancs.game.entity.Chest;
import edu.lancs.game.entity.Enemy;
import edu.lancs.game.entity.Pickup;
import org.jsfml.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

import static edu.lancs.game.Constants.*;
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
    private boolean isBossLevel;

    private Tile[][] tiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Pickup> pickups;
    private ArrayList<Chest> chests;

    private Door northDoor;
    private Door eastDoor;
    private Door southDoor;
    private Door westDoor;
    private Door levelUpDoor;

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
        isBossLevel = false;

        tiles = new Tile[height][width];
        enemies = new ArrayList<>();
        doors = new ArrayList<>();
        pickups = new ArrayList<>();
        chests = new ArrayList<>();

        generateTiles();
        generateEnemies(complexity);
        generateChests();
    }

    /***
     * Generates a rectangle room using a texture name by filling the tiles array with the correct time based on where they are.
     * TODO: Need to add more randomisation.
     * TODO: This functionality could be passed into the Tile/Door itself to prevent this much code, but this works perfectly fine.
     */
    public void generateTiles() {
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

            int randomHealth = random.nextInt(ENEMY_STARTING_HEALTH_MAX + 1 - ENEMY_STARTING_HEALTH_MIN) + ENEMY_STARTING_HEALTH_MIN;
            enemies.add(new Enemy(getWindow(), randomX, randomY, randomHealth, new Color(10, random.nextInt(128 + 1 - 64) + random.nextInt(64), random.nextInt(10))));
        }
    }

    /***
     * Populates the ArrayList of Chests with a random amount of Chests and types for a given room.
     */
    public void generateChests() {
        //TODO: Balance
        Random random = new Random();
        for(int chestIndex = 0; chestIndex < complexity; chestIndex++) {
            int randomX = (random.nextInt(width - 2) + 1) * MAP_TILE_WIDTH + (MAP_TILE_WIDTH / 2);
            int randomY = (random.nextInt(height - 2) + 1) * MAP_TILE_HEIGHT + (MAP_TILE_HEIGHT / 2);

            chests.add(new Chest(getWindow(), randomX, randomY));
        }
    }

    public void unloadLevel() {
        for(Enemy enemy : enemies) {
            enemy.setTargetActor(null);
            enemy.setState(Actor.State.IDLE); // stops audio
        }
    }

    public void generateBossRoom() {
        Random random = new Random();
        width = GAME_LEVEL_WIDTH;
        height = GAME_LEVEL_HEIGHT;
        tiles = new Tile[height][width];
        levelColour = new Color(random.nextInt(192 + 1 - 64) + 64, random.nextInt(64 + 1) + 10, random.nextInt(64 + 1) + 10);
        generateTiles();
        chests.clear();

        enemies.clear();
        enemies.add(new Enemy(getWindow(), GAME_LEVEL_WIDTH / 2 * 114, GAME_LEVEL_HEIGHT / 2 * 114, 100, Color.RED));

        for(Door door : doors) {
            door.setTexture(getWindow().getResourceManager().getTextures(textureName + "_door_round_closed_" + door.getDirection() + "_3"));
        }

        levelUpDoor = new Door(getWindow(), textureName + "_door_square", N, 1, 2, 0, levelRowPosition, levelColumnPosition, true, levelColour);
        tiles[0][2] = levelUpDoor;

        //TODO: Spawn some pickups
        /*for(int x = 0; x < 10; x++)
            pickups.add(new Pickup(getWindow(), 114 + 114 * x, 114, 0));*/
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

    public void setBossLevel(boolean bossLevel) {
        isBossLevel = bossLevel;
    }

    public boolean isBossLevel() {
        return isBossLevel;
    }

    public ArrayList<Pickup> getPickups() {
        return pickups;
    }

    public void addPickup(Pickup pickup) {
        pickups.add(pickup);
    }

    public ArrayList<Chest> getChests() {
        return chests;
    }

    public Door getLevelUpDoor() {
        return levelUpDoor;
    }

    public Door getNorthDoor() {
        return northDoor;
    }

    public Door getEastDoor() {
        return eastDoor;
    }

    public Door getSouthDoor() {
        return southDoor;
    }

    public Door getWestDoor() {
        return westDoor;
    }
}
