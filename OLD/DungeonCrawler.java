package edu.lancs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import static edu.lancs.game.Constants.*;

public class DungeonCrawler {
    private static RenderWindow window;
    private static ResourceManager resourceManager;
    private static Clock clock = new Clock();

    public DungeonCrawler() {
        window = new RenderWindow(new VideoMode(GAME_WIDTH, GAME_HEIGHT), TITLE);
        resourceManager = new ResourceManager();

        InputHandler inputHandler = new InputHandler(window);

        while (window.isOpen()) {
            processEvents();
            InputHandler.processInputs();
            tick(clock.restart());
            draw();
        }
    }

    public static void processInputs() {

    }

    public static void processEvents() {
        for (Event event : window.pollEvents()) {
            switch(event.type) {
                case CLOSED:
                    window.close();
                    break;
            }
        }
    }

    public static void tick(Time t) {

    }

    public static void draw() {
        window.clear(Color.BLACK);

        window.display();
    }

    public static void main(String[] args) {
        new DungeonCrawler();
    }
}
