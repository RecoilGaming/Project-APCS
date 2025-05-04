package com.apcs.disunity;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import com.apcs.disunity.math.Vector2;

/**
 * An app window that holds a game
 * 
 * @author Qinzhao Li
 */
public class App extends JFrame {

    /* ================ [ FIELDS ] ================ */

    /**
     * Creates a new App with dimensions and a game
     * 
     * @param width The width of the window
     * @param height The height of the window
     * @param game The game to display
     */
    public App(String title, int width, int height, Game game) {
        // Window setup
        setTitle(title);
        setSize(width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Window resize listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Rectangle bounds = getContentPane().getBounds();
                game.setBufferSize(
                    Vector2.of(bounds.width, bounds.height)
                );
            }
        });

        // Add game
        add(game);
        
        // Display window
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    
}
