package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.game.physics.PhysicsEngine;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * A 2d node that can handle movement and collision
 * 
 * @author Qinzhao Li
 */
public abstract class Body extends Node2D<Node<?>> {

    /* ================ [ CHILDREN ] ================ */

    @FieldChild
    public final Collider collider;
    @FieldChild
    public final Area2D area2D;

    /* ================ [ FIELDS ] ================ */

    // Body velocity
    @SyncedObject
    private Vector2 velocity = Vector2.ZERO;

    // Constructors
    public Body(Collider collider, Area2D area2D, Node<?>... children) { this(new Transform(), collider, area2D, children); }
    public Body(Transform transform, Collider collider, Area2D area2D, Node<?>... children) {
        super(transform, children);
        this.collider = collider;
        this.area2D = area2D;

        this.area2D.bodyEnteredSignal.connect(this::onBodyEntered);
    }

    {
        PhysicsEngine.bodies.add(this);
    }

    /* ================ [ METHODS ] ================ */

    // Set velocity
    public void setVelocity(Vector2 vel) { this.velocity = vel; }

    // Get velocity
    public Vector2 getVelocity() { return velocity; }

    /* ================ [ NODE ] ================ */

    @Override
    public void update(Transform offset, double delta) {
        addPosition(velocity.mul(delta));

        super.update(offset, delta);
    }

    public abstract void onBodyEntered(BodyEntered signal);

}
