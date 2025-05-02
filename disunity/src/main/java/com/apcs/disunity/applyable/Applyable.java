package com.apcs.disunity.applyable;

/**
 * A class that modifies and returns a value of a specified type
 * 
 * @author Qinzhao Li
 */
public interface Applyable<T> {

    /* ================ [ METHODS ] ================ */

    /**
     * Apply the appliable to a value
     * 
     * @param original The original value
     * @param delta The time since the last update
     * @return The new value
     */
    public T apply(T original, double delta);

}
