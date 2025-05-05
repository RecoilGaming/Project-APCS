package com.apcs.disunity.nodes.action;

import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.signals.Signals;

/**
 * A node that performs an action
 * 
 * @author Qinzhao Li
 */
public abstract class GameAction<T> extends Node {

    /* ================ [ FIELDS ] ================ */

    /** The id of the source node */
    private int source = -1;

    /** Create a new GameAction */
    public GameAction() { super(); }
    /**
     * Create a new GameAction with the given children
     *
     * @param children The children of this node
     */
    public GameAction(Node... children) { super(children); }
    
    /* ================ [ METHODS ] ================ */

    /**
     * Set the source id
     *
     * @param source The source id
     */
    public void setSource(int source) { this.source = source; }

    /* ================ [ NODE ] ================ */

    /** Initialize the node */
    @Override
    public void initialize() {
        // Connect to signal
        Signals.connect(Signals.getSignal(source, this.actionId()), this::trigger);

        // Complete initialization
        super.initialize();
    }

    /* ================ [ ACTION ] ================ */

    /**
     * Get the action id
     *
     * @return The action id
     */
    public abstract String actionId();

    /**
     * Trigger the action
     *
     * @param data The data to trigger the action with
     */
    public abstract void trigger(T data);

}
