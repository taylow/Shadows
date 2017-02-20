package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import edu.lancs.game.gui.HUD;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Clock;
import org.jsfml.window.event.Event;

public class GameScene extends Scene {

    public Clock tick;
    private HUD hud;
    private Player player;

    public GameScene(Window window) {
        super(window);
        setMusic("game_music");
        hud = new HUD(getWindow());
        player = new Player(getWindow(), "Armature-Idle_00");
    }

    @Override
    public void draw(Window window) {
        player.update();
        window.draw(player);
        //hud.getTexts().forEach(window::draw);
    }

    @Override
    public void executeEvent(Event event) {
    }
}
