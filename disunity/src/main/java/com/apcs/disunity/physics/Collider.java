package com.apcs.disunity.physics;

import com.apcs.disunity.math.Vector2;

/**
 * Detects collisions and prevents clipping with other colliders
 * 
 * @author Aayushya Patel
 * @author Qinzhao Li
 */
public class Collider extends Area {

    /* ================ [ FIELDS ] ================ */

    /**
     * Creates a new Collider with the given size
     * 
     * @param size The size of the collider
     */
    public Collider(Vector2 size) {
        super(size);
    }
    
}
