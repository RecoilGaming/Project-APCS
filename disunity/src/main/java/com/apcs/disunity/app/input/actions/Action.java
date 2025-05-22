package com.apcs.disunity.app.input.actions;

import com.apcs.disunity.app.input.Input;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A flag that is true only when all the inputs are present (AND).
 *
 * @author Qinzhao Li
 */
public class Action {

    /* ================ [ FIELDS ] ================ */

    // Inputs
    private final Input[] inputs;

    // Constructors
    @JsonCreator
    public Action(Input... inputs) { this.inputs = inputs; }

    /* ================ [ METHODS ] ================ */

    // Get inputs
    @JsonValue
    public Input[] getInputs() { return inputs; }

}
