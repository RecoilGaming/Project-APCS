package com.apcs.disunity.nodes.action;

import com.apcs.disunity.nodes.UndrawnNode;
import com.apcs.disunity.signals.Signals;

// TODO: Documentation

/**
 * A node that performs an action
 * 
 * @author Qinzhao Li
 */
public abstract class GameAction<T> extends UndrawnNode {

    /* ================ [ FIELDS ] ================ */

    // Source id
    private int source = -1;

    // Constructors
    public GameAction() { super(); }
    public GameAction(UndrawnNode... children) { super(children); }
    
    /* ================ [ METHODS ] ================ */

    // Set source id
    public void setSource(int source) { this.source = source; }

    /* ================ [ NODE ] ================ */

    @Override
    public void initialize() {
        // Connect to signal
        Signals.connect(Signals.getSignal(source, this.actionId()), this::trigger);

        // Complete initialization
        super.initialize();
    }

    /* ================ [ ACTION ] ================ */

    // Get action id
    public abstract String actionId();

    // Trigger the action
    public abstract void trigger(T data);

}
