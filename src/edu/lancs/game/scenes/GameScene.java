package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import edu.lancs.game.generation.Floor;
import edu.lancs.game.generation.Level;
import edu.lancs.game.generation.Tile;
import edu.lancs.game.gui.HUD;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.window.event.Event;

public class GameScene extends Scene {

    private HUD hud;
    private Player player;
    private Level level;

    public GameScene(Window window) {
        super(window);
        setTitle("Do Not Die");
        setMusic("game_music");
        setBackgroundColour(Color.BLACK);

        player = new Player(getWindow()); // creates a Player and passes the Window into it
        hud = new HUD(getWindow(), player); // creates a HUD and passes Window and the player just created into it for variables

        // currently only has one level TODO: add a 2D level array
        level = new Level(getWindow(), 7, 5, 0, "green_stone"); // generates a level 7x5 with 0 complexity and using textures "green_stone"
    }

    /***
     * Draws and updates the game objects such as decorations, player, HUD, Tiles, etc.
     *
     * @param window - Window that the objects will be drawn to
     */
    @Override
    public void draw(Window window) {
        // draws the level tiles
        for (Tile[] tileRow : level.getTiles()) {
            for (Tile tile : tileRow) {
                window.draw(tile);

                //FIXME: Some basic collision detection, worst way possible, needs changing (DON'T USE instanceOf, this was a last resort test)
                if(!(tile instanceof Floor))
                    if(tile.getGlobalBounds().intersection(new FloatRect(player.getPosition().x, player.getPosition().y, 1, 1)) != null)
                        player.setColliding(true);
            }
        }


        /*// FIXME: View works, but should really be done another way. Also, HUD doesn't draw to correct view
        View view = (View) getWindow().getDefaultView();
        view.setCenter(player.getPosition());
        //view.move(velocity);
        getWindow().setView(view);*/

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
