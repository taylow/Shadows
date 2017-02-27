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

    /*Actor Constants*/
    public static final int ACTOR_ATTACK_FRAME = 20; // frame where the damage is dealt

    /*Player Constants*/
    public static final int PLAYER_WEAPON_DAMAGE = 2;
    public static final int PLAYER_STARTING_X = 285;
    public static final int PLAYER_STARTING_Y = 285;
    public static final float PLAYER_BASE_MOVEMENT = 5;
    public static final int PLAYER_STARTING_HEALTH = 10; // (this/2 is the number of hearts on the HUD) FIXME: Odd numbers may cause issues
    public static final float PLAYER_SCALE_WIDTH = 1.0f;
    public static final float PLAYER_SCALE_HEIGHT = 1.0f;

    /*Enemy Constants*/
    public static final int ENEMY_WEAPON_DAMAGE = 1;
    public static final int ENEMY_DEFAULT_SCORE = 100;
    public static final float ENEMY_BASE_MOVEMENT = 1;
    public static final int ENEMY_STARTING_HEALTH_MIN = 1;
    public static final int ENEMY_STARTING_HEALTH_MAX = 5;

    /*Map Constants*/
    public static final int MAP_TILE_WIDTH = 114;
    public static final int MAP_TILE_HEIGHT = 114;
    public static final float MAP_TILE_SCALE = 2f;

    /*HUD Constants*/
    public static final int HUD_HEART_DIMENSION = 30;

    /*Lighting Constants*/
    public static final int LIGHTING_TILE_WIDTH = 40;
    public static final int LIGHTING_TILE_HEIGHT = 40;

    /*Room Constants*/
    public static final int ROOM_WIDTH_MIN = 5;
    public static final int ROOM_WIDTH_MAX = 10;
    public static final int ROOM_HEIGHT_MIN = 5;
    public static final int ROOM_HEIGHT_MAX = 10;

    /*Joystick Mapping Constants (look in InputHandler to find values)*/
    public static final int JOYSTICK_DEADZONE_X = 25;
    public static final int JOYSTICK_DEADZONE_Y = 25;
    public static final int ATTACK_BUTTON = 0;
    public static final int MINIMAP_BUTTON = 1;

    /*Highscores Constants*/
    public static final String HIGHSCORES_USER_AGENT = "Mozilla/5.0";
    public static final String HIGHSCORES_URL = "https://protaytoe.uk/highscores/post.php?name=X&score=Y&time=Z";
}
