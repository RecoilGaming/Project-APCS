package com.apcs.disunity.game.nodes.collider;

import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.game.physics.CollisionLayer;
import static  com.apcs.disunity.game.physics.CollisionLayer.*;
import com.apcs.disunity.game.physics.CollisionMask;

/**
 * A node that represents a colliding boundary
 * 
 * @author Aayushya Patel
 */
public class Collider extends Node2D<Node<?>> {
    public static final CollisionLayer DEFAULT_LAYER = LAYER_0;
    public static final CollisionMask DEFAULT_MASK = new CollisionMask(DEFAULT_LAYER);
    public static final CollisionMask ALL_LAYER_MASK = new CollisionMask(~0);
    public static final CollisionMask NO_LAYER_MASK = new CollisionMask(0);

    public Vector2 SIZE;
    /// represents the layer this collider can collide in
    public final CollisionLayer LAYER;

    public Collider(Transform t, int w, int h) {
        super(t);
        SIZE = Vector2.of(w, h);
        this.LAYER = DEFAULT_LAYER;
    }

    public Collider(int w, int h, CollisionLayer layer) {
        SIZE = Vector2.of(w, h);
        this.LAYER = layer;
    }

    public Collider(int w, int h) { this(w, h, DEFAULT_LAYER); }
}
