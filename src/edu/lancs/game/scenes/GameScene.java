package edu.lancs.game.scenes;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import org.jsfml.system.Clock;
import org.jsfml.window.event.Event;

public class GameScene extends Scene {

    public Clock tick;

    private Player player;

    public GameScene(Window window) {
        super(window);
        setMusic("game_music");
        player = new Player(getWindow(), "Armature-Idle_00");
    }

    @Override
    public void draw(Window window) {
        window.draw(player);
    }

    @Override
    public void executeEvent(Event event) {
        switch (event.type) {
            case KEY_PRESSED:
                player.moveRight();
                break;
        }
    }
}
