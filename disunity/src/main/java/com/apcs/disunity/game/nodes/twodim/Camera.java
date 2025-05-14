package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.game.Game;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.game.nodes.Node;

/**
 * A camera to control the viewport
 * 
 * @author Qinzhao Li
 */
public class Camera extends Node2D<Node<?>> {

    /* ================ [ FIELDS ] ================ */

    // Constructors
    public Camera(Node<?>... children) { super(children); }

    public Camera(Transform transform, Node<?>... children) { super(transform, children); }

    /* ================ [ NODE ] ================ */

    @Override
    public void update(double delta) {
        // Update global transform
        // TODO: need to get global transform
        // currently sets to local transform, so camera does not follow the player
        Game.getInstance().setTransform(getTransform());

        // Update children
        super.update(delta);
    }

}
