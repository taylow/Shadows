package edu.lancs.game;

import static edu.lancs.game.Constants.DEBUG;

public class Debug {
    /***
     * Used to debug the game while in development. Can be enabled/disabled through
     * Constants.java
     * @param string - The string to be printed to the console
     */
    public static void print(String string) {
        // if Constant DEBUG is true, print to console
        if(DEBUG)
            System.out.println(string);
    }
}
