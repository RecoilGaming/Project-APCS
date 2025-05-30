package com.apcs.ljaag;

import com.apcs.disunity.app.App;
import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Camera;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.Characters;
import com.apcs.ljaag.nodes.character.immortals.Demon;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.character.immortals.Immortals;
import com.apcs.ljaag.nodes.items.Machinegun;
import com.apcs.ljaag.nodes.items.Shotgun;
import com.apcs.ljaag.nodes.items.UsetimeAnimation;
import com.apcs.ljaag.nodes.items.UsetimeItem;
import com.apcs.ljaag.nodes.items.UsetimeSound;
import com.apcs.ljaag.nodes.items.UsetimeSprite;

/**
 * Untitled game
 *
 * @author Aayushya Patel
 * @author Qinzhao Li
 * @author Sharvil Phadke
 * @author Toshiki Takeuchi
 */
public class LJAAG {

    /* ================ [ METHODS ] ================ */

    // Play the game 
    private static void play(boolean isServer) {
        // Import keybinds from a JSON file
        Inputs.fromJSON("keybinds.json");

        // Create the game scenes
        Scene scene = new Scene("test", new Sprite("background.png"));

        Node c = new Immortal(new Transform(), Immortals.ZHAO, "demon.png", "demonrun.png", 4);
        Sprite s = new UsetimeSprite("weapons/boomstick.png");
        Sprite s2 = new UsetimeSprite("weapons/uzi.png");
        s.setScale(Vector2.of(0.1));
        s2.setScale(Vector2.of(0.08));
        UsetimeItem shotgunView = new UsetimeItem(15, 1, "fire", s);
        UsetimeItem shotgunFire = new UsetimeItem(30, 1, "fire",
            new Shotgun(),
            // new Machinegun(),
            new UsetimeSound("sounds/boomstick.wav")
        );

        UsetimeItem machinegunView = new UsetimeItem(15, 1, "fireuzi", s2);
        UsetimeItem machinegunFire = new UsetimeItem(30, 0.1, "fireuzi",
            // new Shotgun(),
            new Machinegun(),
            new UsetimeSound("sounds/uzi.wav")
        );
        UsetimeItem swordSwing = new UsetimeItem(20, 0.30, "swing",
            s = new UsetimeAnimation("swing", "weapons/sword.png", 0.05, 0.05, 0.05, 0.05, 0.05),
            new UsetimeSound("sounds/swoosh.wav")
        );
        s.setScale(Vector2.of(1));
        c.addChildren(machinegunFire, machinegunView, shotgunView, shotgunFire, swordSwing, new Camera());
        scene.addChildren(c);

        // Create game application
        Game game = new Game(Vector2.of(480, 270));
        game.addScene(scene);
        game.setScene("test");

        new App("Shotgun Simulator", 800, 450, game);

        // spawn demons
        new Thread(() -> {
            double time = System.currentTimeMillis();
            while (!Thread.currentThread().isInterrupted()) {
                // if (false) {
                if (System.currentTimeMillis() - time > 10000) {
                    // prevent concurrent modification by locking the update list
                    Demon e1 = new Demon(Characters.BROKEN_VESSEL);
                    e1.setPosition(Vector2.of(100, 0));
                    Demon e2 = new Demon(Characters.BROKEN_VESSEL);
                    e2.setPosition(Vector2.of(-100, 0));
                    // Demon e3 = new Demon(Characters.BROKEN_VESSEL);
                    // e3.setPosition(Vector2.of(0, 100));
                    // Demon e4 = new Demon(Characters.BROKEN_VESSEL);
                    // e4.setPosition(Vector2.of(0, -100));
                    scene.addChildren(e1, e2); // e2, e3, e4);
                    time = System.currentTimeMillis();
                }
            }
        }).start();

        game.start();

    }

    public static void own(Node<?> node, int clientId) {
        node.owner = clientId;
        for (Node<?> child : node.getAllChildren()) {
            own(child, clientId);
        }
    }

    /* ================ [ DRIVER ] ================ */

    public static void main(String[] args) { play(true); }

}
