package com.apcs.disunity.app.input.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A flag that is true when any of its actions are true (OR).
 * 
 * @author Qinzhao Li
 */
public class ActionSet {

    /* ================ [ FIELDS ] ================ */

    // Actions
    private final Action[] actions;

    // Constructors
    @JsonCreator
    public ActionSet(Action... actions) { this.actions = actions; }

    /* ================ [ METHODS ] ================ */

    // Get actions
    @JsonValue
    public Action[] getActions() { return actions; }

}
