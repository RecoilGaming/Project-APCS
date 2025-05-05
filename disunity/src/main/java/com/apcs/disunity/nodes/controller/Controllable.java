package com.apcs.disunity.nodes.controller;

/**
 * A node that can be controlled by a controller
 * 
 * @author Qinzhao Li
 */
public interface Controllable {

    /* ================ [ METHODS ] ================ */

    /**
     * Set the controller id
     * 
     * @param controller The controller id
     */
    public void setController(int controller);

}
