// package com.apcs.disunity.physics;

// import com.apcs.disunity.signals.Signals;
// import java.util.ArrayList;
// import java.util.List;

// /**
//  * Manages physics and collision detection
//  * 
//  * @author Aayushya Patel
//  */
// public class Physics {

//     /* ================ [ FIELDS ] ================ */

//     /** Lists the collision areas */
//     private static final List<Area> areas = new ArrayList<>();

//     /* ================ [ METHODS ] ================ */

//     /**
//      * Register a collision area with the physics system
//      * 
//      * @param area The collision area
//      */
//     public static void registerAraea(Area area) {
//         areas.add(area);
//     }

//     /**
//      * Remove a collider from the physics system
//      * 
//      * @param area The collision area
//      */
//     public static void removeArea(Area area) {
//         areas.remove(area);
//     }

//     /**
//      * Update the physics system
//      * 
//      * @param delta The time since the last update
//      */
//     public static void update(double delta) {
//         // Check areas against each other
//         for (int i = 0; i < areas.size(); i++) {
//             for (int j = i + 1; j < areas.size(); j++) {
//                 checkCollision(colliders.get(i), colliders.get(j));
//             }
//         }
//     }

//     /**
//      * Check collision between two colliders
//      */
//     private static void checkCollision(Collider a, Collider b) {
//         // Check actual collision
//         if (a.getBounds().intersects(b.getBounds())) {
//             // Trigger collision events
//             Signals.trigger("collision_" + a.getId(), new CollisionInfo(a, b));

//             Signals.trigger("collision_" + b.getId(), new CollisionInfo(b, a));
//         }
//     }
// }
