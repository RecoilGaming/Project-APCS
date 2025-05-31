package com.apcs.disunity.game.nodes.sprite;

import java.awt.image.BufferedImage;

import com.apcs.disunity.game.selector.Indexed;
import com.apcs.disunity.math.Transform;

/**
 * sprite that renders timed cyclic animation, created by splicing a base image
 * that contains equal width frames horizontally
 * 
 * @author Qinzhao Li
 */
public class AnimatedSprite extends Sprite implements Indexed<String> {

    /* ================ [ FIELDS ] ================ */

    // Animation name
    private final String name;
    private long prevFrame = System.nanoTime();
    private ImageLocation baseImage;

    // Frame durations list
    private double[] frameDurations;

    boolean looping = false;

    // Current frame
    private int frameCount = 0;

    public AnimatedSprite(String name, ImageLocation imageLocation, double... frameDurations) {
        this(name, imageLocation, false, frameDurations);
    }

    public AnimatedSprite(String name, String path, double... frameDurations) {
        this(name, new ImageLocation(path), frameDurations);
    }

    public AnimatedSprite(String name, ImageLocation imageLocation, boolean looping, double... frameDurations) {
        super(imageLocation);
        this.looping = looping;
        baseImage = imageLocation;
        this.name = name;
        this.frameDurations = frameDurations;
        updateFrame();
    }

    public AnimatedSprite(String name, String path, boolean looping, double... frameDurations) {
        this(name, new ImageLocation(path), looping, frameDurations);
    }

    // Constructors

    /* ================ [ METHODS ] ================ */

    // Get frame duration
    protected double frameDuration() { return frameDurations[frameCount]; }

    // Get frame count
    protected int length() { return frameDurations.length; }

    @Override
    public String index() { return name; }

    @Override
    public void update(double delta) {
        // Update frame
        if (!isHidden() && System.nanoTime() - prevFrame >= frameDuration() * 1e9) {
            prevFrame = System.nanoTime();
            frameCount++;
            updateFrame();
        }

        super.update(delta);
    }

    public void updateFrame() {
        if (!looping && frameCount == length()) {
            setHidden(true);
        } else {
            frameCount %= length();

            BufferedImage img = baseImage.getImage();

            int w = img.getWidth() / length();
            setImageLocation(new ImageLocation(baseImage.PATH, w * frameCount, 0, w, img.getHeight()));
        }
    }

    public void reset() {
        frameCount = 0;
        updateFrame();
        prevFrame = System.nanoTime();
    }

    public void play() {
        reset();
        setHidden(false);
    }

    public void setBaseImage(ImageLocation val) {
        this.baseImage = val;
        updateFrame();
    }

    public void setFrameDurations(double[] durations) {
        this.frameDurations = durations;
    }

}
