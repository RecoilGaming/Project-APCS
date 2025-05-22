package com.apcs.disunity.game.physics;

import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.signals.Signal;

/// information of collider that is needed to process collision
public class ColliderInfo {
    public final AABB AABB;
    public final Signal<CollisionInfo> COLLISION_INFO;

    public ColliderInfo(Collider collider, Vector2 absPos) {
        AABB = new AABB(collider, absPos);
        COLLISION_INFO = collider.collisionInfo;
    }
}
