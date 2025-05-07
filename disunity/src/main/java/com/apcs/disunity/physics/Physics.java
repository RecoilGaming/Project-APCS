package com.apcs.disunity.physics;

import com.apcs.disunity.signals.Signals;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages physics and collision detection
 * 
 * @author Aayushya Patel
 */
public class Physics {

    /* ================ [ FIELDS ] ================ */

    /** Lists the collision areas */
    private static final List<Area> areas = new ArrayList<>();

    /* ================ [ METHODS ] ================ */

    /**
     * Register a collision area with the physics system
     * 
     * @param area The collision area
     */
    public static void registerArea(Area area) {
        areas.add(area);
    }

    /**
     * Remove a collider from the physics system
     * 
     * @param area The collision area
     */
    public static void removeArea(Area area) {
        areas.remove(area);
    }

    /**
     * Checks and triggers an intersection between two areas
     * 
     * @param a The first area
     * @param b The second area
     */
    private static void intersect(Area a, Area b) {
        // Check if areas are intersecting
        if (a.getLayer() == b.getLayer() && a.getBounds().intersects(b.getBounds())) {
            Signals.trigger(Signals.getSignal(a.getId(), "collision"), new CollisionInfo(a, b));
            Signals.trigger(Signals.getSignal(a.getId(), "collision"), new CollisionInfo(b, a));
        }
    }

    /**
     * Update the physics system
     * 
     * @param delta The time since the last update
     */
    public static void update(double delta) {
        // Check areas against each other
        for (int i = 0; i < areas.size() - 1; i++) {
            for (int j = i + 1; j < areas.size(); j++) {
                intersect(areas.get(i), areas.get(j));
            }
        }
    }

}
