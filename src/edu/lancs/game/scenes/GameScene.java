package edu.lancs.game.scenes;

import edu.lancs.game.Debug;
import edu.lancs.game.HighscoresUpdater;
import edu.lancs.game.Window;
import edu.lancs.game.entity.Actor;
import edu.lancs.game.entity.Chest;
import edu.lancs.game.entity.Enemy;
import edu.lancs.game.entity.Player;
import edu.lancs.game.generation.*;
import edu.lancs.game.gui.HUD;
import edu.lancs.game.gui.Lighting;
import edu.lancs.game.gui.MiniMap;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.window.event.Event;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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

    public GameScene(Window window, String username) {
        super(window);
        setTitle("Do Not Die");
        setMusic("game_music");
        setBackgroundColour(Color.BLACK);

        this.username = username;

        player = new Player(getWindow()); // creates a Player and passes the Window into it
        hud = new HUD(getWindow(), player); // creates a HUD and passes Window and the player just created into it for variables

        // currently only has one level TODO: add a 2D level array
        Random random = new Random();

        levels = new Level[GAME_LEVEL_WIDTH][GAME_LEVEL_HEIGHT]; // creates a 2D array to fill with levels

        chest = new Chest(getWindow(), 200, 200);

        for(int column = 0; column < GAME_LEVEL_WIDTH; column++)
            for(int row = 0; row < GAME_LEVEL_HEIGHT; row++)
                //FIXME: Make this a little less parameter heavy
                levels[column][row] = new Level(getWindow(), random.nextInt(ROOM_WIDTH_MAX + 1 - ROOM_WIDTH_MIN) + ROOM_WIDTH_MIN, random.nextInt(ROOM_HEIGHT_MAX + 1 - ROOM_HEIGHT_MIN) + ROOM_HEIGHT_MIN, random.nextInt(5), "green_stone", new Color(random.nextInt(192 + 1 - 64) + 64, random.nextInt(128 + 1 - 64) + 64, random.nextInt(64 + 1) + 10), column, row); // generates a level 7x5 with 0 complexity and using textures "green_stone"

        currentLevel = levels[random.nextInt(GAME_LEVEL_WIDTH)][random.nextInt(GAME_LEVEL_HEIGHT)]; // randomises the starting level
        currentLevel.discoverLevel();
        bossLevel = levels[random.nextInt(GAME_LEVEL_WIDTH)][random.nextInt(GAME_LEVEL_HEIGHT)]; // randomises the boss level

        miniMap = new MiniMap(getWindow(), levels); // creates the minimap
        lighting = new Lighting(getWindow(), player); // created the lighting instance
    }

    /***
     * Draws and updates the game objects such as decorations, player, HUD, Tiles, etc.
     *
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        player.update();
        // draws the level tiles
        for (Tile[] tileRow : currentLevel.getTiles()) {
            for (Tile tile : tileRow) {
                window.draw(tile);
                tileCollision(tile);
            }
        }


        // FIXME: View works, but maybe should really be done another way
        View view = (View) getWindow().getView();
        view.setCenter(player.getPosition());
        getWindow().setView(view);

        window.draw(chest); //FIXME: Just a text chest, remove once chest ransomisation is added

        // draws the enemies
        //FIXME: This should be done every time an enemy dies, not every cycle.
        if(currentLevel.getEnemies().size() == 0) {
            currentLevel.getDoors().forEach(Door::unlock);
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
                        System.out.println("Enemy health = " + enemy.getHealth());
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
                currentLevel.getEnemies().remove(enemyId);
                enemyId--;
            }
        }

        // draws the player
        window.draw(player);

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
    private void tileCollision(Tile tile) {
        //FIXME: Some basic collision detection, worst way possible, needs changing (DON'T USE instanceOf, this was a last resort test)
        if (!(tile instanceof Floor)) {
            boolean teleport = false; //FIXME: Quick fix, should be rewritten
            Tile.Direction direction = tile.getDirection();
            if (tile.getGlobalBounds().intersection(new FloatRect(player.getPosition().x, player.getPosition().y, 1, 1)) != null) {
                if(tile instanceof Door) {
                    teleport = useDoor((Door) tile);
                }
                switch (direction) {
                    case N:
                        if(teleport)
                            player.setPosition(currentLevel.getDoor(Tile.Direction.S).getPosition().x + MAP_TILE_WIDTH / 2,
                                    currentLevel.getDoor(Tile.Direction.S).getPosition().y - 100 + MAP_TILE_HEIGHT / 2);
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
    }

    public boolean useDoor(Door door) {
        int destinationColumn = door.getDestinationColumn();
        int destinationRow = door.getDestinationRow();

        // if the door is in bounds of the level array
        if((destinationColumn >= 0 && destinationColumn < GAME_LEVEL_WIDTH) && (destinationRow >= 0 && destinationRow < GAME_LEVEL_HEIGHT)) {
            if (!door.isLocked()) {
                Debug.print("Teleporting to room: " + destinationRow + ", " + destinationColumn);
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
        }
    }

    public void gameOver() {
        HighscoresUpdater highscoresUpdater = new HighscoresUpdater(username, player.getScore(), 1000);
        Thread highScoresThread = new Thread(highscoresUpdater);
        highScoresThread.start();

        GameOverScene gameOverScene = new GameOverScene(getWindow(), getWindow().getScene(0));
        int gameOverSceneIndex = getWindow().addScene(gameOverScene);
        gameOverScene.activate();
        getWindow().setCurrentScene(gameOverSceneIndex);
        getWindow().setView(new View(new FloatRect(0.0f, 0.0f, getWindow().getSize().x, getWindow().getSize().y)));
        this.deactivate();
    }
}
