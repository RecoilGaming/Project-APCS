package com.apcs.disunity.animation;

/**
 * Handles frame progression within an animation
 * 
 * @author Qinzhao Li
 */
public class Animation {

    /* ================ [ FIELDS ] ================ */

    /** The name of the animation */
    private final String name;
    /** The path to the animation sheet */
    private final String path;

    /** Lists the durations of each frame */
    private final double[] frameDurations;

    /** The current frame index */
    private int frame = 0;

    /**
     * Creates a new Animation with the given name, path, and frame durations
     * 
     * @param name The name of the animation
     * @param path The path to the animation sheet
     * @param frameDurations The durations of each frame
     */
    public Animation(String name, String path, double... frameDurations) {
        this.name = name;
        this.path = path;
        this.frameDurations = frameDurations;
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the name of the animation
     * 
     * @return The name of the animation
     */
    public String getName() { return name; }

    /**
     * Get the path to the animation sheet
     * 
     * @return The path to the animation sheet
     */
    public String getPath() { return path; }

    /**
     * Get the current frame index
     * 
     * @return The current frame index
     */
    public int getFrame() { return frame; }

    /**
     * Get the total number of frames
     * 
     * @return The total number of frames
     */
    public int getFrameCount() { return frameDurations.length; }

    /**
     * Get the duration of the current frame
     * 
     * @return The duration of the current frame
     */
    public double getFrameDuration() { return frameDurations[frame]; }

    /** Advance to the next frame */
    public void nextFrame() { frame = (frame + 1) % frameDurations.length; }


}
