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
import com.apcs.ljaag.nodes.HealthBar;
import com.apcs.ljaag.nodes.character.Characters;
import com.apcs.ljaag.nodes.character.enemies.Demon;
import com.apcs.ljaag.nodes.character.enemies.WyrmSegment;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.character.immortals.Immortals;
import com.apcs.ljaag.nodes.items.Shotgun;
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

        UsetimeSprite s = new UsetimeSprite("weapons/boomstick.png");
        s.setScale(Vector2.of(0.1));
        
        WyrmSegment ws = new WyrmSegment(new Transform(Vector2.of(0, 40)), null, Characters.EOW);

        // Create the game scenes
        Scene scene = new Scene("game",
            new Sprite("background.png"),
            new Immortal(new Transform(), Immortals.ZHAO,
                new Camera(),
                new UsetimeItem(
                    20,
                    1,
                    "fire",
                    new UsetimeSound("sounds/boomstick.wav"),
                    new Shotgun()
                ),
                new UsetimeItem(
                    10,
                    1,
                    "fire",
                    s
                ),
                new HealthBar(new Transform(Vector2.of(0, -15)))
            ),
            ws,
            ws = new WyrmSegment(ws, Characters.EOW),
            ws = new WyrmSegment(ws, Characters.EOW),
            ws = new WyrmSegment(ws, Characters.EOW),
            ws = new WyrmSegment(ws, Characters.EOW),
            ws = new WyrmSegment(ws, Characters.EOW),
            ws = new WyrmSegment(ws, Characters.EOW),
            ws = new WyrmSegment(ws, Characters.EOW)
        );

        // Create game application
        Game game = new Game(Vector2.of(480, 270));
        game.addScene(scene);
        game.setScene("game");

        new App("Shotgun Simulator", 800, 450, game);

        // spawn demons
        new Thread(() -> {
            double time = System.currentTimeMillis();
            while (!Thread.currentThread().isInterrupted()) {
                if (System.currentTimeMillis() - time > 10000) {
                    // prevent concurrent modification by locking the update list
                    Demon e1 = new Demon(
                        new Transform(Vector2.of(+100, 0)),
                        Characters.BROKEN_VESSEL,
                        new HealthBar(new Transform(Vector2.of(0, -15)))
                    );
                    Demon e2 = new Demon(
                        new Transform(Vector2.of(-100, 0)),
                        Characters.BROKEN_VESSEL,
                        new HealthBar(new Transform(Vector2.of(0, -15)))
                    );
                    Demon e3 = new Demon(
                        new Transform(Vector2.of(0, +100)),
                        Characters.BROKEN_VESSEL,
                        new HealthBar(new Transform(Vector2.of(0, -15)))
                    );
                    Demon e4 = new Demon(
                        new Transform(Vector2.of(0, -100)),
                        Characters.BROKEN_VESSEL,
                        new HealthBar(new Transform(Vector2.of(0, -15)))
                    );
                    scene.addChildren(e1, e2, e3, e4);
                    time = System.currentTimeMillis();
                }
            }
        }); //.start();

        scene.print();

        // Start game
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
