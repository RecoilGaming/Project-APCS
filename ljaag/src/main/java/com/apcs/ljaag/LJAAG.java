package com.apcs.ljaag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import javax.swing.JOptionPane;

import com.apcs.disunity.App;
import com.apcs.disunity.Game;
import com.apcs.disunity.animation.Animation;
import com.apcs.disunity.animation.AnimationSet;
import com.apcs.disunity.camera.Camera;
import com.apcs.disunity.input.Inputs;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.Node2D;
import com.apcs.disunity.nodes.body.Body;
import com.apcs.disunity.nodes.controller.Controller;
import com.apcs.disunity.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.nodes.sprite.Sprite;
import com.apcs.disunity.scenes.Scenes;
import com.apcs.disunity.server.ClientSideSyncHandler;
import com.apcs.disunity.server.HostSideSyncHandler;
import com.apcs.disunity.server.SyncHandler;
import com.apcs.ljaag.nodes.action.TurnAction;
import com.apcs.ljaag.nodes.action.WalkAction;
import com.apcs.ljaag.nodes.controller.PlayerController;

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
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        String input = JOptionPane.showInputDialog("DISUNITY STARTUP WIZARD\n\nWould you like to start or join a session? (s/j)");
        if (input == null) System.exit(0);
        if (input.startsWith("s") || input.startsWith("S")) {
            Server.main(args);
        } else {
            Client.main(args);
        }
    }

    public static final int NUM_PLAYERS = 2;

    public static class Server {
        public static void main(String[] args) throws IOException, InterruptedException {
            HostSideSyncHandler host = new HostSideSyncHandler();

            String lastLine = null;
            Process process = null;

            boolean forward = JOptionPane.showInputDialog("DISUNITY STARTUP WIZARD\n\nWould you like to expose to the internet in addition to LAN?\n Doing so requires bash and ssh, but allows players on different networks to join. (y/n)").startsWith("y");
            if (forward) {
                int localPort = host.getPort();
                process = new ProcessBuilder("bash", "-c",
                    "ssh -o StrictHostKeyChecking=no -R 0:localhost:" + localPort + " serveo.net")
                    .redirectErrorStream(true).start();

                

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    int i = 0;
                    while ((line = reader.readLine()) != null) {
                        if (++i == 3) {
                            lastLine = line;
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                lastLine = host.getAddress()+":"+host.getPort();
            }
   
            runApp(NUM_PLAYERS, false);
            host.start();
            JOptionPane.showMessageDialog(null, String.format("JOIN INFORMATION:\n\nAddress: %s\nPort: %s\n\nClose this window to end the instance", lastLine.substring(Math.max(lastLine.lastIndexOf(' '),0)).split(":")));
            if (process != null) process.destroy();
            host.close();
            System.exit(0);
        }
    }

    public static class Client {
        public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
            SyncHandler handler;
            while (true) {
                String address = JOptionPane.showInputDialog("DISUNITY STARTUP WIZARD\n\nEnter the Server address. Leave blank for your local Server instance.");
                if (address == null) System.exit(0);
                String port = JOptionPane.showInputDialog("DISUNITY STARTUP WIZARD\n\nEnter the Server port.");
                if (port == null) System.exit(0);
                if (address.isBlank()) address = "0:0:0:0:0:0:0:0";
                try {
                    Field instanceField = SyncHandler.class.getDeclaredField("instance");
                    instanceField.setAccessible(true);
                    instanceField.set(null, null);
                    handler = new ClientSideSyncHandler(address, (int) Integer.parseInt(port));
                    break;
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null, "DISUNITY STARTUP WIZARD\nCould not connect to this Server");
                    ioe.printStackTrace();
                }
            }
            runApp(NUM_PLAYERS, true);
            handler.start();
        }
    }

    private static void runApp(int players, boolean viewable) {
        int endpointId = SyncHandler.getInstance().getEndpointId();

        // Import keybinds from a JSON file

        // Create the game scenes
        Scenes.addScene("test", new Node2D(
            new Sprite("background.png")
        ));

        Scenes.setScene("test");
        for (int i = 1; i <= players; i++) {
            Scenes.getScene().addChildren(instantiateCharacter(i));
        }

        registerNodeRecursive(Scenes.getScene());

        // Create game application

        Game game = new Game(
            Vector2.of(480, 270),
            "test",
            endpointId == 0
        );

        if (viewable) {
            new App(
                SyncHandler.getInstance().getLabel(),
                800, 
                450,
                game);
        }

        game.start();

    }

    private static Body instantiateCharacter(int clientId) {
        Inputs.fromJSON("keybinds.json", clientId);
        boolean isPlayer = SyncHandler.getInstance().getEndpointId() == clientId;
        return new Body(
            isPlayer ? new Camera() : new Node2D(),
            new AnimatedSprite(
                new AnimationSet("player/player.png",
                    new Animation("run", "player/run.png", 0.15, 0.15, 0.15, 0.15, 0.15, 0.15)
                ),
                true
            ),
            new PlayerController(clientId),
            new WalkAction(),
            new TurnAction()
        );
    }

    private static void registerNodeRecursive(Node<?> node) {
        SyncHandler.getInstance().register(node);
        for (Node<?> child : node.getChildren()) {
            registerNodeRecursive(child);
        }
    }
}
