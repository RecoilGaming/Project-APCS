package com.apcs.disunity.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.apcs.disunity.app.Options;
import com.apcs.disunity.app.input.InputHandler;
import com.apcs.disunity.app.rendering.ScalableBuffer;
import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.selector.Selector;
import com.apcs.disunity.game.signals.SignalBus;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * runs the selected scene, and render it
 *
 * @author Qinzhao Li
 */
public class Game extends JPanel {
    static {
        Sound.init();
    }

    /* ================ [ FIELDS ] ================ */

    /** Whether this game is the host instance */
    public final boolean isHost;

    /** The singleton instance of the game */
    private static Game instance;

    /** The thread that the game runs on */
    private GameThread game;

    /** The dimensions of the game buffer */
    private Vector2 dimensions;

    /** The global transform of the viewport */
    private Transform transform = new Transform();

    /** The buffer that draws and scales the game */
    private ScalableBuffer buffer;

    private final Selector<String, Scene> scenes = new Selector<>(new Scene("default"));

    private SignalBus globalSignal;

    /**
     * Creates a new Game with the given dimensions and scene ID
     * 
     * @param dimensions The dimensions of the game buffer
     */
    public Game(Vector2 dimensions) {
        this(dimensions, true);
    }

    /**
     * Creates a new Game with the given dimensions, scene ID, and host status
     *
     * @param dimensions The dimensions of the game buffer
     * @param isHost     Whether this game is the host instance
     */
    public Game(Vector2 dimensions, boolean isHost) {
        // Set host status and dimensions
        this.isHost = isHost;
        this.dimensions = dimensions;

        // Clear panel background
        setBackground(Color.BLACK);

        // Initialize scalable buffer
        buffer = new ScalableBuffer(dimensions);

        // Focus window to capture inputs
        setFocusable(true);
        requestFocusInWindow();

        // Attach input handler
        InputHandler input = new InputHandler();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addFocusListener(input);

        // Create game thread
        game = new GameThread(this::update, this::draw);

        // Set singleton instance
        instance = this;
    }

    /* ================ [ GAME ] ================ */

    /** Start the game loop */
    public void start() {
        game.start();
    }

    /** Update the game */
    private void update() {
        scenes.getSelected().update(Options.getSPF());
    }

    /** Draw the game */
    private void draw() {
        repaint();
    }

    /* ================ [ METHODS ] ================ */

    /**
     * Set the global transform of the viewport
     * 
     * @param transform The new transform
     */
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    /**
     * Gets the global transform of the viewport
     * 
     * @return transform The transform
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     * Set the size of the game buffer
     * 
     * @param size The new size
     */
    public void setBufferSize(Vector2 size) {
        buffer.setSize(size);
    }

    /**
     * Get the game buffer for drawing
     * 
     * @return The game buffer
     */
    public ScalableBuffer getBuffer() {
        return buffer;
    }

    /**
     * Get the singleton instance of the game
     * 
     * @return The game instance
     */
    public static Game getInstance() {
        return instance;
    }

    public void addScene(String name, Node<?>... children) {
        addScene(new Scene(name, children));
    }

    public void addScene(Scene s) {
        scenes.add(s);
    }

    public void setScene(String name) {
        globalSignal = scenes.select(name).GLOBAL_SIGNAL_BUS;
    }

    public SignalBus getGlobalSignal() {
        return globalSignal;
    }

    /* ================ [ JPANEL ] ================ */

    /**
     * Draw the game from the buffer
     * 
     * @param g The graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Clear buffer
        buffer.clear();

        // Update buffer
        scenes.getSelected().draw(transform.addPos(dimensions.mul(0.5)));

        BufferedImage image = buffer.getImage();
        int w = image.getWidth();
        int h = image.getHeight();

        // Draw to screen
        g.drawImage(image, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, null);
    }
}
