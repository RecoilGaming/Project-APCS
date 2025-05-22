package com.apcs.ljaag;

import java.io.IOException;

import com.apcs.disunity.app.App;
import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Camera;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.body.LJCharacter;
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

    /* ================ [ DRIVER ] ================ */

    public static void main(String[] args) throws IOException { runApp(true); }

    private static void runApp(boolean isServer) {
        // Import keybinds from a JSON file
        Inputs.fromJSON("keybinds.json");

        // Create the game scenes
        Scene scene = new Scene("test", new Sprite("background.png"));

        Node c = new LJCharacter(0, 0, 1);
        Sprite s = new UsetimeSprite("weapons/boomstick.png");
        s.setScale(Vector2.of(0.1));
        UsetimeItem shotgunAbility1 = new UsetimeItem(15, 1, "fire",
            s,
            new Shotgun(),
            new UsetimeSound("sounds/boomstick.wav")
        );
        UsetimeItem swordSwing = new UsetimeItem(20, 0.30, "swing",
            s = new UsetimeAnimation("swing", "weapons/sword.png", 0.05, 0.05, 0.05, 0.05, 0.05),
            new UsetimeSound("sounds/swoosh.wav")
        );
        s.setScale(Vector2.of(1));
        c.addChildren(shotgunAbility1, swordSwing, new Camera());
        scene.addChild(c);

        // Create game application
        Game game = new Game(Vector2.of(480, 270));
        game.addScene(scene);
        game.setScene("test");

        new App("Shotgun Simulator", 800, 450, game);

        game.start();

    }

    public static void own(Node<?> node, int clientId) {
        node.owner = clientId;
        for (Node<?> child : node.getAllChildren()) {
            own(child, clientId);
        }
    }
}
