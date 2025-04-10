package com.apcs.ljaag.nodes.moveaction;

import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.UndrawnNode;
import com.apcs.disunity.nodes.moveaction.MoveAction;

/**
 * A move action that allows directional movement.
 *
 * @author Qinzhao Li
 */
public class WalkAction extends MoveAction<Vector2> {

    /* ================ [ ATTRIBUTES ] ================ */

    // Speed
    private double speed = 100;

    /* ================ [ FIELDS ] ================ */

    // Action id
    protected static final String actionId = "walk";

    // Walk direction
    private Vector2 dir = Vector2.ZERO;

    // Constructors
    public WalkAction() { super(); }
    public WalkAction(UndrawnNode... children) { super(children); }

    /* ================ [ MOVEACTION ] ================ */
    
    @Override
    public void trigger(Vector2 data) { this.dir = data; }

    @Override
    public Vector2 apply(Vector2 vel, double delta) { return dir.mul(speed); }

}
