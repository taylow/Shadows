package edu.lancs.game;

public class Constants {
    /*Game Constants*/
    public static final String GAME_TITLE = "Dungeon Crawler";
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    public static final int GAME_FRAME_RATE = 60;
    public static final boolean GAME_FULLSCREEN = false;

    public static final int GAME_LEVEL_WIDTH = 10;
    public static final int GAME_LEVEL_HEIGHT = 10;

    /*Path Constants*/
    public static final String RESOURCE_PATH = "resources/";

    /*Debug Constants*/
    public static final boolean DEBUG_MESSAGES = true;
    public static final boolean DEBUG_ERRORS = true;

    /*Menu Constants*/
    public static final int MENU_BUTTON_WIDTH = 254;
    public static final int MENU_BUTTON_HEIGHT = 83;
    public static final int TITLE_BANNER_WIDTH = 600;
    public static final int TITLE_BANNER_HEIGHT = 200;

    /*Player Constants*/
    public static final int PLAYER_STARTING_X = 285;
    public static final int PLAYER_STARTING_Y = 285;
    public static final float PLAYER_BASE_MOVEMENT = 5;
    public static final int PLAYER_STARTING_HEALTH = 10; // (this/2 is the number of hearts on the HUD) FIXME: Odd numbers may cause issues

    /*Map Constants*/
    public static final int MAP_TILE_WIDTH = 116;
    public static final int MAP_TILE_HEIGHT = 116;

    /*HUD Constants*/
    public static final int HUD_HEART_DIMENSION = 30;
}
