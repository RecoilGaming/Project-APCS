package com.apcs.disunity.nodes.action;

import com.apcs.disunity.applyable.VelocityApplyable;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.controller.Controllable;

/**
 * A node that performs a movement action
 * 
 * @author Qinzhao Li
 */
public abstract class MoveAction<T> extends GameAction<T> implements VelocityApplyable, Controllable {

    /* ================ [ FIELDS ] ================ */

    /** Create a new MoveAction */
    public MoveAction() { super(); }
    /**
     * Create a new MoveAction with the given children
     *
     * @param children The children of this node
     */
    public MoveAction(Node... children) { super(children); }

    /* ================ [ CONTROLLABLE ] ================ */

    /**
     * Set the controller id
     *
     * @param controller The controller id
     */
    public void setController(int controller) { setSource(controller); }
    
}
