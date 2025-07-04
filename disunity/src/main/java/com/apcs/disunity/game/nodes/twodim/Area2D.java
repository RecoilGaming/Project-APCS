package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.game.physics.CollisionMask;
import com.apcs.disunity.game.signals.Signal;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/// node that detects if collider is colliding with itself.
public class Area2D extends Node2D<Node<?>> {

    public final Signal<BodyEntered> bodyEnteredSignal = new Signal<>(BodyEntered.class);
    /// determines which layers can collide with this area
    public final CollisionMask MASK;
    public Vector2 size;

    public Area2D(Vector2 size, CollisionMask mask) {
        this.size = size;
        MASK = mask;
    }

    public Area2D(Vector2 size) { this(size, Collider.DEFAULT_MASK); }
    public Area2D(double w, double h) { this(Vector2.of(w,h)); }

    @Override
    @SuppressWarnings("unused")
    public void draw(Transform offset) {
        if (false) { // Toggle drawing of areas
            Game.getInstance().getBuffer().drawArea(this);
        }
    }

}
