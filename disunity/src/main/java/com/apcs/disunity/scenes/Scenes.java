package com.apcs.disunity.scenes;

import java.util.HashMap;
import java.util.Map;

import com.apcs.disunity.math.Transform;
import com.apcs.disunity.nodes.Node;

/**
 * Manages the scenes in the game
 * 
 * @author Qinzhao Li
 */
public class Scenes {

    /* ================ [ FIELDS ] ================ */

    // Current scene id
    private static String scene = "";

    // Maps scene ids to scenes
    private static final Map<String, Node> scenes = new HashMap<>();

    /* ================ [ METHODS ] ================ */

    // Add a scene
    public static void addScene(String name, Node scene) { scenes.put(name, scene); }

    // Set current scene
    public static void setScene(String scene) { Scenes.scene = scene; }
    
    // Get current scene
    public static Node getScene() { return scenes.get(scene); }

    // Update current scene
    public static void updateScene(Transform offset, double delta) { getScene().tick(offset, delta); }

    // Draw current scene
    public static void drawScene(Transform offset) { getScene().draw(offset); }

}
