package com.apcs.disunity.physics;

import com.apcs.disunity.math.Rectangle;
import com.apcs.disunity.math.Vector2;

/**
 * Contains bounding box information about a collision
 * 
 * @author Aayushya Patel
 * @author Qinzhao Li
 */
public class CollisionInfo {

    /* ================ [ FIELDS ] ================ */

    /** Whether the collision is inverted */
    private final boolean isInverted;

    /** The bounds of the area that received this collision */
    private final Rectangle target;
    /** The previous bounds of the area that received this collision */
    private final Rectangle prevTarget;

    /** The bounds of the area that received this collision */
    private final Rectangle source;
    /** The previous bounds of the area that received this collision */
    private final Rectangle prevSource;

    /**
     * Create a new CollisionInfo with the given colliders
     * 
     * @param self The area that received this collision
     * @param other The area that triggered this collision
     */
    public CollisionInfo(Area self, Area other) {
        this(self.getBounds(), self.getPrevBounds(), other.getBounds(), other.getPrevBounds(), other.isInverted());
    }
    /**
     * Create a new CollisionInfo with the given rectangles and inverted state
     * 
     * @param target The bounds of the area that received this collision
     * @param prevTarget The previous bounds of the area that received this collision
     * @param source The bounds of the area that triggered this collision
     * @param prevSource The previous bounds of the area that triggered this collision
     * @param isInverted Whether the collision is inverted
     */
    public CollisionInfo(Rectangle target, Rectangle prevTarget, Rectangle source, Rectangle prevSource, boolean isInverted) {
        this.target = target;
        this.prevTarget = prevTarget;
        this.source = source;
        this.prevSource = prevSource;
        this.isInverted = isInverted;
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the position of the target after collision
     *
     * @return The position of the target after collision
     */
    public Vector2 getPos() {
        return target.getCenter();
    }

    /**
     * Get the position of the target before collision
     *
     * @return The position of the target before collision
     */
    public Vector2 getPrevPos() {
        return prevTarget.getCenter();
    }

    /**
     * Get the movement of the target
     * 
     * @return The movement of the target
     */
    public Vector2 getMovement() {
        return target.getCenter().sub(prevTarget.getCenter());
    }

    /**
     * Get whether the collision was on the top
     * 
     * @return Whether the collision was on the top
     */
    public boolean onTop() {
        if (isInverted) {
            return prevSource.bottom > prevTarget.top && source.bottom <= target.top;
        }

        return prevSource.bottom < prevTarget.top && source.bottom >= target.top;
    }

    /**
     * Get whether the collision was on the bottom
     * 
     * @return Whether the collision was on the bottom
     */
    public boolean onBottom() {
        if (isInverted) {
            return prevSource.top > prevTarget.bottom && source.top <= target.bottom;
        }

        return prevSource.top > prevTarget.bottom && source.top <= target.bottom;
    }

    /**
     * Get whether the collision was on the left
     * 
     * @return Whether the collision was on the left
     */
    public boolean onLeft() {
        if (isInverted) {
            return prevSource.right > prevTarget.left && source.right <= target.left;
        }

        return prevSource.right < prevTarget.left && source.right >= target.left;
    }

    /**
     * Get whether the collision was on the right
     * 
     * @return Whether the collision was on the right
     */
    public boolean onRight() {
        if (isInverted) {
            return prevSource.left > prevTarget.right && source.left <= target.right;
        }
        
        return prevSource.left > prevTarget.right && source.left <= target.right;
    }

}
