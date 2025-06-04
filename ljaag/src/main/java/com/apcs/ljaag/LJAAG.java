package com.apcs.ljaag;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.apcs.disunity.app.App;
import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.app.input.actions.Action;
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
import com.apcs.disunity.game.signals.Signal;
import com.apcs.disunity.game.signals.SignalBus;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.Gate;
import com.apcs.ljaag.nodes.HealthBar;
import com.apcs.ljaag.nodes.Indicator;
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

    private static Game game;
    private static Scene scene;

    public static final double SIMULATION_DISTANCE_CHUNKS = 5;

    /* ================ [ METHODS ] ================ */

    // Play the game 
    private static void play(boolean isServer) {
        // Import keybinds from a JSON file
        Inputs.fromJSON("keybinds.json");

        UsetimeSprite s = new UsetimeSprite("weapons/boomstick.png");
        s.setScale(Vector2.of(0.1));
        
        // Create the game scenes
        Scene scene = new Scene("levels/0_start.txt");

        // Create game application
        game = new Game(Vector2.of(480, 270));
        game.addScene(scene);
        game.setScene("levels/0_start.txt");

        new App("Shotgun Simulator", 800, 450, game);

        try {
            loadLevel("levels/0_start.txt", scene, game);
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

    public static void restart() {
        // Game.getInstance().resetScenes();

        // try {
        //     loadLevel("levels/test.txt", scene, game);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // game.setScene("levels/test.txt");
        Game.getInstance().unpause();
    }

    public static void loadLevel(String name, Scene scene, Game game) throws IOException {
        
        scene.clearChildren();
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

        EnemyManager m = new EnemyManager(blockSize * SIMULATION_DISTANCE_CHUNKS);
        scene.addChild(m);
        scene.addChild(new Node<Node>() {
            private double cooldown = 1;
            @Override
            public void update(double dt) {
                cooldown -= dt;
                if (cooldown <= 0 && Inputs.getAction("restart")) {
                    try {
                        loadLevel(name, scene, game);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        String nextLevelPath = s.nextLine().trim();
        if (!nextLevelPath.contains("NONE")) {
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
                        double scale = (double) blockSize / (Resources.loadResource("gate.png", Image.class).getBuffer().getHeight());
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
                                    c.modifyHealth((c instanceof Immortal) ? -10 : -2);
                                }
                            }

                            @Override
                            public void setPosition(Vector2 pos) {}
                        });
                    }
                    // player spawn
                    case 'P', 'p' -> {
                        // UsetimeSprite uzi = new UsetimeSprite("weapons/uzi.png");
                        // uzi.setScale(Vector2.of(0.08));
                        UsetimeSprite boomstick = new UsetimeSprite("weapons/boomstick.png");
                        boomstick.setScale(Vector2.of(0.08));
                        scene.addChild(new Immortal(new Transform(Vector2.of(x * blockSize, y * blockSize)), Immortals.ZHAO.get(),
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
                                boomstick
                            ),
                            // new UsetimeItem(
                            //     20,
                            //     0.2,
                            //     "fire",
                            //     new UsetimeSound("sounds/uzi.wav"),
                            //     new Machinegun()
                            // ),
                            // new UsetimeItem(
                            //     10,
                            //     1,
                            //     "fire",
                            //     uzi
                            // ),
                            new HealthBar(new Transform(Vector2.of(0, -15)))
                        ));
                    }
                    // wyrms starting spawn
                    case 'w' -> {
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10), Vector2.of(0.5), 0);   
                        WyrmSegment ws = new WyrmSegment(new Transform(Vector2.of(x * blockSize, y * blockSize)), null, Characters.EOW.get(), new HealthBar(healthBarTransform));
                        scene.addChildren(
                            ws,
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                            ws = new WyrmSegment(new Transform(), ws, Characters.EOW.get(), new HealthBar(healthBarTransform))
                        );
                    }
                    // wyrm spawner
                    case 'W' -> {
                        double scale = (double) blockSize / (Resources.loadResource("spawner/idle.png", Image.class).getBuffer().getHeight());
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10), Vector2.of(0.5), 0);   
                        scene.addChildren(
                            new Spawner(5, (t) -> {
                                WyrmSegment ws = new WyrmSegment(t, null, Characters.EOW.get(), new HealthBar(healthBarTransform));
                                scene.addChildren(
                                    ws,
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform)),
                                    ws = new WyrmSegment(t, ws, Characters.EOW.get(), new HealthBar(healthBarTransform))
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
                            new Transform(Vector2.of(x * blockSize, y * blockSize), Vector2.of(scale), 0),
                            Characters.SPAWNER.get(),
                            new HealthBar(new Transform(Vector2.of(0, -blockSize * 0.75), Vector2.of(blockSize / 20), 0))
                            )
                        );
                    }
                    // demon
                    case 'd' -> {
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10));   
                        Demon d = new Demon(new Transform(Vector2.of(x * blockSize, y * blockSize)), Characters.BROKEN_VESSEL.get(), new HealthBar(healthBarTransform));
                        scene.addChild(d);
                    }

                    // demon spawner
                    case 'D' -> {
                        double scale = (double) blockSize / (Resources.loadResource("spawner/idle.png", Image.class).getBuffer().getHeight());
                        Transform healthBarTransform = new Transform(Vector2.of(0, -10));   
                        scene.addChildren(
                            new Spawner(5, (t) -> { 
                                Demon d = new Demon(t, Characters.BROKEN_VESSEL.get(), new HealthBar(healthBarTransform));
                                scene.addChild(d);
                            },
                            new Transform(Vector2.of(x * blockSize, y * blockSize), Vector2.of(scale), 0),
                            Characters.SPAWNER.get(),
                            new HealthBar(new Transform(Vector2.of(0, -blockSize * 0.75), Vector2.of(blockSize / 20), 0))
                            )
                        );
                    }
                    default -> {}
                }
                x++;
            }
            y++;
            
        }

        Sprite background = new Sprite("ground.png");
        background.setScale(Vector2.of(width * blockSize / background.getImageLocation().getImage().getWidth(), height * blockSize / background.getImageLocation().getImage().getHeight()));
        background.setPosition(Vector2.of((x + 1) * blockSize / 2., (y + 1) * blockSize / 2.));
        scene.getDynamicChildren().addFirst(background);

        List<Spawner> spawners = new LinkedList<>();
        List<Gate> gates = new LinkedList<>();

        for (Node n : scene.getAllChildren()) {
            switch (n) {
                case Enemy e -> {
                    m.getEnemies().add(e);
                    if (n instanceof Spawner spawner) {
                        spawners.add(spawner);
                    }
                }
                case Immortal i -> m.getPlayers().add(i);
                case Gate g -> gates.add(g);
                default -> {}
            }
        }

        for (Immortal i : m.getPlayers()) {
            for (Spawner spawner : spawners) {
                i.addChild(new Indicator("spawner/indicator.png", spawner, 50));
            }
        }

        for (Immortal i : m.getPlayers()) {
            for (Gate gate : gates) {
                i.addChild(new Indicator("gate_indicator.png", gate, 50));
            }
        }

        scene.getDynamicChildren().sort((e1, e2) -> ((e1 instanceof Character) ? 1 : 0) - ((e2 instanceof Character) ? 1 : 0));
    }

}
