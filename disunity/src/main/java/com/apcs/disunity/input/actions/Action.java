package com.apcs.disunity.input.actions;

import com.apcs.disunity.input.Input;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An action that can be triggered by a set of inputs (AND)
 * 
 * @author Qinzhao Li
 */
public class Action {

    /* ================ [ FIELDS ] ================ */

    /** The inputs required to trigger this action */
    private final Input[] inputs;

    /**
     * Creates a new Action with the given inputs
     *
     * @param inputs The inputs required to trigger this action
     */
    @JsonCreator
    public Action(Input... inputs) {
        this.inputs = inputs;
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the inputs required to trigger this action
     * 
     * @return The inputs required to trigger this action
     */
    @JsonValue
    public Input[] getInputs() { return inputs; }
    
}
