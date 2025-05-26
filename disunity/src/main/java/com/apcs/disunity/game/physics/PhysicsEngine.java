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
        for (Map.Entry<Area2D,Vector2> area: searchNode(scene, Vector2.ZERO, Area2D.class, new ArrayList<>())) {
            for (Map.Entry<Body,Vector2> body: searchNode(scene, Vector2.ZERO, Body.class, new ArrayList<>())) {

                if (
                    body.getKey().area2D != area.getKey() &&
                    (body.getKey().collider.LAYER.BITSET & area.getKey().MASK.BITSET) != 0 &&
                        new AABB(body.getKey().collider.SIZE, body.getValue()).isColliding(new AABB(area.getKey().size, area.getValue()))
                ) {
                    area.getKey().bodyEnteredSignal.emit(new BodyEntered(body.getKey()));
                }
            }
        }
    }

    private static <T extends Node2D<?>> ArrayList<Map.Entry<T,Vector2>> searchNode(Node<?> node, Vector2 absPos, Class<T> clazz, ArrayList<Map.Entry<T,Vector2>> infos) {
        if (clazz.isAssignableFrom(node.getClass())) {
            T needle = (T) node;
            infos.add(new AbstractMap.SimpleEntry<>(needle, absPos.add(needle.getPosition())));
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
