package com.apcs.disunity.app.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * A buffer that scales from its constructed dimensions.
 * 
 * @author Qinzhao Li
 */
public class ScalableBuffer {

    /* ================ [ FIELDS ] ================ */

    // Buffer image instance & graphics
    private BufferedImage image;
    private Graphics2D graphics;

    // Buffer scaling ratio
    private final Vector2 ratio;

    // Current buffer scale
    private double scale = 1;

    // Constructors
    public ScalableBuffer(Vector2 ratio) { this(ratio, ratio); }

    public ScalableBuffer(Vector2 ratio, Vector2 target) {
        this.ratio = ratio;
        this.setSize(target);
    }

    /* ================ [ METHODS ] ================ */

    // Refresh buffer
    public void setSize(Vector2 size) {
        size = Vector2.of(Math.min(size.x, size.y * ratio.x / ratio.y), Math.min(size.y, size.x * ratio.y / ratio.x));

        // Create buffer
        image = new BufferedImage(size.xi, size.yi, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();

        // Set scale
        scale = size.x / ratio.x;

        // White background
        graphics.setBackground(Color.WHITE);
    }

    // Clear buffer
    public void clear() { graphics.clearRect(0, 0, image.getWidth(), image.getHeight()); }

    // Getters
    public Vector2 getDimensions() { return ratio; }
    public double getScale() { return scale; }
    public BufferedImage getImage() { return image; }

    /* ================ [ GRAPHICS ] ================ */

    // Draw area
    public void drawArea(Area2D area) {
        Vector2 pos = Vector2.of(image.getWidth(), image.getHeight()).div(2).add(area.getTransform().pos.sub(area.size.mul(0.5)).mul(scale));
        Vector2 sca = area.size.mul(scale);
        graphics.setColor(Color.RED);
        graphics.fillRect(pos.xi, pos.yi, sca.xi, sca.yi);
    }

    // Draw image
    public void drawImage(BufferedImage img, Transform transform) {
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        at.concatenate(transform.toAT());

        graphics.drawImage(img, at, null);
    }

    // Draw image
    public void drawRect(int width, int height, Color c, Transform transform) {
       graphics.setPaint(c);
       graphics.fillRect((int) (scale * transform.pos.xi), (int) (scale * transform.pos.yi), (int) (scale * transform.scale.xi * width), (int) (scale * transform.scale.yi * height));
        
    }

}
