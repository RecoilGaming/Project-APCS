package com.apcs.disunity.physics;

import com.apcs.disunity.applyable.TransformApplyable;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * Detects collisions and prevents clipping with other colliders
 * 
 * @author Aayushya Patel
 * @author Qinzhao Li
 */
public class Collider extends Area implements TransformApplyable {

    /* ================ [ FIELDS ] ================ */

    /** The previous collision info */
    private CollisionInfo prevInfo;

    /**
     * Creates a new Collider with the given size
     * 
     * @param size The size of the collider
     */
    public Collider(Vector2 size) {
        super(size);
    }
    /**
     * Creates a new Collider with the given size and inverted state
     * 
     * @param size The size of the collider
     * @param isInverted Whether the collider is inverted
     */
    public Collider(Vector2 size, boolean isInverted) {
        super(size, isInverted);
    }

    /* ================ [ AREA ] ================ */

    /**
     * Handle a collision with another area
     * 
     * @param info The collision info
     */
    public void handleCollision(CollisionInfo info) {
        prevInfo = info;
    }

    /* ================ [ APPLIABLE ] ================ */

    /**
     * Applies the target position after a collision
     * 
     * @param transform The original transform
     * @param delta The time since the last update
     * @return The new transform
     */
    @Override
    public Transform apply(Transform transform, double delta) {
        if (prevInfo != null) {
            if (getId() == 2) {
                System.out.println(prevInfo.getMovement());
            }

            Transform res = transform.move(prevInfo.getMovement().mul(-1));
            prevInfo = null;
            return res;
        }
        
        return transform;
    }
    
}
