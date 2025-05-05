package com.apcs.ljaag.nodes.action;

import com.apcs.disunity.applyable.TransformApplyable;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.Node;

/**
 * Allows a node to turn in the direction of its movement
 *
 * @author Qinzhao Li
 */
public class TurnAction extends Node implements TransformApplyable {

    /* ================ [ FIELDS ] ================ */

    /** Where the node is currently facing */
    private int facing = 1;

    /** The previous X position of the node */
    private double prevX = 0;

    /* ================ [ APPLIABLE ] ================ */

    /**
     * Applies the rotation transformation by inverting X scale
     * 
     * @param transform The original transform
     * @param delta The time since the last update
     * @return The new transform
     */
    @Override
    public Transform apply(Transform transform, double delta) {
        Transform res = transform;

        // Move direction
        double moving = transform.pos.x - prevX;

        // Check for turn
        if (Math.abs(moving) > 0.1 && (int) Math.signum(moving) != facing) {
            res = res.scale(Vector2.of(-1, 1));
            facing = -facing;
        }

        // Update previous x
        prevX = transform.pos.x;
        return res;
    }
    
}
