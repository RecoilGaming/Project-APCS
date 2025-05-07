package com.apcs.disunity.physics;

import com.apcs.disunity.math.Rectangle;

/**
 * Contains bounding box information about a collision
 * 
 * @author Aayushya Patel
 * @author Q`izhao Li
 */
public class CollisionInfo {

    /* ================ [ FIELDS ] ================ */

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
        this.target = self.getBounds();
        this.prevTarget = self.getPrevBounds();
        this.source = other.getBounds();
        this.prevSource = other.getPrevBounds();
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Get whether the collision was on the top
     * 
     * @return Whether the collision was on the top
     */
    public boolean onTop() {
        return prevSource.bottom < prevTarget.top && source.bottom >= target.top;
    }

    /**
     * Get whether the collision was on the bottom
     * 
     * @return Whether the collision was on the bottom
     */
    public boolean onBottom() {
        return prevSource.top > prevTarget.bottom && source.top <= target.bottom;
    }

    /**
     * Get whether the collision was on the left
     * 
     * @return Whether the collision was on the left
     */
    public boolean onLeft() {
        return prevSource.right < prevTarget.left && source.right >= target.left;
    }

    /**
     * Get whether the collision was on the right
     * 
     * @return Whether the collision was on the right
     */
    public boolean onRight() {
        return prevSource.left > prevTarget.right && source.left <= target.right;
    }

}
