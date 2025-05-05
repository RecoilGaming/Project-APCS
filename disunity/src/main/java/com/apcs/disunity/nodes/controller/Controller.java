package com.apcs.disunity.nodes.controller;

import com.apcs.disunity.nodes.Node;

/**
 * Controls a body node
 * 
 * @author Qinzhao Li
 */
public abstract class Controller extends Node {

    /* ================ [ FIELDS ] ================ */

    /** The global controller count */
    private static int controllers = 0;

    /** The id of the controller */
    private int id;

    /** Create a new Controller */
    public Controller() { super(); this.id = controllers++; }
    /**
     * Create a new Controller with the given children
     *
     * @param children The children of this node
     */
    public Controller(Node... children) { super(children); }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the id of the controller
     *
     * @return The id of the controller
     */
    public int getId() { return id; }

}
