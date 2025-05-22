package com.apcs.disunity.game.physics;

import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;

import java.util.ArrayList;

/**
 * Manages physics and collision detection for all nodes
 * 
 * @author Aayushya Patel
 */
public class PhysicsEngine {

    /**
     * Update the physics system
     */
    public static void run(Scene scene, double delta) {
        ArrayList<ColliderInfo> colliders = new ArrayList<>();

        searchCollider(scene, Vector2.ZERO, colliders);

        for (ColliderInfo c1 : colliders) {
            for (ColliderInfo c2 : colliders) {
                if (c1 != c2 && c1.AABB.isColliding(c2.AABB)) {
                    c1.COLLISION_INFO.emit(new CollisionInfo(c1.AABB, c2.AABB, delta));
                }
            }
        }
    }

    private static void searchCollider(Node<?> node, Vector2 absPos, ArrayList<ColliderInfo> infos) {
        if (node instanceof Collider collider) {
            infos.add(new ColliderInfo(collider, absPos.add(collider.getPosition())));
        } else {
            if (node instanceof Node2D<?> node2D) {
                node.getAllChildren().forEach(n -> searchCollider(n, absPos.add(node2D.getPosition()), infos));
            } else {
                node.getAllChildren().forEach(n -> searchCollider(n, absPos, infos));
            }
        }
    }
}
