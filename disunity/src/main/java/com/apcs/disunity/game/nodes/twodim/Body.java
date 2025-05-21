package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.physics.CollisionInfo;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * A 2d node that can handle movement and collision
 * 
 * @author Qinzhao Li
 */
public abstract class Body extends Node2D<Node<?>> {

    /* ================ [ FIELDS ] ================ */

    // Velocity
    @SyncedObject
    private Vector2 vel = Vector2.ZERO;

    @FieldChild
    public final Collider collider;

    // Constructors
    public Body(Collider collider, Node<?>... children) { this(new Transform(), collider, children); }

    public Body(Transform transform, Collider collider, Node<?>... children) {
        super(transform, children);
        this.collider = collider;
        collider.collisionInfo.connect(this::onCollision);
    }

    /* ================ [ METHODS ] ================ */

    // Set velocity
    public void setVel(Vector2 vel) { this.vel = vel; }

    // Get velocity
    public Vector2 getVel() { return vel; }

    /* ================ [ NODE ] ================ */

    public void update(double delta, Transform t) {
        addPos(vel.mul(delta));

        super.update(delta, t);
    }

    public abstract void onCollision(CollisionInfo info);

}
