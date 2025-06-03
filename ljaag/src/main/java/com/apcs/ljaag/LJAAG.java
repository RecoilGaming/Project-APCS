package com.apcs.ljaag;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.apcs.disunity.app.App;
import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.app.resources.Image;
import com.apcs.disunity.app.resources.Resources;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.nodes.twodim.Camera;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.HealthBar;
import com.apcs.ljaag.nodes.character.Characters;
import com.apcs.ljaag.nodes.character.enemies.Demon;
import com.apcs.ljaag.nodes.character.enemies.Spawner;
import com.apcs.ljaag.nodes.character.enemies.WyrmSegment;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.character.immortals.Immortals;
import com.apcs.ljaag.nodes.items.Machinegun;
import com.apcs.ljaag.nodes.items.Shotgun;
import com.apcs.ljaag.nodes.items.UsetimeItem;
import com.apcs.ljaag.nodes.items.UsetimeSound;
import com.apcs.ljaag.nodes.items.UsetimeSprite;
import com.apcs.ljaag.nodes.character.Character;

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

        Transform healthBarTransform = new Transform(Vector2.of(0, -10), Vector2.of(0.5), 0);
        
        // Create the game scenes
        Scene scene = new Scene("game");

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
        });//.start();

        try {
            loadLevel("levels/test.txt", scene);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

    public static void loadLevel(String name, Node dist) throws IOException {
        InputStream source = LJAAG.class.getClassLoader().getResourceAsStream(name);
        if (source == null) {
            System.out.println("unable to load level: "+name);
            return;
        }
        Scanner s = new Scanner(source);
        int blockSize = s.hasNextInt() ? s.nextInt() : 10;
        int width = s.hasNextInt() ? s.nextInt() : 10;
        int height = s.hasNextInt() ? s.nextInt() : 10;
        int x, y = x = 0;
        Sprite sp = new Sprite("ground.png");
        sp.setScale(Vector2.of(width * blockSize / sp.getImageLocation().getImage().getWidth(), height * blockSize / sp.getImageLocation().getImage().getHeight()));
        sp.setPosition(Vector2.of(width * blockSize / 2, width * blockSize / 2));
        dist.addChild(sp);
        while (s.hasNextLine()) {
            x = 0;
            String line = s.nextLine();
            for (char c : line.toCharArray()) {
                switch (c) {
                    // void (removed)
                    // case 'V' -> {
                    //     Body n;
                    //     dist.addChild(n = new Body(
                    //         new Transform(Vector2.of(x * blockSize, y * blockSize)),
                    //         new Collider(blockSize, blockSize),
                    //         new Area2D(Vector2.of(blockSize))
                    //     ) {
                    //         @Override
                    //         public void draw(Transform offset) {
                    //             super.draw(offset);
                    //             Game.getInstance().getBuffer().drawRect(blockSize, blockSize, Color.BLACK, offset.apply(getTransform()).addPos(Vector2.of(blockSize).mul(-0.5)));
                    //         }

                    //         @Override
                    //         public void onBodyEntered(BodyEntered signal) { }

                    //         @Override
                    //         public void setPosition(Vector2 pos) {}
                    //     });
                    // }
                    // Lava
                    case 'L' -> {
                        Body n;
                        double[] framesDurations = new double[45];
                        for (int i = 0; i < framesDurations.length; i++) {
                            framesDurations[i] = 0.1;
                        }
                        double scale = blockSize / (Resources.loadResource("lava.png", Image.class).getBuffer().getWidth() / 45.);
                        dist.addChild(n = new Body(
                            new Transform(Vector2.of(x * blockSize, y * blockSize), Vector2.of(scale), 0),
                            new Collider(blockSize, blockSize),
                            new Area2D(Vector2.of(blockSize)),
                            new AnimatedSprite("lava", "lava.png", true, framesDurations)
                        ) {
                            

                            @Override
                            public void onBodyEntered(BodyEntered signal) {
                                if (signal.body instanceof Character c) {
                                    c.modifyHealth(-1);
                                }
                            }

                            @Override
                            public void setPosition(Vector2 pos) {}
                        });
                    }
                    // player spawn
                    case 'P', 'p' -> {
                        UsetimeSprite uzi = new UsetimeSprite("weapons/uzi.png");
                        uzi.setScale(Vector2.of(0.08));
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
                    // wyrms starting spawn
                    case 'y' -> {
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
                    // wyrm spawner
                    case 'Y' -> {
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10), Vector2.of(0.5), 0);   
                        dist.addChildren(
                            new Spawner(5, (t) -> {
                                WyrmSegment ws = new WyrmSegment(t, null, Characters.EOW, new HealthBar(healthBarTransform));
                                dist.addChildren(
                                    ws,
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW, new HealthBar(healthBarTransform))
                                );
                            },
                            new Transform(Vector2.of(x * blockSize, y * blockSize)),
                            Characters.SPAWNER,
                            new HealthBar(new Transform(Vector2.of(0, -blockSize * 0.65), Vector2.of(blockSize / 20), 0))
                            )
                        );
                    }
                    default -> {}
                }
                x++;
            }
            y++;
            
        }
    }

}
