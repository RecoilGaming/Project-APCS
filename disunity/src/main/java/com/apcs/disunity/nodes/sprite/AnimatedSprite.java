package com.apcs.disunity.nodes.sprite;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Stream;

import com.apcs.disunity.Game;
import com.apcs.disunity.animation.AnimationSet;
import com.apcs.disunity.annotations.syncedfield.SyncedObject;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.Node2D;
import com.apcs.disunity.nodes.controller.Controllable;
import com.apcs.disunity.resources.Image;
import com.apcs.disunity.resources.Resources;
import com.apcs.disunity.server.SyncHandler;
import com.apcs.disunity.server.SyncedString;
import com.apcs.disunity.signals.Signals;

/**
 * A sprite that can play animations
 * 
 * @author Qinzhao Li
 */
public class AnimatedSprite extends Node2D implements Controllable {

    /* ================ [ FIELDS ] ================ */

    // Controller id
    private int controller;

    // Animation set
    private final AnimationSet animations;

    // Current animation
    @SyncedObject
    private SyncedString animation = new SyncedString("");

    // Previous frame time
    private long prevFrame = System.nanoTime();

    // Constructors
    public AnimatedSprite(AnimationSet animations) { super(); this.animations = animations; }
    public AnimatedSprite(AnimationSet animations, Node<?>... children) { super(children); this.animations = animations; }
    public AnimatedSprite(AnimationSet animations, Transform transform, Node<?>... children) { super(transform, children); this.animations = animations; }
    public AnimatedSprite(AnimationSet animations, boolean visible) { super(visible); this.animations = animations; }
    public AnimatedSprite(AnimationSet animations, boolean visible, Node<?>... children) { super(visible, children); this.animations = animations; }
    public AnimatedSprite(AnimationSet animations, Transform transform, boolean visible, Node<?>... children) { super(transform, visible, children); this.animations = animations; }
    
    /* ================ [ CONTROLLABLE ] ================ */

    // Set controller id
    @Override
    public void setController(int controller) { this.controller = controller; }
    
    /* ================ [ METHODS ] ================ */

    // Set animation
    @SuppressWarnings("StringEquality")
    public void setAnimation(String animation) {
        if (this.animation.value() == animation) return;
        this.animation.setValue(animation);
        prevFrame = System.nanoTime();
    }

    // Get animation
    public String getAnimation() { return animation.value(); }

    /* ================ [ NODE ] ================ */

    @Override
    public void initialize() {
        // Connect to signal
        Signals.connect(Signals.getSignal(controller, "animate"), this::setAnimation);

        // Complete initialization
        super.initialize();
    }

    @Override
    public void update(double delta) {
        // Update frame
        if (!animation.value().isEmpty() && !SyncHandler.getInstance().isClient()) {
            if (System.nanoTime() - prevFrame >= animations.getAnimation(animation.value()).getFrameDuration() * 1000000000) {
                prevFrame = System.nanoTime();
                animations.getAnimation(animation.value()).nextFrame();
            }
        }

        // Update children
        super.update(delta);
    }

    @Override
    public void draw(Transform offset) {

        BufferedImage img;
        // keep the copy of synced value, to avoid unexpected override
        String animation = this.animation.value();
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

    @Override
    public List<Node<?>> getChildren() {
        return Stream.concat(super.getChildren().stream(),animations.getChildren().stream()).toList();
    }
}
