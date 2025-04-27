package com.apcs.disunity.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import com.apcs.disunity.input.actions.Action;
import com.apcs.disunity.input.actions.ActionSet;
import static com.apcs.disunity.resources.Resources.loadFileAsInputStream;

import static com.apcs.disunity.server.CODEC.*;
import com.apcs.disunity.server.SelfCodec;
import com.apcs.disunity.server.SyncHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles all player inputs recieved
 *
 * @author Qinzhao Li
 */
public class Inputs implements SelfCodec<Inputs> {

    /* ================ [ FIELDS ] ================ */

    // list of client inputs in game
    private static final LinkedList<Inputs> instances = new LinkedList<>();

    // Maps actions to their respective bindings
    private Map<String, ActionSet> actions = new HashMap<>();

    // Maps inputs to their pressed state
    private final Set<Input> inputs = new HashSet<>();

    // Mouse position on the screen
    public int mouseX = 0, mouseY = 0;

    /* ================ [ METHODS ] ================ */

    // get an inputs bound to given endpoint
    public static Inputs getInstance(int endpointId) {
        return instances.get(endpointId);
    }
    // get an instance bound to current runtime
    // needs to have sync handler instantiated.
    public static Inputs runtimeInstance() {
        return instances.get(SyncHandler.getInstance().getEndpointId());
    }

    // Press an input
    public void press(Input input) { inputs.add(input); }

    // Release an input
    public void release(Input input) { inputs.remove(input); }
    // Release all keys
    public void releaseAll() { inputs.clear(); }

    // Get if input is pressed
    public boolean get(Input input) { return inputs.contains(input); }

    // Add an action to the map
    public void addAction(String name, ActionSet action) { actions.put(name, action); }

    // Get if an action is pressed
    public boolean getAction(String name) {
        for (Action action : actions.get(name).getActions()) {
            boolean pressed = true;
            for (Input input : action.getInputs())
                pressed = pressed && get(input);

            if (pressed) return true;
        } return false;
    }

    /* ================ [ LOADER ] ================ */

    // loads a json file to create an instance of inputs,
    // and adds it to the registry
    public static void fromJSON(String path, int endpointId) {
        if(endpointId == 0) throw new RuntimeException("server cannot make an instance of input.");
        try {
            InputStream file = loadFileAsInputStream(path);
            ObjectMapper om = new ObjectMapper();
            Inputs inputs = new Inputs();
            inputs.actions = om.readValue(file, new TypeReference<>() {
            });

            for(int i=instances.size(); i<=endpointId; i++) {
                instances.add(null);
            }
            instances.set(endpointId, inputs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* ============== [ CODEC ] =============== */

    @Override
    public Inputs decode(InputStream in) {
        int size = decodeInt(in);
        inputs.clear();
        for(int i=0; i<size; i++) {
            inputs.add(Input.values()[decodeByte(in)]);
        }
        return this;
    }

    @Override
    public void encode(OutputStream out) {
        encodeInt(inputs.size(), out);
        for(Input input: inputs) {
            encodeByte((byte) input.ordinal(), out);
        }
    }

}
