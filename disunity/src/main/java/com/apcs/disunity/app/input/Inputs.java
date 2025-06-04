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

    // Maps action names to their respective bindings
    private static Map<String, ActionSet> actions = new HashMap<>();

    // Tracks which inputs are currently pressed
    private static final Set<Input> inputs = new HashSet<>();

    private static final Map<String, Integer> cleared = new HashMap<>();

    // Global mouse position on the screen
    static Vector2 rawMousePos = Vector2.of(-1);

    // Local mouse position in the world
    static Vector2 mousePos = Vector2.ZERO;

    /* ================ [ ACTIONS ] ================ */

    // Registers an input as pressed
    public static void press(Input input) { inputs.add(input); }

    // Registers an input as released
    public static void release(Input input) { inputs.remove(input); }

    // Releases all currently pressed inputs
    public static void releaseAll() { inputs.clear(); }

    // Returns true if the given input is currently pressed
    public static boolean get(Input input) { return inputs.contains(input); }

    // Adds an action with its associated set of bindings
    public static void addAction(String name, ActionSet action) { actions.put(name, action); }

    // Returns true if the specified action is currently triggered
    public static boolean getAction(String name) {
        if (cleared.getOrDefault(name, 0) > 0) return false;

        ActionSet set = actions.get(name);
        if (set == null)
            return false;

        for (Action action : set.getActions()) {
            boolean allInputsPressed = true;
            for (Input input : action.getInputs()) {
                if (!get(input)) {
                    allInputsPressed = false;
                    break;
                }
            }
            if (allInputsPressed)
                return true;
        }

        return false;
    }

    // Clear an action for the frame
    public static void clearAction(String name) {
        cleared.put(name, 5);
    }

    public static void update() {
        cleared.replaceAll((key, val) -> val - 1);
		cleared.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }

    /* ================ [ MOUSE ] ================ */

    // Converts screen position to world position
    private static Vector2 toLocal(Vector2 pos) {
        Game game = Game.getInstance();
        ScalableBuffer buffer = game.getBuffer();
        Rectangle bounds = game.getBounds();

        Vector2 viewportCenter = Vector2.of(bounds.width, bounds.height).div(2);
        Vector2 scaledPos = pos.sub(viewportCenter).div(buffer.getScale());

        return scaledPos.sub(game.getTransform().pos);
    }

    // Returns the local mouse position
    public static Vector2 getMousePos() {
        mousePos = toLocal(rawMousePos);
        return mousePos;
    }

    // Returns the current mouse movement delta
    public static Vector2 getMouseVel() { return mousePos.sub(getMousePos()); }

    /* ================ [ LOADER ] ================ */

    // Loads input mappings from a JSON file
    public static void fromJSON(String path) {
        try (InputStream file = Inputs.class.getClassLoader().getResourceAsStream(path)) {
            ObjectMapper mapper = new ObjectMapper();
            actions = mapper.readValue(file, new TypeReference<Map<String, ActionSet>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
