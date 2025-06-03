package com.apcs.disunity.game.physics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Vector2;

/**
 * Manages physics and collision detection for all nodes
 * 
 * @author Aayushya Patel
 */
public class PhysicsEngine {
    public static ArrayList<Area2D> area2DS = new ArrayList<>();
    public static ArrayList<Body> bodies = new ArrayList<>();

    /**
     * Update the physics system
     */
    public static void run(Scene scene, double delta) {
        List<Area2D> areas = searchNode(scene, Vector2.ZERO, Area2D.class, new ArrayList<>());
        List<Body> bodies = searchNode(scene, Vector2.ZERO, Body.class, new ArrayList<>());
        bodies.sort(Comparator.comparingDouble(b -> new AABB(b.collider.SIZE, b.getGlobalTrans().pos).LEFT));

        for (Area2D area : areas) {
            AABB areaAABB = new AABB(area.size, area.getGlobalTrans().pos);
            for (Body body : bodies) {
                if (body.area2D == area) continue;
                AABB bodyAABB = new AABB(body.collider.SIZE, body.getGlobalTrans().pos);
                if (bodyAABB.LEFT > areaAABB.RIGHT) break;
                if ((body.collider.LAYER.BITSET & area.MASK.BITSET) != 0 && bodyAABB.isColliding(areaAABB)) {
                    area.bodyEnteredSignal.emit(new BodyEntered(body));
                }
            }
        }
    }

    private static <T extends Node2D<?>> ArrayList<T> searchNode(Node<?> node, Vector2 absPos, Class<T> clazz, ArrayList<T> infos) {
        if (clazz.isAssignableFrom(node.getClass())) {
            T needle = (T) node;
            infos.add(needle);
        } else {
            if (node instanceof Node2D<?> node2D) {
                node.getAllChildren().forEach(n -> searchNode(n, absPos.add(node2D.getPosition()), clazz, infos));
            } else {
                node.getAllChildren().forEach(n -> searchNode(n, absPos, clazz, infos));
            }
        }
        return infos;
    }
}
