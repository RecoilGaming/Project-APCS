package com.apcs.disunity;

import com.apcs.disunity.input.InputHandler;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.physics.Physics;
import com.apcs.disunity.rendering.ScalableBuffer;
import com.apcs.disunity.scenes.Scenes;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Contains and displays a scene on a loop
 *
 * @author Qinzhao Li
 */
public class Game extends JPanel {

    /* ================ [ FIELDS ] ================ */

    /** Whether this game is the host instance   */
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

    /** Handles keystrokes from user */
    private InputHandler input;

    /**
     * Creates a new Game with the given dimensions and scene ID
     * 
     * @param dimensions The dimensions of the game buffer
     * @param scene The scene ID
     */
    public Game(Vector2 dimensions, String scene) { this(dimensions, scene, true); }

    /**
     * Creates a new Game with the given dimensions, scene ID, and host status
     *
     * @param dimensions The dimensions of the game buffer
     * @param scene The scene ID
     * @param isHost Whether this game is the host instance
     */
    public Game(Vector2 dimensions, String scene, boolean isHost) {
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
        input = new InputHandler();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addFocusListener(input);

        // Set current scene
        Scenes.setScene(scene);

        // Create game thread
        game = new GameThread(
            this::update,
            this::draw
        );

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
        // Update scene
        Scenes.updateScene(Transform.IDENTITY, Options.getSPF()); // Delta value from configs

        // Update physics
         Physics.update(Options.getSPF());
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
        Scenes.drawScene(
            transform.move(dimensions.mul(0.5)) // Center on camera
        );

        BufferedImage image = buffer.getImage();
        int w = image.getWidth();
        int h = image.getHeight();

        // Draw to screen
        g.drawImage(
            image,
            (getWidth() - w) / 2,
            (getHeight() - h) / 2,
            w, h,
            null
        );
    }
}
