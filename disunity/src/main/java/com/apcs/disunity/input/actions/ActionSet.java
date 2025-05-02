package com.apcs.disunity.input.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An action that can be triggered by other sub-actions (OR)
 * 
 * @author Qinzhao Li
 */
public class ActionSet {

    /* ================ [ FIELDS ] ================ */

    /** The sub-actions that trigger this action */
    private final Action[] subactions;

    /**
     * Creates a new ActionSet with the given sub-actions
     *
     * @param actions The sub-actions that trigger this action
     */
    @JsonCreator
    public ActionSet(Action... subactions) { this.subactions = subactions; }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the sub-actions that trigger this action
     *
     * @return The sub-actions that trigger this action
     */
    @JsonValue
    public Action[] getActions() { return subactions; }
    
}
