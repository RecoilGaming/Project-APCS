package com.apcs.disunity.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

// TODO: Document

/**
 * An image to be used in the game
 * 
 * @author Qinzhao Li
 * @author Sharvil Phadke
 */
public class Image {

    static {
        Resources.loaders.put(Image.class, Image::new);
    }

    private final BufferedImage buffer;

    public Image(BufferedImage image) { this.buffer = image; }

    public Image(InputStream imageData) {
        try (imageData) { buffer = ImageIO.read(imageData); }
        catch (IOException e) { throw new RuntimeException("Unable to read image files"); }
    }

    public BufferedImage getBuffer() { return buffer; }
    
}
