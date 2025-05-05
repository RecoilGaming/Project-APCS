package com.apcs.disunity.physics;

import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.Node;

/**
 * Detects collisions within a bounding box (AABB)
 * 
 * @author Qinzhao Li
 */
public class Area extends Node {

    /* ================ [ FIELDS ] ================ */

    /** The global area count */
    private static int areas = 0;
    /** The id of the area */
    private int id;

    /** The size of the area */
    private Vector2 size;

    /** The collision layer of the area */
    private int layer = 0;

    /**
     * Creates a new Area with the given size
     * 
     * @param size The size of the area
     */
    public Area(Vector2 size) {
        this.size = size;
        this.id = areas++;

        // Register with physics manager
        // Physics.registerArea(this);
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the id of the area
     * 
     * @return The id of the area
     */
    public int getId() {
        return id;
    }

    /**
     * Get the collision layer of the area
     * 
     * @return The collision layer of the area
     */
    public int getLayer() { return layer; }

    /**
     * Set the collision layer of the area
     * 
     * @param layer The collision layer of the area
     */
    public void setLayer(int layer) { this.layer = layer; }

    /**
     * Get the size of the area
     * 
     * @return The size of the area
     */
    public Vector2 getSize() {
        return size;
    }

}
