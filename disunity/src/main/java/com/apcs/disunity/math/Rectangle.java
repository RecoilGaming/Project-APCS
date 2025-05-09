package com.apcs.disunity.math;

/**
 * Stores a top, bottom, left, and right for an axis-aligned rectangle
 * IMMUTABLE
 * 
 * @author Aayushya Patel
 * @author Qinzhao Li
 */
public class Rectangle {

    /* ================ [ FIELDS ] ================ */

    /** The top of the rectangle */
    public final double top;
    /** The bottom of the rectangle */
    public final double bottom;
    /** The left of the rectangle */
    public final double left;
    /** The right of the rectangle */
    public final double right;

    /** Create a new Rectangle */
    public Rectangle() { this(0, 0, 0, 0); }
    /**
     * Create a new Rectangle with the given values
     * 
     * @param top The top of the rectangle
     * @param bottom The bottom of the rectangle
     * @param left The left of the rectangle
     * @param right The right of the rectangle
     */
    public Rectangle(double top, double bottom, double left, double right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Create a new Rectangle with the given values
     * 
     * @param top The top of the rectangle
     * @param bottom The bottom of the rectangle
     * @param left The left of the rectangle
     * @param right The right of the rectangle
     * @return The new Rectangle
     */
    public static Rectangle of(double top, double bottom, double left, double right) {
        return new Rectangle(top, bottom, left, right);
    }

    /**
     * Get a clone of the rectangle
     * 
     * @return A clone of the rectangle
     */
    public Rectangle clone() {
        return new Rectangle(top, bottom, left, right);
    }

    /**
     * Get the center of the rectangle
     * 
     * @return The center of the rectangle
     */
    public Vector2 getCenter() {
        return new Vector2(
            (left + right) / 2,
            (top + bottom) / 2
        );
    }

    /**
     * Check if the rectangle intersects with another
     * 
     * @param other The other rectangle
     * @return Whether or not the rectangles intersect
     */
    public boolean intersects(Rectangle other) {
        if (top > other.bottom || other.top > bottom) return false;
        if (left > other.right || other.left > right) return false;
        return true;
    }

    /**
     * Check if the rectangle contains another
     * 
     * @param other The other rectangle
     * @return Whether or not the rectangles intersect
     */
    public boolean contains(Rectangle other) {
        return other.left >= left && other.right <= right && other.top >= top && other.bottom <= bottom;
    }
    
}
