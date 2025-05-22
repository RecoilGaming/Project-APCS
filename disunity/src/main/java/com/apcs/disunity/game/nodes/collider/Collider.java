package com.apcs.disunity.game.nodes.collider;

import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.game.physics.CollisionInfo;
import com.apcs.disunity.game.physics.CollisionLayer;
import com.apcs.disunity.game.physics.CollisionMask;
import com.apcs.disunity.game.signals.Signal;

/**
 * A node that represents a colliding boundary
 * 
 * @author Aayushya Patel
 */
public class Collider extends Node2D<Node<?>> {
    public static final CollisionLayer DEFAULT_LAYER = new CollisionLayer((byte) 0);
    public static final CollisionMask DEFAULT_MASK = new CollisionMask(DEFAULT_LAYER);
    public static final CollisionMask ALL_LAYER_MASK = new CollisionMask(~0);
    public static final CollisionMask NO_LAYER_MASK = new CollisionMask(0);

    public Vector2 SIZE;

    public final CollisionLayer LAYER;
    public final CollisionMask MASK;

    public Signal<CollisionInfo> collisionInfo = new Signal<>(CollisionInfo.class);

    public Collider(Transform t, int w, int h) {
        super(t);
        SIZE = Vector2.of(w, h);
        this.LAYER = DEFAULT_LAYER;
        this.MASK = DEFAULT_MASK;
    }

    public Collider(int w, int h, CollisionLayer layer, CollisionMask mask) {
        SIZE = Vector2.of(w, h);
        this.LAYER = layer;
        this.MASK = mask;
    }

    public Collider(int w, int h) { this(w, h, DEFAULT_LAYER, DEFAULT_MASK); }
}
