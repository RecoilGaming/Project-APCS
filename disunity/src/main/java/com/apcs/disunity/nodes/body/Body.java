package com.apcs.disunity.nodes.body;

import com.apcs.disunity.annotations.Requires;
import com.apcs.disunity.annotations.syncedfield.SyncedObject;
import com.apcs.disunity.applyable.TransformApplyable;
import com.apcs.disunity.applyable.VelocityApplyable;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.Node2D;
import com.apcs.disunity.nodes.controller.Controllable;
import com.apcs.disunity.nodes.controller.Controller;

/**
 * A 2d node that can handle movement and collision
 * 
 * @author Qinzhao Li
 */
@Requires(nodes = {Controller.class})
public class Body extends Node2D {

    /* ================ [ FIELDS ] ================ */

    /** The id of the controller */
    private int controller;

    /** The velocity of the body */
    @SyncedObject
    private Vector2 vel = Vector2.ZERO;

    /** Create a new Body */
    public Body() { super(); }
    /**
     * Create a new Body with the given children
     *
     * @param children The children of this node
     */
    public Body(Node... children) { super(children); }
    /**
     * Create a new Body with the given transform and children
     *
     * @param transform The transform of this node
     * @param children The children of this node
     */
    public Body(Transform transform, Node... children) { super(transform, children); }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the velocity of the body
     * 
     * @return The velocity of the body
     */
    public Vector2 getVel() { return vel; }

    /**
     * Set the velocity of the body
     * 
     * @param vel The velocity of the body
     */
    public void setVel(Vector2 vel) { this.vel = vel; }

    /* ================ [ NODE ] ================ */

    /** Initialize the node */
    @Override
    public void initialize() {
        // Grab controller id
        this.controller = getChild(Controller.class).getId();

        // Update children controllables
        for (Controllable action : getChildren(Controllable.class)) {
            action.setController(controller);
        }

        // Complete initialization
        super.initialize();
    }

    /**
     * Update the node and its children
     * 
     * @param offset The offset of the node
     * @param delta The time since the last update
     */
    @Override
    public void update(Transform offset, double delta) {
        // Apply velocity nodes
        for (VelocityApplyable va : getChildren(VelocityApplyable.class)) {
            vel = va.apply(vel, delta);
        }

        // Apply transform nodes
        for (TransformApplyable ta : getChildren(TransformApplyable.class)) {
            transform = ta.apply(transform, delta);
        }

        // Move with velocity
        transform = transform.move(vel.mul(delta));

        // Update children
        super.update(offset, delta);
    }

}
