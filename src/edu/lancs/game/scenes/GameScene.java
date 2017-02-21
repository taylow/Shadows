package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import edu.lancs.game.generation.Level;
import edu.lancs.game.generation.Tile;
import edu.lancs.game.gui.Decoration;
import edu.lancs.game.gui.HUD;
import org.jsfml.system.Clock;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;

public class GameScene extends Scene {

    public Clock tick;
    private HUD hud;
    private Player player;
    private Level level;

    private ArrayList<Decoration> decorations;

    public GameScene(Window window) {
        super(window);
        setMusic("game_music");
        decorations = new ArrayList<>();
        player = new Player(getWindow(), "idle_knight");
        hud = new HUD(getWindow(), player);

        // currently only has one level TODO: add a 2D level array
        level = new Level(getWindow(), 7, 5, 0, "green_stone");

        // test floor, will be removed.
        decorations.add(new Decoration(getWindow(), "menu_wood_background", 0, 0, getWindow().getWidth(), getWindow().getHeight()));
    }

    @Override
    public void draw(Window window) {
        decorations.forEach(window::draw);

        // draws the level tiles
        for(Tile[] tileRow : level.getTiles()) {
            for(Tile tile : tileRow) {
                window.draw(tile);
            }
        }

        // draws the player
        player.update();
        window.draw(player);

        // draws the HUD
        hud.update();
        hud.getHearts().forEach(window::draw);
        window.draw(hud.getScoreText());
        //hud.getTexts().forEach(window::draw);
    }

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
