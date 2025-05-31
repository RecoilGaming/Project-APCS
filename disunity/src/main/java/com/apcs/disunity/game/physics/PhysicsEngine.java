package com.apcs.disunity.game.physics;

import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

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
        for (Area2D area: searchNode(scene, Vector2.ZERO, Area2D.class, new ArrayList<>())) {
            for (Body body: searchNode(scene, Vector2.ZERO, Body.class, new ArrayList<>())) {

                if (
                    body.area2D != area &&
                    (body.collider.LAYER.BITSET & area.MASK.BITSET) != 0 &&
                        new AABB(body.collider.SIZE, body.getGlobalTrans().pos).isColliding(new AABB(area.size, area.getGlobalTrans().pos))
                ) {
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
