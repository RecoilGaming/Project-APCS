package com.apcs.disunity.animation;

import com.apcs.disunity.nodes.UndrawnNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Stores a set of animations for some game element.
 * 
 * @author Qinzhao Li
 */
public class AnimationSet extends UndrawnNode {

    /* ================ [ FIELDS ] ================ */

    // Base image
    private final String base;

    // Animations
    private final Map<String, Animation> animations = new HashMap<>();

    // Constructors
    public AnimationSet(String base, Animation... animations) {
        this.base = base;
        for (Animation animation : animations)
            this.animations.put(animation.getName(), animation);
    }

    /* ================ [ METHODS ] ================ */

    // Get base image
    public String getBase() { return base; }

    // Get animation
    public Animation getAnimation(String name) {
        if (animations.containsKey(name)) return animations.get(name);
        else throw new RuntimeException("Unable to resolve animation: "+name);
    }

    @Override
    public List<UndrawnNode> getChildren() {
        return Stream.concat(super.getChildren().stream(),animations.values().stream()).toList();
    }
}
