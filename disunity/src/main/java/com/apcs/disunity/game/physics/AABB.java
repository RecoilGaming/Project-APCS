package com.apcs.disunity.game.physics;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.math.Vector2;

/// Axis Aligned Bounding Box, where pos is at the center of the bounding box.
public class AABB {
    public final double RIGHT;
    public final double BOTTOM;
    public final double LEFT;
    public final double TOP;
    public final Vector2 SIZE;
    public final Vector2 POS;
    public final CollisionLayer LAYER;
    public final CollisionMask MASK;

    public AABB(Collider collider, Vector2 absPos) { this(collider.SIZE, absPos, collider.LAYER, collider.MASK); }

    public AABB(Vector2 size, Vector2 absPos, CollisionLayer layer, CollisionMask mask) {
        this.LAYER = layer;
        this.SIZE = size;
        this.MASK = mask;
        POS = absPos;
        TOP = absPos.y - SIZE.y / 2;
        LEFT = absPos.x - SIZE.x / 2;
        BOTTOM = TOP + SIZE.y;
        RIGHT = LEFT + SIZE.x;
    }

    public boolean isColliding(AABB that) {
        // Check actual collision
        return (this.MASK.BITSET & that.LAYER.BITSET) != 0
                && Math.max(this.LEFT, that.LEFT) < Math.min(this.RIGHT, that.RIGHT)
                && Math.max(this.TOP, that.TOP) < Math.min(this.BOTTOM, that.BOTTOM);
    }

    public AABB setPos(Vector2 pos) { return new AABB(SIZE, pos, LAYER, MASK); }

    /// returns array of doubles that represents the boundary of this AABB, in the
    /// order of
    /// RIGHT, BOTTOM, LEFT, TOP
    public double[] toArray() { return new double[] { RIGHT, BOTTOM, LEFT, TOP }; }
}
