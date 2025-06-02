package com.apcs.disunity.game.nodes.twodim;

import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * A base class for 2D nodes with position.
 * 
 * @author Qinzhao Li
 */
public class Node2D<T extends Node<?>> extends Node<T> {

    /* ================ [ FIELDS ] ================ */

    // transform relative to parent's coordinate system
    @SyncedObject
    private Transform localTrans;
    // global transform of parent
    private Transform parentGT = new Transform();

    // Constructors
    public Node2D(T... children) {
        this(new Transform(), children);
    }

    public Node2D(Transform localTrans, T... children) {
        super(children);
        this.localTrans = localTrans;
    }

    @Override
    public void setParent(Node<?> parent) {
        super.setParent(parent);
        if(parent instanceof Node2D<?> n2 && n2.parentGT != null) {
            parentGT = n2.parentGT.apply(n2.localTrans);
            propagateTransformChange();
        }
    }

    /* ================ [ METHODS ] ================ */

    protected void propagateTransformChange() {
        for(Node child: getAllChildren()) {
            if(child instanceof Node2D<?> n2) {
                n2.parentGT = this.parentGT.apply(localTrans);
            }
        }
    }

    // Move position
    public void addPosition(Vector2 vel) {
        localTrans = localTrans.addPos(vel);
        propagateTransformChange();
    }

    // Setters
    public void setPosition(Vector2 pos) {
        localTrans = new Transform(pos, localTrans.scale, localTrans.rot);
        propagateTransformChange();
    }

    public void setScale(Vector2 scale) {
        localTrans = new Transform(localTrans.pos, scale, localTrans.rot);
        propagateTransformChange();
    }

    public void setRotation(double rot) {
        localTrans = new Transform(localTrans.pos, localTrans.scale, rot % (2 * Math.PI));
        propagateTransformChange();
    }


    // Getters
    public Vector2 getPosition() {
        return localTrans.pos;
    }

    public Vector2 getScale() {
        return localTrans.scale;
    }

    public double getRotation() {
        return localTrans.rot;
    }

    public Transform getTransform() {
        return localTrans;
    }

    public Transform getGlobalTrans() {
        return parentGT.apply(localTrans);
    }

    public Transform getParentGT() {
        return parentGT;
    }

    /* ================ [ NODE ] ================ */

    @Override
    public void draw(Transform offset) {
        // Draw children relative to this
        super.draw(offset.apply(localTrans));
    }
}