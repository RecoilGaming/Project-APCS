package com.apcs.disunity.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.apcs.disunity.input.actions.Action;
import com.apcs.disunity.input.actions.ActionSet;
import com.apcs.disunity.resources.Resources;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * Handles all player inputs recieved
 * 
 * @author Qinzhao Li
 */
public class Inputs {

    /* ================ [ FIELDS ] ================ */

    /** Maps action names to their action sets */
    private static Map<String, ActionSet> actions = new HashMap<>();

    /** Lists all inputs that are currently pressed */
    private static final Set<Input> inputs = new HashSet<>();

    /** The mouse X position on the screen */
    public static int mouseX = 0;
    /** The mouse Y position on the screen */
    public static int mouseY = 0;

    /* ================ [ METHODS ] ================ */

    /**
     * Set an input state to pressed
     * 
     * @param input The input
     */
    public static void press(Input input) { inputs.add(input); }

    /**
     * Set an input state to released
     * 
     * @param input The input     
     */
    public static void release(Input input) { inputs.remove(input); }

    /** Release all inputs */
    public static void releaseAll() { inputs.clear(); }

    /**
     * Get if an input is pressed
     * 
     * @param input The input
     * @return Whether or not the input is pressed
     */
    public static boolean get(Input input) { return inputs.contains(input); }

    /**
     * Add an action to the map
     * 
     * @param name The name of the action
     * @param action The action set
     */
    public static void addAction(String name, ActionSet action) { actions.put(name, action); }

    /**
     * Get if an action is triggered
     * 
     * @param name The name of the action
     * @return Whether or not the action is triggered
     */
    public static boolean getAction(String name) {
        for (Action action : actions.get(name).getActions()) {
            boolean pressed = true;
            
            // Check if all required inputs are pressed
            for (Input input : action.getInputs()) {
                pressed = pressed && get(input);
            }
            
            if (pressed) {
                return true;
            }
        } return false;
    }

    /* ================ [ LOADER ] ================ */

    /**
     * Load actions from a JSON file
     *
     * @param path The path to the JSON file
     */
    public static void fromJSON(String path) {
        try {
            // Load file as input stream
            InputStream file = Resources.loadFileAsInputStream(path);

            // Read the JSON file
            ObjectMapper om = new ObjectMapper();
            actions = om.readValue(file, new TypeReference<Map<String, ActionSet>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
