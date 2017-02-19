package edu.lancs.game;

import edu.lancs.game.scenes.MenuScene;

import static edu.lancs.game.Constants.*;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(GAME_WIDTH, GAME_HEIGHT, GAME_TITLE, GAME_FULLSCREEN, GAME_FRAME_RATE);

        MenuScene menu = new MenuScene(window);
        int menuSceneIndex = window.addScene(menu);

        window.setCurrentScene(menuSceneIndex);
        window.drawActiveScene();
    }
}
