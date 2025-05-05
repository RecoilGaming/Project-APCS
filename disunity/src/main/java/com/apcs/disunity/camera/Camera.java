package com.apcs.disunity.camera;

import com.apcs.disunity.Game;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.Node2D;

/**
 * Controls the viewport by modifying the global transform
 * 
 * @author Qinzhao Li
 */
public class Camera extends Node2D {

    /* ================ [ FIELDS ] ================ */

    /** Whether or not this camera is active */
    private boolean isActive = true;

    /** Creates a new Camera */
    public Camera() { super(); }
    /**
     * Creates a new Camera with the given children
     * 
     * @param children The children of this node
     */
    public Camera(Node... children) { super(children); }
    /**
     * Creates a new Camera with the given transform and children
     * 
     * @param transform The transform of this node
     * @param children The children of this node
     */
    public Camera(Transform transform, Node... children) { super(transform, children); }

    /* ================ [ METHODS ] ================ */

    /** Get whether or not this camera is active */
    public boolean isActive() { return isActive; }

    /**
     * Set whether or not this camera is active
     * 
     * @param isActive Whether or not this camera is active
     */
    public void setActive(boolean isActive) { this.isActive = isActive; }

    /* ================ [ NODE ] ================ */

    /**
     * Update the global transform of the game
     * 
     * @param offset The offset of the node
     * @param delta The time since the last update
     */
    @Override
    public void update(Transform offset, double delta) {
        // Update global transform
        if (isActive) {
            Game.getInstance().setTransform(transform);
        }

        // Update children
        super.update(transform, delta);
    }

}
