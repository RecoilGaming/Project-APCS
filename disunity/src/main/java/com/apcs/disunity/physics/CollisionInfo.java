package com.apcs.disunity.physics;

/**
 * Contains info for collision between two colliders
 * @author Aayushya Patel
 */
public class CollisionInfo {

    private final Collider me;
    private final Collider you;

    public CollisionInfo(Collider self, Collider other) {
        this.me = self;
        this.you = other;
    }

    /**
     * Get the collider that received this collision
     */
    public Collider getMe() {
        return me;
    }

    /**
     * Get the other collider in this collision
     */
    public Collider getYou() {
        return you;
    }

    // /**
    //  * Check if this is a trigger collision
    //  */
    // public boolean isTrigger() {
    //     return me.isTrigger() || you.isTrigger();
    // }
}
