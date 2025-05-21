package com.apcs.disunity.app.input;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.apcs.disunity.app.input.actions.Action;
import com.apcs.disunity.app.input.actions.ActionSet;
import com.apcs.disunity.app.rendering.ScalableBuffer;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.math.Vector2;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A manager that handles all inputs and their actions.
 * 
 * @author Qinzhao Li
 */
public class Inputs {

    /* ================ [ FIELDS ] ================ */

    // Maps actions to their respective bindings
    private static Map<String, ActionSet> actions = new HashMap<>();

    // Maps inputs to their pressed state
    private static final Set<Input> inputs = new HashSet<>();

    // Mouse position on the screen
    static Vector2 rawMousePos = Vector2.of(-1);
    static Vector2 lastMousePos = Vector2.ZERO;

    /* ================ [ METHODS ] ================ */

    public static Vector2 adjustForGame(Vector2 pos) {
        ScalableBuffer buffer = Game.getInstance().getBuffer();
        Rectangle bounds = Game.getInstance().getBounds();
        Vector2 viewDim = Vector2.of(bounds.width, bounds.height);
        Vector2 newPos = pos.sub(viewDim.div(2)).div(buffer.getScale()).sub(Game.getInstance().getTransform().pos);
        return newPos;
    }

    // getter for mouse pos
    public static Vector2 getMousePos() {
        lastMousePos = adjustForGame(rawMousePos);
        return lastMousePos;
    }

    // getter for mouse vel
    public static Vector2 getMouseVel() {
        Vector2 last = lastMousePos;
        Vector2 pos = getMousePos();
        return pos.sub(last);
    }
 
    // Press an input
    public static void press(Input input) { inputs.add(input); }

    // Release an input
    public static void release(Input input) { inputs.remove(input); }

    // Release all keys
    public static void releaseAll() { inputs.clear(); }

    // Get if input is pressed
    public static boolean get(Input input) { return inputs.contains(input); }

    // Add an action to the map
    public static void addAction(String name, ActionSet action) { actions.put(name, action); }

    // Get if an action is pressed
    public static boolean getAction(String name) {
        for (Action action : actions.get(name).getActions()) {
            boolean pressed = true;
            for (Input input : action.getInputs()) {
                pressed = pressed && get(input);
            }

            if (pressed) {
                return true;
            }
        }
        return false;
    }

    /* ================ [ LOADER ] ================ */

    // Load from a JSON file
    public static void fromJSON(String path) {
        try {
            InputStream file = Inputs.class.getClassLoader().getResourceAsStream(path);
            ObjectMapper om = new ObjectMapper();
            actions = om.readValue(file, new TypeReference<Map<String, ActionSet>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
