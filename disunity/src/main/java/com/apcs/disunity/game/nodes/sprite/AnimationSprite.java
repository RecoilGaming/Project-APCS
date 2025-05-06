package com.apcs.disunity.game.nodes.sprite;


import com.apcs.disunity.game.nodes.selector.Indexed;

import java.awt.image.BufferedImage;

/**
 * Handles one animation with frames
 * 
 * @author Qinzhao Li
 */
public class AnimationSprite extends Sprite implements Indexed<String> {

    /* ================ [ FIELDS ] ================ */

    // Animation name
    private final String name;
    private long prevFrame = System.nanoTime();
    private final ImageLocation baseImage;

    // Frame durations list
    private final double[] frameDurations;

    // Current frame
    private int frameCount = 0;

    public AnimationSprite(String name, ImageLocation imageLocation, double... frameDurations) {
        super(imageLocation);
        baseImage = imageLocation;
        this.name = name;
        this.frameDurations = frameDurations;
        updateFrame();
    }

    public AnimationSprite(String name, String path, double... frameDurations) {
        this(name, new ImageLocation(path), frameDurations);
    }

    // Constructors

    /* ================ [ METHODS ] ================ */

    // Get frame duration
    protected double frameDuration() { return frameDurations[frameCount]; }

    // Get frame count
    protected int length() { return frameDurations.length; }

    @Override
    public String index() {
        return name;
    }

    @Override
    public void update(double delta) {
        // Update frame
        if (System.nanoTime() - prevFrame >= frameDuration() * 1e9) {
            prevFrame = System.nanoTime();
            frameCount++;
            updateFrame();
        }
    }

    public void updateFrame() {
        frameCount %= length();

        BufferedImage img = baseImage.getImage();

        int w = img.getWidth() / length();
        setImageLocation(new ImageLocation(
            baseImage.PATH,
            w*frameCount, 0,
            w, img.getHeight()
        ));
    }

}
