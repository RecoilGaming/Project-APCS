package com.apcs.ljaag.nodes.action;

import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.action.MoveAction;

/**
 * Allows a node to move in 2d space
 *
 * @author Qinzhao Li
 */
public class WalkAction extends MoveAction<Vector2> {
    
    /* ================ [ ATTRIBUTES ] ================ */

    /** The speed of movement */
    private double speed = 100;

    /* ================ [ FIELDS ] ================ */

    /** The direction of movement */
    private Vector2 dir = Vector2.ZERO;

    /** Creates a new WalkAction */
    public WalkAction() { super(); }

    /**
     * Creates a new WalkAction with the given children
     * 
     * @param children The children of this node
     */
    public WalkAction(Node... children) { super(children); }

    /* ================ [ APPLIABLE ] ================ */

    /**
     * Applies the movement to the given velocity
     * 
     * @param vel The original velocity
     * @param delta The time since the last update
     * @return The new velocity
     */
    @Override
    public Vector2 apply(Vector2 vel, double delta) { return dir.mul(speed); }

    /* ================ [ ACTION ] ================ */

    /**
     * Get the action ID for a WalkAction
     * 
     * @return The action ID
     */
    @Override
    public String actionId() { return "walk"; }
    
    /**
     * Trigger the action with the direction of movement
     * 
     * @param data The direction of movement
     */
    @Override
    public void trigger(Vector2 data) { this.dir = data; }

}
