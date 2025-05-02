package com.apcs.disunity.nodes.action;

import com.apcs.disunity.applyable.VelocityApplyable;
import com.apcs.disunity.nodes.UndrawnNode;
import com.apcs.disunity.nodes.controller.Controllable;

/**
 * A node that performs a movement action
 * 
 * @author Qinzhao Li
 */
public abstract class MoveAction<T> extends GameAction<T> implements VelocityApplyable, Controllable {

    /* ================ [ FIELDS ] ================ */

    // Constructors
    public MoveAction() { super(); }
    public MoveAction(UndrawnNode... children) { super(children); }

    /* ================ [ CONTROLLABLE ] ================ */

    // Set controller id
    public void setController(int controller) { setSource(controller); }
    
}
