package com.apcs.disunity.nodes;

import com.apcs.disunity.annotations.syncedfield.SyncedObject;
import com.apcs.disunity.math.Transform;

/**
 * The base class for a 2d node with a transform
 * 
 * @author Qinzhao Li
 */
public class Node2D extends Node {

    /* ================ [ FIELDS ] ================ */
    
    /** The transform of the node */
    @SyncedObject
    public Transform transform = new Transform();

    /** Create a new Node2D */
    public Node2D() { super(); }
    /**
     * Create a new Node2D with the given children
     * 
     * @param children The children of this node
     */
    public Node2D(Node... children) { super(children); }
    /**
     * Create a new Node2D with the given transform and children
     * 
     * @param transform The transform of this node
     * @param children The children of this node
     */
    public Node2D(Transform transform, Node... children) { super(children); this.transform = transform; }
    /**
     * Create a new Node2D with the given transform, visibility, and children
     *
     * @param transform The transform of this node
     * @param visible Whether or not the node is visible
     * @param children The children of this node
     */
    public Node2D(Transform transform, boolean visible, Node... children) { super(visible, children); this.transform = transform; }
    
    /* ================ [ NODE ] ================ */

    /**
     * Update the node and its children
     * 
     * @param offset The offset of the node
     * @param delta The time since the last update
     */
    @Override
    public void update(Transform offset, double delta) {
        super.update(transform.apply(offset), delta);
    }

    /**
     * Draw the node and its children
     * 
     * @param offset The offset of the node
     */
    @Override
    public void draw(Transform offset) {
        super.draw(transform.apply(offset));
    }

}
