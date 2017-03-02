package edu.lancs.game.scenes;

import edu.lancs.game.Constants;
import edu.lancs.game.Debug;
import edu.lancs.game.HighscoresUpdater;
import edu.lancs.game.Window;
import edu.lancs.game.entity.*;
import edu.lancs.game.generation.*;
import edu.lancs.game.gui.HUD;
import edu.lancs.game.gui.Lighting;
import edu.lancs.game.gui.MiniMap;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.window.event.Event;

import java.util.Random;

import static edu.lancs.game.Constants.*;

public class GameScene extends Scene {
    private String username;

    private HUD hud;
    private MiniMap miniMap;
    private Lighting lighting;
    private Player player;
    private Level[][] levels;
    private Level currentLevel;
    private Level bossLevel;
    private Chest chest;
    private int level;
    private GameMode gameMode;

    public GameScene(Window window, String username, GameMode gameMode) {
        super(window);
        setTitle("Do Not Die");
        getWindow().getResourceManager().stopSound("menu_music");
        setMusic("game_music");
        setBackgroundColour(Color.BLACK);

        this.username = username;
        this.level = 1;
        this.gameMode = gameMode;

        player = new Player(getWindow()); // creates a Player and passes the Window into it
        hud = new HUD(getWindow(), player); // creates a HUD and passes Window and the player just created into it for variables

        Random random = new Random();

        levels = new Level[GAME_LEVEL_WIDTH][GAME_LEVEL_HEIGHT]; // creates a 2D array to fill with levels

        generateRooms();
        int startingLevelColumn = random.nextInt(GAME_LEVEL_WIDTH);
        int startingLevelRow = random.nextInt(GAME_LEVEL_HEIGHT);

        currentLevel = levels[startingLevelRow][startingLevelColumn]; // randomises the starting level

        currentLevel.discoverLevel();
        currentLevel.getEnemies().clear();

        miniMap = new MiniMap(getWindow(), levels); // creates the minimap
        lighting = new Lighting(getWindow(), player); // created the lighting instance

        Debug.print("Starting game on " + gameMode + " mode");
        Debug.print("Current level " + currentLevel.getLevelColumnPosition() + " - " + currentLevel.getLevelRowPosition());
        Debug.print("Boss level " + bossLevel.getLevelColumnPosition() + " - " + bossLevel.getLevelRowPosition());
    }

    /***
     * Draws and updates the game objects such as decorations, player, HUD, Tiles, etc.
     *
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        draw:
        player.update();
        // draws the level tiles
        boolean teleported = false;
        for (Tile[] tileRow : currentLevel.getTiles()) {
            for (Tile tile : tileRow) {
                window.draw(tile);
                if(!teleported) // quick fix for the room glitch
                    teleported = tileCollision(tile);
            }
        }


        // FIXME: View works, but maybe should really be done another way
        View view = (View) getWindow().getView();
        view.setCenter(player.getPosition());
        getWindow().setView(view);

        for(Chest chest : currentLevel.getChests()) {
            window.draw(chest);
            if(player.getGlobalBounds().intersection(new FloatRect(chest.getPosition().x, chest.getPosition().y, 10, 10)) != null) {
                if(!chest.isOpen())
                    currentLevel.addPickup(chest.open());
            }
        }

        for(int pickupId = 0; pickupId < currentLevel.getPickups().size(); pickupId++) {
            Pickup pickup = currentLevel.getPickups().get(pickupId);
            window.draw(pickup);
            if(player.getGlobalBounds().intersection(new FloatRect(pickup.getPosition().x, pickup.getPosition().y, 10, 10)) != null) {
                pickup.givePerk(player);
                if(pickup.isUsed()) {
                    currentLevel.getPickups().remove(pickup);
                    HUD h = new HUD(this.getWindow(), this.player);
                    h.getTexts();

                    pickupId--;

                }
            }
        }

        // draws the enemies
        //FIXME: This should be done every time an enemy dies, not every cycle.
        if(currentLevel.getEnemies().size() == 0) {
            if(currentLevel.isBossLevel())
                currentLevel.getLevelUpDoor().unlock();
            else
                for(Door door: currentLevel.getDoors()) {
                    if (!door.requiresKey()) {
                        door.unlock();
                    }
                }
        }

        //enemies.stream().filter(Actor::isDead).forEach(enemy -> enemies.remove(enemy)); // remove any dead enemies FIXME: Could be done more efficiently
        for(int enemyId = 0; enemyId < currentLevel.getEnemies().size(); enemyId++) {
            Enemy enemy = currentLevel.getEnemies().get(enemyId);
            if(!enemy.isDead()) {
                enemy.setTargetActor(player);
                enemy.update();

                window.draw(enemy);


                // enemy hit detection FIXME: Poor attempt, I know. It works and meets criteria
                if (player.getState() == Actor.State.ATTACKING && player.getFrame() == ACTOR_ATTACK_FRAME) {
                    if (player.canAttackReach(enemy)) {
                        enemy.damage(player.getWeaponDamage());
                    }
                }

                // player hit detection FIXME: Poor attempt, I know. It works and meets criteria
                if (enemy.getState() == Actor.State.ATTACKING && enemy.getFrame() == ACTOR_ATTACK_FRAME) {
                    if (enemy.canAttackReach(player)) {
                        player.damage(enemy.getWeaponDamage());
                    }
                }

                if(player.getHealth() == 0)
                    gameOver();

            } else {
                player.addScore(ENEMY_DEFAULT_SCORE + enemy.getHearts() * 10);

                currentLevel.getEnemies().remove(enemyId);
                enemyId--;
            }
        }

        // draws the player
        window.draw(player);

        for(int projectileId = 0; projectileId < player.getProjectiles().size(); projectileId++) {
            Projectile projectile =  player.getProjectiles().get(projectileId);
            projectile.update();
            window.draw(projectile);
            for(Enemy enemy : currentLevel.getEnemies()) {
                if (projectile.getGlobalBounds().intersection(new FloatRect(enemy.getPosition().x, enemy.getPosition().y, 20, 30)) != null) {
                    enemy.damage(projectile.getDamage());
                    player.getProjectiles().remove(projectile);
                    projectileId--;
                    break;
                }
            }
            if(projectile.hasArrived()) {
                player.getProjectiles().remove(projectile); // remove projectile when it reaches the mouse position
                projectileId--;
            }
        }

        // draw the lighting
        lighting.generateLighting(100 + player.getBatteryLevel());
        lighting.getLighting().forEach(window::draw);

        // draws the HUD
        hud.update();
        hud.getDecorations().forEach(window::draw);
        hud.getHearts().forEach(window::draw);
        hud.getTexts().forEach(window::draw);

        // display the minimap if the control key is pressed
        if(getWindow().getInputHandler().isCtrlKeyPressed()) {
            // draw the minimap
            miniMap.updateMap();
            miniMap.getMapTiles().forEach(window::draw);
        }
    }

    /***
     * VERY VERY VERY bad collision and level teleportation FIXME: NEEDS TO BE LOOKED AT AND COMPLETELY REWRITTEN
     * @param tile
     */
    private boolean tileCollision(Tile tile) {
        //FIXME: Some basic collision detection, worst way possible, needs changing (DON'T USE instanceOf, this was a last resort test)
        boolean teleport = false; //FIXME: Quick fix, should be rewritten
        if (!(tile instanceof Floor)) {
            Tile.Direction direction = tile.getDirection();
            if (tile.getGlobalBounds().intersection(new FloatRect(player.getPosition().x, player.getPosition().y, 1, 1)) != null) {
                if(tile instanceof Door) {
                    teleport = useDoor((Door) tile);
                }
                switch (direction) {
                    case N:
                        if(teleport)
                            if(currentLevel.getDoor(Tile.Direction.S) != null) {
                                player.setPosition(currentLevel.getDoor(Tile.Direction.S).getPosition().x + MAP_TILE_WIDTH / 2,
                                        currentLevel.getDoor(Tile.Direction.S).getPosition().y - 100 + MAP_TILE_HEIGHT / 2);
                            } else {
                                player.setPosition((currentLevel.getWidth() / 2) * (MAP_TILE_WIDTH - 1),
                                        (currentLevel.getHeight() - 1) * (MAP_TILE_HEIGHT - 1));
                                System.out.println("TEST");
                            }
                        else
                            player.setCollidingUp(true);
                        break;
                    case E:
                        if(teleport)
                            player.setPosition(currentLevel.getDoor(Tile.Direction.W).getPosition().x + 100 + MAP_TILE_WIDTH / 2,
                                    currentLevel.getDoor(Tile.Direction.W).getPosition().y + MAP_TILE_HEIGHT / 2);
                        else
                            player.setCollidingRight(true);
                        break;
                    case S:
                        if(teleport)
                            player.setPosition(currentLevel.getDoor(Tile.Direction.N).getPosition().x + MAP_TILE_WIDTH / 2,
                                    currentLevel.getDoor(Tile.Direction.N).getPosition().y + 100 + MAP_TILE_HEIGHT / 2);
                        else
                            player.setCollidingDown(true);
                        break;
                    case W:
                        if(teleport)
                            player.setPosition(currentLevel.getDoor(Tile.Direction.E).getPosition().x - 100 + MAP_TILE_WIDTH / 2,
                                    currentLevel.getDoor(Tile.Direction.E).getPosition().y + MAP_TILE_HEIGHT / 2);
                        else
                            player.setCollidingLeft(true);
                        break;
                    case NW:
                        player.setCollidingUp(true);
                        player.setCollidingLeft(true);
                        break;
                    case NE:
                        player.setCollidingUp(true);
                        player.setCollidingRight(true);
                        break;
                    case SW:
                        player.setCollidingDown(true);
                        player.setCollidingLeft(true);
                        break;
                    case SE:
                        player.setCollidingDown(true);
                        player.setCollidingRight(true);
                        break;
                }
            }
        }
        return teleport;
    }

    public boolean useDoor(Door door) {
        int destinationColumn = door.getDestinationColumn();
        int destinationRow = door.getDestinationRow();

        // if the door is in bounds of the level array
        if(currentLevel.isBossLevel()) {
            if (!door.isLocked()) {
                if(gameMode != GameMode.IMPOSSIBLE && level == gameMode.LEVELS) {
                    gameComplete();
                } else {
                    level++;
                    Debug.print("Teleporting to room: " + destinationRow + ", " + destinationColumn + " Level: " + level);
                    generateRooms();
                    currentLevel.setCurrentLevel(false); // set the level loaded to not be the current level
                    currentLevel.unloadLevel();
                    currentLevel = levels[currentLevel.getLevelColumnPosition()][currentLevel.getLevelRowPosition()]; // change the level
                    currentLevel.discoverLevel(); // discover (minimap)
                    currentLevel.setCurrentLevel(true); // set the current level
                    player.resetCollision();
                    return true;
                }
            }
        } else if((destinationColumn >= 0 && destinationColumn < GAME_LEVEL_WIDTH) && (destinationRow >= 0 && destinationRow < GAME_LEVEL_HEIGHT)) {
            if (door.requiresKey()) {
                if (player.hasBossKey()) {
                    door.unlock();
                    player.setHasBossKey(false);
                }
            }
            if (!door.isLocked()) {
                Debug.print("Teleporting to room: " + destinationRow + ", " + destinationColumn + " Level: " + level);
                currentLevel.setCurrentLevel(false); // set the level loaded to not be the current level
                currentLevel.unloadLevel();
                currentLevel = levels[destinationRow][destinationColumn]; // change the level
                currentLevel.discoverLevel(); // discover (minimap)
                currentLevel.setCurrentLevel(true); // set the current level
                player.resetCollision();
                return true;
            }
        }
        return false;
    }

    /***
     * Window events happening while the GameScene is loaded are pushed to the InputHandler.
     *
     * @param event - Event that has been triggered
     */
    @Override
    public void executeEvent(Event event) {
        switch (event.type) {
            case KEY_PRESSED:
            case KEY_RELEASED:
                getWindow().getInputHandler().processInputs(event.asKeyEvent().key); // updates the InputHandler as to which key was pressed/released
                break;

            case JOYSTICK_MOVED:
                getWindow().getInputHandler().processInputs(event.asJoystickMoveEvent());
                break;

            case JOYSTICK_BUTTON_PRESSED:
            case JOYSTICK_BUTTON_RELEASED:
                getWindow().getInputHandler().processInputs(event.asJoystickButtonEvent());
                break;

            case MOUSE_BUTTON_PRESSED:
            case MOUSE_BUTTON_RELEASED:
                getWindow().getInputHandler().processInputs(event.asMouseButtonEvent());
                break;
        }
    }

    public void generateRooms() {
        Random random = new Random();
        for(int column = 0; column < GAME_LEVEL_WIDTH; column++)
            for(int row = 0; row < GAME_LEVEL_HEIGHT; row++)
                //FIXME: Make this a little less parameter heavy
                levels[column][row] = new Level(getWindow(), random.nextInt(ROOM_WIDTH_MAX + 1 - ROOM_WIDTH_MIN) + ROOM_WIDTH_MIN, random.nextInt(ROOM_HEIGHT_MAX + 1 - ROOM_HEIGHT_MIN) + ROOM_HEIGHT_MIN, random.nextInt(5), "green_stone", new Color(random.nextInt(192 + 1 - 64) + 64, random.nextInt(128 + 1 - 64) + 64, random.nextInt(64 + 1) + 10), column, row); // generates a level 7x5 with 0 complexity and using textures "green_stone"

        int bossLevelColumn = random.nextInt(GAME_LEVEL_WIDTH);
        int bossLevelRow = random.nextInt(GAME_LEVEL_HEIGHT);

        Debug.print("Boss room is at " + bossLevelColumn + " - " + bossLevelRow);

        bossLevel = levels[bossLevelRow][bossLevelColumn]; // randomises the boss level
        bossLevel.setBossLevel(true);
        bossLevel.generateBossRoom();

        if(bossLevelRow > 0) {
            levels[bossLevelRow - 1][bossLevelColumn].getEastDoor().setTexture(getWindow().getResourceManager().getTextures("green_stone_door_square_closed_E_1"));
            levels[bossLevelRow - 1][bossLevelColumn].getEastDoor().setRequiresKey(true);
        }
        if(bossLevelRow < GAME_LEVEL_WIDTH - 1) {
            levels[bossLevelRow + 1][bossLevelColumn].getWestDoor().setTexture(getWindow().getResourceManager().getTextures("green_stone_door_square_closed_W_1"));
            levels[bossLevelRow + 1][bossLevelColumn].getWestDoor().setRequiresKey(true);
        }
        if(bossLevelColumn > 0) {
            levels[bossLevelRow][bossLevelColumn - 1].getSouthDoor().setTexture(getWindow().getResourceManager().getTextures("green_stone_door_square_closed_S_1"));
            levels[bossLevelRow][bossLevelColumn - 1].getSouthDoor().setRequiresKey(true);
        }
        if(bossLevelColumn < GAME_LEVEL_HEIGHT - 1) {
            levels[bossLevelRow][bossLevelColumn + 1].getNorthDoor().setTexture(getWindow().getResourceManager().getTextures("green_stone_door_square_closed_N_1"));
            levels[bossLevelRow][bossLevelColumn + 1].getNorthDoor().setRequiresKey(true);
        }

        if(SHOW_SPECIAL_ROOMS)
            bossLevel.discoverLevel();

        int bossKeyColumn = random.nextInt(GAME_LEVEL_WIDTH);
        int bossKeyRow = random.nextInt(GAME_LEVEL_HEIGHT);

        // make sure it's not in the boss room
        while(bossKeyColumn == bossLevelColumn && bossKeyRow == bossLevelRow) {
            bossKeyColumn = random.nextInt(GAME_LEVEL_WIDTH);
            bossKeyRow = random.nextInt(GAME_LEVEL_HEIGHT);
        }

        int randomX = (random.nextInt(levels[bossKeyRow][bossKeyColumn].getWidth() - 2) + 1) * MAP_TILE_WIDTH + (MAP_TILE_WIDTH / 2);
        int randomY = (random.nextInt(levels[bossKeyRow][bossKeyColumn].getHeight() - 2) + 1) * MAP_TILE_HEIGHT + (MAP_TILE_HEIGHT / 2);
        levels[bossKeyRow][bossKeyColumn].getChests().add(new Chest(getWindow(), randomX, randomY, Pickup.Type.values().length - 1)); // put key in chest
        if(SHOW_SPECIAL_ROOMS)
            levels[bossKeyRow][bossKeyColumn].discoverLevel();
        Debug.print("Boss room key is at " + bossKeyColumn + " - " + bossKeyRow);
    }

    public void gameOver() {
        currentLevel.unloadLevel();
        if(player.getSound() != null)
            player.getSound().stop();

        HighscoresUpdater highscoresUpdater = new HighscoresUpdater(username, player.getScore(), player.getTimeAlive(), level, player.getKills());
        Thread highScoresThread = new Thread(highscoresUpdater);
        highScoresThread.start();

        GameOverScene gameOverScene = new GameOverScene(getWindow(), getWindow().getScene(0));
        int gameOverSceneIndex = getWindow().addScene(gameOverScene);
        gameOverScene.activate();
        getWindow().setCurrentScene(gameOverSceneIndex);
        getWindow().setView(new View(new FloatRect(0.0f, 0.0f, getWindow().getSize().x, getWindow().getSize().y)));
        this.deactivate();
    }

    public void gameComplete() {
        currentLevel.unloadLevel();
        if(player.getSound() != null)
            player.getSound().stop();

        this.deactivate();

        HighscoresUpdater highscoresUpdater = new HighscoresUpdater(username, player.getScore(), player.getTimeAlive(), level, player.getKills());
        Thread highScoresThread = new Thread(highscoresUpdater);
        highScoresThread.start();

        GameWinScene gameWinScene = new GameWinScene(getWindow(), getWindow().getScene(0));
        int gameWinrSceneIndex = getWindow().addScene(gameWinScene);
        gameWinScene.activate();
        getWindow().setCurrentScene(gameWinrSceneIndex);
        getWindow().setView(new View(new FloatRect(0.0f, 0.0f, getWindow().getSize().x, getWindow().getSize().y)));
    }

    public enum GameMode {
        EASY(EASY_LEVEL_COUNT), MEDIUM(MEDIUM_LEVEL_COUNT), HARD(HARD_LEVEL_COUNT), IMPOSSIBLE(0);

        public final int LEVELS;

        GameMode(int levels) {
            LEVELS = levels;
        }
    }
}
