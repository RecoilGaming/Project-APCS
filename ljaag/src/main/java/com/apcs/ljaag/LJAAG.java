package com.apcs.ljaag;

import java.awt.Color;
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
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.Gate;
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
import com.apcs.ljaag.nodes.character.enemies.Enemy;
import com.apcs.ljaag.nodes.character.enemies.EnemyManager;

/**
 * Untitled game
 *
 * @author Aayushya Patel
 * @author Qinzhao Li
 * @author Sharvil Phadke
 * @author Toshiki Takeuchi
 */
public class LJAAG {

    public static final double SIMULATION_DISTANCE_CHUNKS = 5;

    /* ================ [ METHODS ] ================ */

    // Play the game 
    private static void play(boolean isServer) {
        // Import keybinds from a JSON file
        Inputs.fromJSON("keybinds.json");

        UsetimeSprite s = new UsetimeSprite("weapons/boomstick.png");
        s.setScale(Vector2.of(0.1));
        
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
            loadLevel("levels/test.txt", scene, game);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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

    public static void loadLevel(String name, Scene scene, Game game) throws IOException {
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
        s.nextLine();

        scene.clearChildren();

        EnemyManager m = new EnemyManager(blockSize * SIMULATION_DISTANCE_CHUNKS);
        scene.addChild(m);

        Sprite sp = new Sprite("ground.png");
        sp.setScale(Vector2.of(width * blockSize / sp.getImageLocation().getImage().getWidth(), height * blockSize / sp.getImageLocation().getImage().getHeight()));
        sp.setPosition(Vector2.of(width * blockSize / 2, height * blockSize / 2));
        scene.addChild(sp);
        String nextLevelPath = s.nextLine().trim();
        System.out.println(nextLevelPath);
        if (!nextLevelPath.contains("NONE") && !game.hasScene(nextLevelPath)) {
            Scene nextScene = new Scene(nextLevelPath);
            game.addScene(nextScene);
            try {
                loadLevel(nextLevelPath, nextScene, game);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        while (s.hasNextLine()) {
            x = 0;
            String line = s.nextLine();
            for (char c : line.toCharArray()) {
                switch (c) {
                    // gates to next level
                    case 'G' -> {
                        double scale = blockSize / (Resources.loadResource("gate.png", Image.class).getBuffer().getHeight());
                        scene.addChild(
                            new Gate(
                                new Transform(Vector2.of(x * blockSize, y * blockSize), Vector2.of(scale), 0),
                                () -> {
                                    boolean allDead = true;
                                    for (Node n : scene.getAllChildren()) {
                                        if (n instanceof Enemy e && e.getHealth() > 0) {
                                            allDead = false;
                                        } 
                                    }
                                    return !nextLevelPath.contains("NONE") && allDead;
                                },
                                () -> {
                                    game.setScene(nextLevelPath);
                                    
                                },
                                new Collider(blockSize / 2, blockSize / 2),
                                new Area2D(Vector2.of(blockSize / 2))
                            )
                        );
                    }
                    // Lava
                    case 'L' -> {
                        double[] framesDurations = new double[45];
                        for (int i = 0; i < framesDurations.length; i++) {
                            framesDurations[i] = 0.1;
                        }
                        double scale = blockSize / (Resources.loadResource("lava.png", Image.class).getBuffer().getWidth() / 45.);
                        scene.addChild(new Body(
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
                        scene.addChild(new Immortal(new Transform(Vector2.of(x * blockSize, y * blockSize)), Immortals.ZHAO,
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
                        scene.addChildren(
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
                        scene.addChildren(
                            new Spawner(5, (t) -> {
                                WyrmSegment ws = new WyrmSegment(t, null, Characters.EOW, new HealthBar(healthBarTransform));
                                scene.addChildren(
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
                                m.getEnemies().clear();
                                m.getPlayers().clear();
                                for (Node n : scene.getAllChildren()) {
                                    switch (n) {
                                        case Enemy e -> m.getEnemies().add(e);
                                        case Immortal i -> m.getPlayers().add(i);
                                        default -> {}
                                    }
                                }
                            },
                            new Transform(Vector2.of(x * blockSize, y * blockSize)),
                            Characters.SPAWNER,
                            new HealthBar(new Transform(Vector2.of(0, -blockSize * 0.65), Vector2.of(blockSize / 20), 0))
                            )
                        );
                    }
                    // demon
                    case 'd' -> {
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10), Vector2.of(0.5), 0);   
                        Demon d = new Demon(new Transform(Vector2.of(x * blockSize, y * blockSize)), Characters.BROKEN_VESSEL, new HealthBar(healthBarTransform));
                        scene.addChild(d);
                    }

                    // demon spawner
                    case 'D' -> {
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10), Vector2.of(0.5), 0);   
                        scene.addChildren(
                            new Spawner(5, (t) -> { 
                                Demon d = new Demon(t, Characters.BROKEN_VESSEL, new HealthBar(healthBarTransform));
                                scene.addChild(d);
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

        for (Node n : scene.getAllChildren()) {
            switch (n) {
                case Enemy e -> m.getEnemies().add(e);
                case Immortal i -> m.getPlayers().add(i);
                default -> {}
            }
        }
    }

}
