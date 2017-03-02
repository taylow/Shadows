package edu.lancs.game;

public class Constants {
    /*Game Constants*/
    public static final String GAME_TITLE = "Shadows";
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    public static final int GAME_FRAME_RATE = 60;
    public static final boolean GAME_FULLSCREEN = false;

    public static final int GAME_LEVEL_WIDTH = 10;
    public static final int GAME_LEVEL_HEIGHT = 10;

    public static final int EASY_LEVEL_COUNT = 1;
    public static final int MEDIUM_LEVEL_COUNT = 5;
    public static final int HARD_LEVEL_COUNT = 10;

    public static final int RANDOM_SOUNDS_TIME = 60000; // plays a random sound every minute

    /*Path Constants*/
    public static final String RESOURCE_PATH = "resources/";

    /*Debug Constants*/
    public static final boolean DEBUG_MESSAGES = true;
    public static final boolean DEBUG_ERRORS = true;
    public static final boolean SHOW_SPECIAL_ROOMS = true;

    /*Menu Constants*/
    public static final int MENU_BUTTON_WIDTH = 254;
    public static final int MENU_BUTTON_HEIGHT = 83;
    public static final int TITLE_BANNER_WIDTH = 551;
    public static final int TITLE_BANNER_HEIGHT = 92;

    /*Actor Constants*/
    public static final int ACTOR_ATTACK_FRAME = 20; // frame where the damage is dealt

    /*Player Constants*/
    public static final int PLAYER_WEAPON_DAMAGE = 2;
    public static final int PLAYER_STARTING_X = 285;
    public static final int PLAYER_STARTING_Y = 285;
    public static final float PLAYER_BASE_MOVEMENT = 5;
    public static final float PLAYER_BOOST_MOVEMENT = 2;
    public static final int PLAYER_STARTING_HEALTH = 10; // (this/2 is the number of hearts on the HUD) FIXME: Odd numbers may cause issues
    public static final int PLAYER_MAGIC_DAMAGE = 10;
    public static final int PLAYER_STARTING_BATTERY = 100;
    public static final int PLAYER_STARTING_RUNES = 5000;
    public static final int PLAYER_MAGIC_COOLDOWN = 1000;
    public static final float PLAYER_SCALE_WIDTH = 0.8f;
    public static final float PLAYER_SCALE_HEIGHT = 0.8f;

    /*Enemy Constants*/
    public static final int ENEMY_WEAPON_DAMAGE = 1;
    public static final int ENEMY_DEFAULT_SCORE = 100;
    public static final float ENEMY_BASE_MOVEMENT = 1;
    public static final int ENEMY_STARTING_HEALTH_MIN = 1;
    public static final int ENEMY_STARTING_HEALTH_MAX = 5;
    public static final int ENEMY_MAGIC_DAMAGE = 1;
    public static final int ENEMY_STARTING_RUNES = 5;
    public static final int ENEMY_TRANSPARENCY = 125;
    public static final float ENEMY_SCALE_WIDTH = 0.9f;
    public static final float ENEMY_SCALE_HEIGHT = 0.9f;
    public static final float BOSS_SCALE_WIDTH = 1.5f;
    public static final float BOSS_SCALE_HEIGHT = 1.5f;
    public static final int ENEMY_RANGE_DELAY_TIME = 2000;
    public static final int ENEMY_RANGE_COOLDOWN_TIME = 5000;
    public static final int ENEMY_RANGE_ACCURACY = 5;

    /*Map Constants*/
    public static final int MAP_TILE_WIDTH = 114;
    public static final int MAP_TILE_HEIGHT = 114;
    public static final float MAP_TILE_SCALE = 2f;

    /*HUD Constants*/
    public static final int HUD_HEART_DIMENSION = 30;
    public static final int STORABLE_PICKUPS = 3;

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
    public static final int ATTACK_BUTTON = 1; // A
    public static final int MINIMAP_BUTTON = 3; // Y
    public static final int BOOST_BUTTON = 2; // B

    /*Highscores Constants*/
    public static final String HIGHSCORES_USER_AGENT = "Mozilla/5.0";
    public static final String HIGHSCORES_URL = "https://protaytoe.uk/highscores/post.php?name=#&score=~&time=@&level=*&kills=£&difficulty=!";
}
