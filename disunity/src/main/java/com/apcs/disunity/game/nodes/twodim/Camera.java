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

    private static Camera instance;

    public static Camera getActiveCamera() {
        return instance;
    }

    {
        instance = this;
    }

    

    /* ================ [ FIELDS ] ================ */

    private Transform lastLoc = new Transform();

    private Transform getLastGlobalLocation() { return lastLoc; }

    // Constructors
    public Camera(Node<?>... children) { super(children); }

    public Camera(Transform transform, Node<?>... children) { super(transform, children); }

    /* ================ [ NODE ] ================ */

    @Override
    public void update(double delta, Transform t) {
        lastLoc = t;
        // Update global transform
        // TODO: need to get global transform
        // currently sets to local transform, so camera does not follow the player
        Game.getInstance().setTransform(new Transform(t.pos.mul(-1), t.scale, 0).apply(getTransform()));

        // Update children
        super.update(delta, t);
    }

    public void setActive() {
        instance = this;
    }

}
