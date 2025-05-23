package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * A base class for 2D nodes with position.
 * 
 * @author Qinzhao Li
 */
public class Node2D<T extends Node<?>> extends Node<T> {

    /* ================ [ FIELDS ] ================ */

    // Transform
    @SyncedObject
    private Transform transform;

    // Constructors
    public Node2D(T... children) {
        this(new Transform(), children);
    }

    public Node2D(Transform transform, T... children) {
        super(children);
        this.transform = transform;
    }

    /* ================ [ METHODS ] ================ */

    // Move position
    public void addPosition(Vector2 vel) {
        transform = transform.addPos(vel);
    }

    // Setters
    public void setPosition(Vector2 pos) {
        transform = new Transform(pos, transform.scale, transform.rot);
    }

    public void setScale(Vector2 scale) {
        transform = new Transform(transform.pos, scale, transform.rot);
    }

    public void setRotation(double rot) {
        transform = new Transform(transform.pos, transform.scale, rot);
    }

    // Getters
    public Vector2 getPosition() {
        return transform.pos;
    }

    public Vector2 getScale() {
        return transform.scale;
    }

    public double getRotation() {
        return transform.rot;
    }

    public Transform getTransform() {
        return transform;
    }

    /* ================ [ NODE ] ================ */

    @Override
    public void draw(Transform offset) {
        // Draw children relative to this
        super.draw(transform.apply(offset));
    }


    @Override
    public final void update(double delta) {
        if (!(getParent() instanceof Node2D)) {
            this.update(new Transform(), delta);
        }
        for (Node child : getAllChildren()) {
            if (child instanceof Node2D node) {
                node.update(transform, delta);
            }
        }
        super.update(delta);
    }

    public void update(Transform offset, double delta) {
        super.update(delta);
    }

    public void update(double dt, Transform t) { }
}
