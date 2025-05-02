package com.apcs.disunity.animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores a set of animations for some object
 * 
 * @author Qinzhao Li
 */
public class AnimationSet {

    /* ================ [ FIELDS ] ================ */

    /** The base (fallback) image */
    private final String base;

    /** Maps names to animation objects */
    private final Map<String, Animation> animations = new HashMap<>();

    /**
     * Creates a new AnimationSet with the given base image and animations
     * 
     * @param base The base image
     * @param animations The animations
     */
    public AnimationSet(String base, Animation... animations) {
        this.base = base;

        // Map provided animations
        for (Animation animation : animations) {
            this.animations.put(animation.getName(), animation);
        }
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the base image
     * 
     * @return The base image
     */
    public String getBase() { return base; }

    /**
     * Get an animation by name
     *
     * @param name The name of the animation
     * @return The animation
     */
    public Animation getAnimation(String name) {
        if (animations.containsKey(name)) {
            return animations.get(name);
        } else {
            throw new RuntimeException("Animation not found: " + name);
        }
    }
    
}
