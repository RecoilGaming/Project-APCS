package com.apcs.disunity.physics;

import com.apcs.disunity.math.Rectangle;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.Node2D;
import com.apcs.disunity.signals.Signals;

/**
 * Detects collisions within a bounding box (AABB)
 * 
 * @author Qinzhao Li
 */
public class Area extends Node2D {

    /* ================ [ FIELDS ] ================ */

    /** The global area count */
    private static int areas = 0;
    /** The id of the area */
    private int id;

    /** The size of the area */
    private Vector2 size;

    /** The bounds of the area in the previous frame */
    private Rectangle prevBounds;
    /** The bounds of the area */
    private Rectangle bounds;

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

        this.bounds = new Rectangle(
            transform.pos.x - size.x / 2, transform.pos.x + size.x / 2,
            transform.pos.y - size.y / 2, transform.pos.y + size.y / 2
        );
        this.prevBounds = this.bounds;

        Physics.registerArea(this);
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

    /**
     * Get the bounds of the area
     * 
     * @return The bounds of the area
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Get the bounds of the area in the previous frame
     * 
     * @return The bounds of the area in the previous frame
     */
    public Rectangle getPrevBounds() {
        return prevBounds;
    }

    /* ================ [ NODE ] ================ */
    
    /** Initialize the node */
    @Override
    public void initialize() {
        // Complete initialization
        super.initialize();
    }
    
    /**
     * Update the node and its children
     * 
     * @param offset The offset of the node
     * @param delta The time since the last update
     */
    @Override
    public void update(Transform offset, double delta) {
        Vector2 pos = transform.apply(offset).pos;

        // Update previous bounds
        this.prevBounds = this.bounds;

        // Update current bounds
        this.bounds = new Rectangle(
            pos.x - size.x / 2, pos.x + size.x / 2,
            pos.y - size.y / 2, pos.y + size.y / 2
        );

        super.update(offset, delta);
    }

}
