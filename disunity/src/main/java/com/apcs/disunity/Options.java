package com.apcs.disunity;

/**
 * All of the configurable options for the game
 * 
 * @author Qinzhao Li
 */
public class Options {

    /* ================ [ FIELDS ] ================ */

    // Frame rate
    public static double FPS = 60.0;

    /* ================ [ METHODS ] ================ */

    // Seconds per frame
    public static double getSPF() { return 1.0 / FPS; }

    // Milliseconds per frame
    public static double getMSPF() { return 1000.0 / FPS; }

}
