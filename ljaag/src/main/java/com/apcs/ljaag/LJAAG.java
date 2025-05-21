package com.apcs.ljaag;

import java.io.IOException;

import com.apcs.disunity.app.App;
import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.app.network.packet.SyncHandler;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Camera;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.body.LJCharacter;
import com.apcs.ljaag.nodes.items.Shotgun;

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

    public static void main(String[] args) throws IOException {
        // MultiplayerLauncher launcher = new MultiplayerLauncher(LJAAG::runApp);
        // launcher.lauch();
        runApp(true);
    }

    public static final int NUM_PLAYERS = 2;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void runApp(boolean isServer) {

        // Import keybinds from a JSON file
        Inputs.fromJSON("keybinds.json");

        // Create the game scenes
        Scene scene = new Scene("test", new Sprite("background.png"));

        // for (int i = 1; i <= NUM_PLAYERS; i++) {
        int i = 1;
            Node c = new LJCharacter(30 * i - 30, 0, i);
            Sprite s1 = new Shotgun("weapons/boomstick.png");
            Sprite s2 = new Shotgun(10, "weapons/boomstick.png");
            Sprite s3 = new Shotgun(-10, "weapons/boomstick.png");
            s1.setScale(Vector2.of(0.1, 0.1));
            s2.setScale(Vector2.of(0.1, 0.1));
            s3.setScale(Vector2.of(0.1, 0.1));
            c.addChildren(s1, s2, s3, new Camera());
            scene.addChild(c);
        // }

        // registerNodeRecursive(scene);

        // Create game application
        Game game = new Game(Vector2.of(480, 270));
        game.addScene(scene);
        game.setScene("test");

        // int endpointId = SyncHandler.getInstance().getEndpointId();
        new App("Shotgun Simulator"
            // endpointId == 0 ? "[SERVER]" : "[CLIENT_" + endpointId + "]"
        , 800, 450, game);

        game.start();

    }

    private static void registerNodeRecursive(Node<?> node) {
        SyncHandler.getInstance().register(node);
        for (Node<?> child : node.getChildren()) {
            registerNodeRecursive(child);
        }
    }

    // TODO: implement proper client reconciliation
    public static void own(Node<?> node, int clientId) {
        node.owner = clientId;
        for (Node<?> child : node.getChildren()) {
            own(child, clientId);
        }
    }
}
