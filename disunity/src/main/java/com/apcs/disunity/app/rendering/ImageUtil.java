package com.apcs.disunity.app.rendering;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * A utility class for image manipulation
 * 
 * @author Sharvil Phadke
 */
public class ImageUtil {

    /* ================ [ METHODS ] ================ */

    // Rotates an image by the given angle
    public static BufferedImage rotate(BufferedImage img, double rad) {
        int width = img.getWidth();
        int height = img.getHeight();

        AffineTransform transform = new AffineTransform();
        transform.rotate(rad, width / 2.0, height / 2.0);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        return op.filter(img, result);
    }

    // Flips an image horizontally
    public static BufferedImage flipHorizontally(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-width, 0);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        return op.filter(img, result);
    }

    // Flips an image vertically
    public static BufferedImage flipVertically(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        AffineTransform transform = new AffineTransform();
        transform.scale(1, -1);
        transform.translate(0, -height);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        return op.filter(img, result);
    }

}
