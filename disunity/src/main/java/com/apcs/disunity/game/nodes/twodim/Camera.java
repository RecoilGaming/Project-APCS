package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

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

    // Constructors
    public Camera(Node<?>... children) { super(children); }
    public Camera(Transform transform, Node<?>... children) { super(transform, children); }

    /* ================ [ METHODS ] ================ */

    // Update the current active camera
    public void setActive() { active = this; }

    /* ================ [ NODE ] ================ */

    @Override
    public void update(double delta) {
        // Update global transform
        Game.getInstance().setTransform(getGlobalTrans().rotateTo(0).scaleTo(Vector2.ONE));
    }

}
