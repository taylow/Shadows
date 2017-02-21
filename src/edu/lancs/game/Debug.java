package edu.lancs.game;

import static edu.lancs.game.Constants.DEBUG_ERRORS;
import static edu.lancs.game.Constants.DEBUG_MESSAGES;

public class Debug {
    // colour codes for console colours
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /***
     * Used to debug the game while in development. Can be enabled/disabled through
     * Constants.java
     *
     * @param string - The string to be printed to the console
     */
    public static void print(String string) {
        // if Constant DEBUG is true, print to console
        if (DEBUG_MESSAGES)
            System.out.println(ANSI_GREEN + "[DEBUG] " + string + ANSI_RESET);
    }

    /***
     * Used to debug the game while in development but uses a red message. Can be enabled/disabled through
     * Constants.java
     *
     * @param string - The string to be printed to the console
     */
    public static void error(String string) {
        // if Constant DEBUG is true, print to console
        if (DEBUG_ERRORS)
            System.out.println(ANSI_RED + "[ERROR] " + string + ANSI_RESET);
    }
}
