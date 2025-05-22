package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;

/**
 * A camera to control the viewport
 * 
 * @author Qinzhao Li
 */
public class Camera extends Node2D<Node<?>> {

    /* ================ [ CONSTANTS ] ================ */

    // Active camera instance
    private static Camera active;

    // Get active camera 
    public static Camera getActive() { return active; }

    /* ================ [ FIELDS ] ================ */

    {
        active = this;
    }

    // Previous transform
    private Transform prevT = new Transform();

    // Constructors
    public Camera(Node<?>... children) { super(children); }
    public Camera(Transform transform, Node<?>... children) { super(transform, children); }

    /* ================ [ METHODS ] ================ */

    // Update the current active camera
    public void setActive() { active = this; }

    /* ================ [ NODE ] ================ */

    @Override
    public void update(Transform offset, double delta) {
        prevT = offset;

        // Update global transform
        Game.getInstance().setTransform(new Transform(offset.pos.mul(-1), offset.scale, 0).apply(getTransform()));

        // Update children
        super.draw(offset);
    }

}
