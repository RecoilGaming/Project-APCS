package com.apcs.disunity.math;

import com.apcs.disunity.server.SelfCodec;

import java.io.InputStream;
import java.io.OutputStream;

import static com.apcs.disunity.server.CODEC.*;

/**
 * Stores x and y components of a vector
 * IMMUTABLE
 * 
 * @author Qinzhao Li
 * @author Sharvil Phadke
 */
public class Vector2 implements SelfCodec<Vector2> {

    /* ================ [ CONSTANTS ] ================ */

    /** The zero vector */
    public static final Vector2 ZERO = new Vector2();

    /** The unit vector */
    public static final Vector2 UNIT = new Vector2(1, 1);

    /* ================ [ FIELDS ] ================ */

    /** The x component */
    public final double x;
    /** The y component */
    public final double y;

    /** The x component rounded to an integer */
    public final int xi;
    /** The y component rounded to an integer */
    public final int yi;

    /** Creates a new Vector2 */
    public Vector2() { this(0, 0); }

    /**
     * Creates a new Vector2 with the given components
     *
     * @param x The x component
     * @param y The y component
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
        this.xi = (int) Math.round(x);
        this.yi = (int) Math.round(y);
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Create a square Vector2 with the given size
     *
     * @param x The size of the square
     * @return The square Vector2
     */
    public static Vector2 of(double x) { return new Vector2(x, x); }

    /**
     * Create a Vector2 with the given components
     *
     * @param x The x component
     * @param y The y component
     * @return The Vector2
     */
    public static Vector2 of(double x, double y) { return new Vector2(x, y); }

    /**
     * Add another vector
     * 
     * @param v The other vector
     * @return The sum vector
     */
    public Vector2 add(Vector2 v) { return new Vector2(x + v.x, y + v.y); }

    /**
     * Subtract another vector
     *
     * @param v The other vector
     * @return The difference vector
     */
    public Vector2 sub(Vector2 v) { return new Vector2(x - v.x, y - v.y); }

    /**
     * Multiply by a scalar
     * 
     * @param v The scalar
     * @return The scaled vector
     */
    public Vector2 mul(double v) { return new Vector2(x * v, y * v); }

    /**
     * Multiply by another vector component-wise
     * 
     * @param v The other vector
     * @return The scaled vector
     */
    public Vector2 mul(Vector2 v) { return new Vector2(x * v.x, y * v.y); }

    /**
     * Divide by a scalar
     *
     * @param v The scalar
     * @return The scaled vector
     */
    public Vector2 div(double v) { return new Vector2(x / v, y / v); }

    /**
     * Divide by another vector component-wise
     * 
     * @param v The other vector
     * @return The scaled vector
     */
    public Vector2 div(Vector2 v) { return new Vector2(x / v.x, y / v.y); }

    /**
     * Get the dot product with another vector
     * 
     * @param v The other vector
     * @return The dot product
     */
    public double dot(Vector2 v) { return x * v.x + y * v.y; }

    /**
     * Get the magnitude of the vector
     * 
     * @return The magnitude
     */
    public double length() { return Math.sqrt(x * x + y * y); }
    
    /**
     * Get the normalized vector
     * 
     * @return The normalized vector
     */
    public Vector2 normalized() {
        double l = length();
        return l == 0 ? this : new Vector2(x / l, y / l);
    }

    /**
     * Check if vectors are equal
     * 
     * @param v The other vector
     * @return Whether or not the vectors are equal
     */
    public boolean equals(Vector2 v) { return x == v.x && y == v.y; }

    /* ================ [ OBJECT ] ================ */

    /**
     * Convert the vector to a string
     * 
     * @return The vector as a string
     */
    @Override
    public String toString() { return "(" + x + ", " + y + ")"; }

    /* ================ [ CODEC ] ================ */
    
    @Override
    public void encode(OutputStream out) {
        encodeDouble(x,out);
        encodeDouble(y,out);
    }

    @Override
    public Vector2 decode(InputStream in) {
        var x = decodeDouble(in);
        var y = decodeDouble(in);
        return Vector2.of(x,y);
    }
}
