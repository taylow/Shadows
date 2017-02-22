package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Chest;
import edu.lancs.game.entity.Enemy;
import edu.lancs.game.entity.Player;
import edu.lancs.game.generation.Floor;
import edu.lancs.game.generation.Level;
import edu.lancs.game.generation.Tile;
import edu.lancs.game.gui.HUD;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.window.event.Event;

import java.util.Random;

import static edu.lancs.game.Constants.GAME_LEVEL_HEIGHT;
import static edu.lancs.game.Constants.GAME_LEVEL_WIDTH;

public class GameScene extends Scene {

    private HUD hud;
    private Player player;
    private Enemy enemy;
    private Level[][] levels;
    private Level currentLevel;
    private Chest chest;

    public GameScene(Window window) {
        super(window);
        setTitle("Do Not Die");
        setMusic("game_music");
        setBackgroundColour(Color.BLACK);

        player = new Player(getWindow()); // creates a Player and passes the Window into it
        enemy = new Enemy(getWindow()); // creates a Player and passes the Window into it
        hud = new HUD(getWindow(), player); // creates a HUD and passes Window and the player just created into it for variables

        // currently only has one level TODO: add a 2D level array
        Random random = new Random();

        levels = new Level[GAME_LEVEL_WIDTH][GAME_LEVEL_HEIGHT];

        chest = new Chest(getWindow(), 200, 200);

        for(int column = 0; column < GAME_LEVEL_WIDTH; column++)
            for(int row = 0; row < GAME_LEVEL_HEIGHT; row++)
                levels[column][row] = new Level(getWindow(), random.nextInt(10) + 5, random.nextInt(10) + 5, 0, "green_stone"); // generates a level 7x5 with 0 complexity and using textures "green_stone"

        currentLevel = levels[0][0]; // TODO: Randomise where the player starts and finishes
    }

    /***
     * Draws and updates the game objects such as decorations, player, HUD, Tiles, etc.
     *
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        // draws the level tiles
        for (Tile[] tileRow : currentLevel.getTiles()) {
            for (Tile tile : tileRow) {
                window.draw(tile);

                //FIXME: Some basic collision detection, worst way possible, needs changing (DON'T USE instanceOf, this was a last resort test)
                if (!(tile instanceof Floor))
                    if (tile.getGlobalBounds().intersection(new FloatRect(player.getPosition().x, player.getPosition().y, 1, 1)) != null)
                        player.setColliding(true);
            }
        }

        Random random = new Random();
        //FIXME: This is to test the levels... Remove when done
        //currentLevel = levels[random.nextInt(5)][random.nextInt(5)];


        // FIXME: View works, but should really be done another way. Also, HUD doesn't draw to correct view
        View view = (View) getWindow().getDefaultView();
        view.setCenter(player.getPosition());
        //view.move(velocity);
        getWindow().setView(view);

        window.draw(chest);

        // draws the enemy //TODO: enemies should be stored in the level, so do that
        enemy.setTargetActor(player);
        enemy.update();
        window.draw(enemy);

        // draws the player
        player.update();
        window.draw(player);

        // draws the HUD
        hud.update();
        hud.getDecorations().forEach(window::draw);
        hud.getHearts().forEach(window::draw);
        hud.getTexts().forEach(window::draw);
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
        }
    }
}
