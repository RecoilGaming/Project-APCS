package com.apcs.disunity.nodes.sprite;

import java.awt.image.BufferedImage;

import com.apcs.disunity.Game;
import com.apcs.disunity.animation.AnimationSet;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.Node2D;
import com.apcs.disunity.nodes.controller.Controllable;
import com.apcs.disunity.resources.Image;
import com.apcs.disunity.resources.Resources;
import com.apcs.disunity.signals.Signals;

/**
 * A sprite that can play animations
 * 
 * @author Qinzhao Li
 */
public class AnimatedSprite extends Node2D implements Controllable {

    /* ================ [ FIELDS ] ================ */

    /** The id of the controller */
    private int controller;

    /** The set of animations to play */
    private final AnimationSet animations;

    /** The current animation id */
    private String animation = "";

    /** The timestamp of the previous frame */
    private long prevFrame = System.nanoTime();

    /**
     * Create a new AnimatedSprite with the given animation set
     *
     * @param animations The animation set to play
     */
    public AnimatedSprite(AnimationSet animations) { super(); this.animations = animations; }
    /**
     * Create a new AnimatedSprite with the given animation set and children
     *
     * @param animations The animation set to play
     * @param children The children of this node
     */
    public AnimatedSprite(AnimationSet animations, Node... children) { super(children); this.animations = animations; }
    /**
     * Create a new AnimatedSprite with the given animation set, transform, and children
     *
     * @param animations The animation set to play
     * @param transform The transform of this node
     * @param children The children of this node
     */
    public AnimatedSprite(AnimationSet animations, Transform transform, Node... children) { super(transform, children); this.animations = animations; }
    /**
     * Create a new AnimatedSprite with the given animation set, transform, visibility, and children
     *
     * @param animations The animation set to play
     * @param transform The transform of this node
     * @param visible Whether or not the node is visible
     * @param children The children of this node
     */
    public AnimatedSprite(AnimationSet animations, Transform transform, boolean visible, Node... children) { super(transform, visible, children); this.animations = animations; }
    
    /* ================ [ CONTROLLABLE ] ================ */

    /**
     * Set the controller id
     * 
     * @param controller The controller id
     */
    @Override
    public void setController(int controller) { this.controller = controller; }
    
    /* ================ [ METHODS ] ================ */

    /**
     * Set the animation to be played
     *
     * @param animation The animation id
     */
    @SuppressWarnings("StringEquality")
    public void setAnimation(String animation) {
        if (this.animation.equals(animation)) return;
        this.animation = animation;
        prevFrame = System.nanoTime();
    }

    /**
     * Get the current animation id
     *
     * @return The current animation id
     */
    public String getAnimation() { return animation; }

    /* ================ [ NODE ] ================ */

    /** Initialize the node */
    @Override
    public void initialize() {
        // Connect to signal
        Signals.connect(Signals.getSignal(controller, "animate"), this::setAnimation);

        // Complete initialization
        super.initialize();
    }

    /** 
     * Update the node and its children
     * 
     * @param offset The offset of the node
     * @param delta The time since the last update
     */
    @Override
    public void update(Transform offset, double delta) {
        // Update frame
        if (!animation.isEmpty()) {
            if (System.nanoTime() - prevFrame >= animations.getAnimation(animation).getFrameDuration() * 1000000000) {
                prevFrame = System.nanoTime();
                animations.getAnimation(animation).nextFrame();
            }
        }

        // Update children
        super.update(offset, delta);
    }

    /**
     * Draw the node and its children
     * 
     * @param offset The offset of the node
     */
    @Override
    public void draw(Transform offset) {
        BufferedImage img;
        if (animation.isEmpty()) {
            // Default sprite fallback
            img = Resources.loadResource(animations.getBase(), Image.class).getBuffer();
        } else {
            // Load current frame
            img = Resources.loadResource(animations.getAnimation(animation).getPath(), Image.class).getBuffer();

            // Crop image to current frame
            int w = img.getWidth() / animations.getAnimation(animation).getFrameCount();
            img = img.getSubimage(
                w * animations.getAnimation(animation).getFrame(), 0,
                w, img.getHeight()
            );
        }
            
        // Draw image to buffer
        Game.getInstance().getBuffer().drawImage(img, transform.apply(offset));
        
        // Draw children
        super.draw(offset);
    }
    
}
