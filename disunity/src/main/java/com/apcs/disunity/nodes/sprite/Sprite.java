package com.apcs.disunity.nodes.sprite;

import java.awt.image.BufferedImage;

import com.apcs.disunity.Game;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.nodes.Node;
import com.apcs.disunity.nodes.Node2D;
import com.apcs.disunity.resources.Image;
import com.apcs.disunity.resources.Resources;

/**
 * A 2d node that renders an image
 * 
 * @author Qinzhao Li
 */
public class Sprite extends Node2D {

    /* ================ [ FIELDS ] ================ */

    /** The image id */
    private String image;

    /**
     * Create a new Sprite with the given image
     *
     * @param image The image id
     */
    public Sprite(String image) { super(); this.image = image; }
    /**
     * Create a new Sprite with the given image and children
     *
     * @param image The image id
     * @param children The children of this node
     */
    public Sprite(String image, Node... children) { super(children); this.image = image; }
    /**
     * Create a new Sprite with the given image, transform, and children
     *
     * @param image The image id
     * @param transform The transform of this node
     * @param children The children of this node
     */
    public Sprite(String image, Transform transform, Node... children) { super(transform, children); this.image = image; }
    /**
     * Create a new Sprite with the given image, transform, visibility, and children
     *
     * @param image The image id
     * @param transform The transform of this node
     * @param visible Whether or not the node is visible
     * @param children The children of this node
     */
    public Sprite(String image, Transform transform, boolean visible, Node... children) { super(transform, visible, children); this.image = image; }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the image id
     *
     * @return The image id
     */
    public String getImage() { return image; }

    /**
     * Change the image id
     *
     * @param image The image id
     */
    public void setImage(String image) { this.image = image; }

    /* ================ [ NODE ] ================ */

    /**
     * Draw the node and its children
     * 
     * @param offset The offset of the node
     */
    @Override
    public void draw(Transform offset) {
        // Load sprite image
        BufferedImage img = Resources.loadResource(getImage(), Image.class).getBuffer();

        // Draw image to buffer
        Game.getInstance().getBuffer().drawImage(img, transform.apply(offset));

        // Draw children
        super.draw(offset);
    }

}
