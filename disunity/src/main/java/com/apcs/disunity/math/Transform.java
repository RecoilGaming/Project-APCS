package com.apcs.disunity.math;

import com.apcs.disunity.annotations.syncedfield.SyncedDouble;
import com.apcs.disunity.annotations.syncedfield.SyncedObject;

/**
 * Stores position, scale, and rotation information
 * IMMUTABLE
 * 
 * @author Qinzhao Li
 */
public class Transform {

    /* ================ [ CONSTANTS ] ================ */

    /** The identity transform */
    public static final Transform IDENTITY = new Transform();

    /* ================ [ FIELDS ] ================ */

    /** The position of the transform */
    @SyncedObject
    public final Vector2 pos;

    /** The scale of the transform */
    @SyncedObject
    public final Vector2 scale;

    /** The rotation of the transform (degrees) */
    @SyncedDouble
    public final double rot;

    /** Creates a new Transform */
    public Transform() { this(Vector2.ZERO, Vector2.UNIT, 0); }
    /**
     * Creates a new Transform with the given position, scale, and rotation
     * 
     * @param pos The position of the transform
     * @param scale The scale of the transform
     * @param rot The rotation of the transform
     */
    public Transform(Vector2 pos, Vector2 scale, double rot) {
        this.pos = pos;
        this.scale = scale;
        this.rot = rot % 360;
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Set the position of the transform
     * 
     * @param pos The new position of the transform
     * @return The new transform
     */
    public Transform setPos(Vector2 pos) { return new Transform(pos, scale, rot); }

    /**
     * Set the scale of the transform
     * 
     * @param scale The new scale of the transform
     * @return The new transform
     */
    public Transform setScale(Vector2 scale) { return new Transform(pos, scale, rot); }

    /**
     * Set the rotation of the transform
     * 
     * @param rot The new rotation of the transform
     * @return The new transform
     */
    public Transform setRot(double rot) { return new Transform(pos, scale, rot); }

    /**
     * Translate the transform by a vector
     * 
     * @param amt The amount to translate
     * @return The new transform
     */
    public Transform move(Vector2 amt) { return new Transform(pos.add(amt), scale, rot); }

    /**
     * Scale the transform by a scalar
     * 
     * @param amt The amount to scale
     * @return The new transform
     */
    public Transform scale(double amt) { return new Transform(pos, scale.mul(amt), rot); }

    /**
     * Scale the transform by a vector
     * 
     * @param amt The amount to scale
     * @return The new transform
     */
    public Transform scale(Vector2 amt) { return new Transform(pos, scale.mul(amt), rot); }

    /**
     * Rotate the transform by a scalar
     * 
     * @param amt The amount to rotate
     * @return The new transform
     */
    public Transform rotate(double amt) { return new Transform(pos, scale, (rot + amt) % 360); }

    /**
     * Apply one transform to another
     * 
     * @param t The other transform
     * @return The new transform
     */
    public Transform apply(Transform t) { return new Transform(pos.add(t.pos), scale.mul(t.scale), (rot + t.rot) % 360); }

    /* ================ [ OBJECT ] ================ */

    /**
     * Convert the transform to a string
     * 
     * @return The transform as a string
     */
    @Override
    public String toString() { return "pos: " + pos + ", scale: " + scale + ", rot: " + rot; }

}
