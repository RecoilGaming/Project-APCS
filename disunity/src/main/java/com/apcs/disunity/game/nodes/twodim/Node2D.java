package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * A base class for 2D nodes with position
 * 
 * @author Qinzhao Li
 */
public class Node2D<T extends Node<?>> extends Node<T> {

    /* ================ [ FIELDS ] ================ */

    // Transform
    @SyncedObject
    private Transform transform;

    // Constructors
    public Node2D(T... children) { this(new Transform(), children); }

    public Node2D(Transform transform, T... children) {
        super(children);
        this.transform = transform;
    }

    /* ================ [ NODE ] ================ */

    @Override
    public void draw(Transform offset) {
        // Draw children relative to this
        super.draw(transform.apply(offset));
    }

    public void setPos(Vector2 pos) { transform = new Transform(pos, transform.scale, transform.rot); }

    public void addPos(Vector2 vel) { transform = transform.addPos(vel); }

    public void setScale(Vector2 scale) { transform = new Transform(transform.pos, scale, transform.rot); }

    public void setRot(double rot) { transform = new Transform(transform.pos, transform.scale, rot); }

    public Vector2 getPos() { return transform.pos; }

    public Vector2 getScale() { return transform.pos; }

    public double getRot() { return transform.rot; }

    public Transform getTransform() { return transform; }

    @Override
    public final void update(double dt) {
        if (!(getParent() instanceof Node2D)) {
            this.update(dt, new Transform());
        }
        for (Node n : getAllChildren()) {
            if (n instanceof Node2D node2D) {
                node2D.update(dt, transform);
            }
        }
        super.update(dt);
    }

    public void update(double dt, Transform t) { }
}
