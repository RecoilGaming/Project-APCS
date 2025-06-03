package com.apcs.ljaag;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.apcs.disunity.app.App;
import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.nodes.twodim.Camera;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.HealthBar;
import com.apcs.ljaag.nodes.character.Characters;
import com.apcs.ljaag.nodes.character.enemies.Demon;
import com.apcs.ljaag.nodes.character.enemies.WyrmSegment;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.character.immortals.Immortals;
import com.apcs.ljaag.nodes.items.Machinegun;
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
            )
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
        }).start();

        // try {
        //     loadLevel("levels/test.txt", scene);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
        // Start game
        scene.print();
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

    public static void loadLevel(String name, Node dist) throws IOException {
        InputStream source = LJAAG.class.getClassLoader().getResourceAsStream(name);
        if (source == null) {
            System.out.println("unable to load level: "+name);
            return;
        }
        Scanner s = new Scanner(source);
        int blockSize = s.hasNextInt() ? s.nextInt() : 10;
        int x, y = x = 0;
        while (s.hasNextLine()) {
            x = 0;
            String line = s.nextLine();
            for (char c : line.toCharArray()) {
                switch (c) {
                    // walls
                    case 'W' -> {
                        Body n;
                        dist.addChild(n = new Body(
                            new Collider(blockSize, blockSize),
                            new Area2D(Vector2.of(blockSize))
                        ) {
                            @Override
                            public void draw(Transform offset) {
                                super.draw(offset);
                                Game.getInstance().getBuffer().drawRect(blockSize, blockSize, Color.BLACK, offset.apply(getTransform()).addPos(Vector2.of(blockSize).mul(-0.5)));
                            }

                            @Override
                            public void onBodyEntered(BodyEntered signal) { }
                        });
                        n.setPosition(Vector2.of(x * blockSize, y * blockSize));
                    }
                    // player spawn
                    case 'P' -> {
                        UsetimeSprite uzi = new UsetimeSprite("weapons/uzi.png");
                        uzi.setScale(Vector2.of(0.05));
                        dist.addChild(new Immortal(new Transform(Vector2.of(x * blockSize, y * blockSize)), Immortals.ZHAO,
                            new Camera(),
                            // new UsetimeItem(
                            //     20,
                            //     1,
                            //     "fire",
                            //     new UsetimeSound("sounds/boomstick.wav"),
                            //     new Shotgun()
                            // ),
                            // new UsetimeItem(
                            //     10,
                            //     1,
                            //     "fire",
                            //     s
                            // ),
                            new UsetimeItem(
                                20,
                                0.2,
                                "fire",
                                new UsetimeSound("sounds/uzi.wav"),
                                new Machinegun()
                            ),
                            new UsetimeItem(
                                10,
                                1,
                                "fire",
                                uzi
                            ),
                            new HealthBar(new Transform(Vector2.of(0, -15)))
                        ));
                    }
                    // wyrms spawn
                    case 'Y' -> {
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10), Vector2.of(0.5), 0);   
                        WyrmSegment ws = new WyrmSegment(new Transform(Vector2.of(x * blockSize, y * blockSize)), null, Characters.EOW, new HealthBar(healthBarTransform));
                        dist.addChildren(
                            ws,
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW, new HealthBar(healthBarTransform))
                        );
                    }
                    default -> {}
                }
                if (!Character.isSpaceChar(c)) {
                    
                }
                x++;
            }
            y++;
            
        }
    }

}
