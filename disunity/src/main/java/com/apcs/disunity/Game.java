package com.apcs.disunity;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.apcs.disunity.input.InputHandler;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.rendering.ScalableBuffer;
import com.apcs.disunity.scenes.Scenes;

/**
 * Renders the current scene to the screen
 * 
 * @author Qinzhao Li
 */
public class Game extends JPanel {

    /* ================ [ FIELDS ] ================ */

    // Singleton instance
    private static Game instance;

    // Game thread
    private Thread game;

    // Game dimensions
    private Vector2 dimensions;

    // Buffer for scaling the game
    private ScalableBuffer buffer;

    // Handles keystrokes from user
    private InputHandler input;

    // Is the host
    private boolean isHost;

    // Global transform to control viewport
    private Transform transform = new Transform();

    // Constructors
    public Game(Vector2 dimensions, String scene) {
        this(dimensions, scene, true);
    }

    public Game(Vector2 dimensions, String scene, boolean isHost) {
        // Set host status
        this.isHost = isHost;
        
        // Game dimensions
        this.dimensions = dimensions;

        // Panel background
        setBackground(Color.BLACK);
        
        // Double buffering
        buffer = new ScalableBuffer(dimensions);

        // Focus window for input
        setFocusable(true);
        requestFocusInWindow();

        // Attach input handler
        if(!isHost) {
            input = new InputHandler();
            addKeyListener(input);
            addMouseListener(input);
            addMouseMotionListener(input);
            addFocusListener(input);
        }

        // Set current scene
        Scenes.setScene(scene);

        // Set singleton instance
        instance = this;
    }

    /* ================ [ METHODS ] ================ */
    
    // Start game thread
    public void start() {
        game = new ThrottledLoopThread(
            Options.getMSPF(),
            () -> Scenes.updateScene(Options.getSPF()),
            this::repaint);
        game.start();
    }

    // Set global transform
    public void setTransform(Transform transform) { this.transform = transform; }

    // Set buffer size
    public void setBufferSize(Vector2 size) { buffer.setSize(size); }

    // Get buffer
    public ScalableBuffer getBuffer() { return buffer; }

    // Get instance
    public static Game getInstance() { return instance; }

    // Get host status
    public boolean isHost() { return isHost; }

    /* ================ [ JPANEL ] ================ */

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
        g.drawImage(image, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, null);
    }
}
