package edu.lancs.game;

import edu.lancs.game.scenes.MenuScene;

import java.util.Random;

import static edu.lancs.game.Constants.*;

public class Main {
    public static void main(String[] args) {
        //FIXME: This whole file could be deleted, technically. It's only to create the Window, but this could be done in Window.
        SplashScreen splashScreen = new SplashScreen();
        Window window = new Window(GAME_WIDTH, GAME_HEIGHT, GAME_TITLE, GAME_FULLSCREEN, GAME_FRAME_RATE); // creates a Window with variables in Constants
        splashScreen.dispose();

        // create a MenuScene and set it as the current scene (this should always be the first scene)
        // FIXME: make this scene display first by default
        MenuScene menu = new MenuScene(window);
        int menuSceneIndex = window.addScene(menu);
        window.setCurrentScene(menuSceneIndex);
        window.drawActiveScene();
    }
}
