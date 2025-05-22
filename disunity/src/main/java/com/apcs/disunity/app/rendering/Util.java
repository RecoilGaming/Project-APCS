package com.apcs.disunity.app.rendering;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Util {
    public static BufferedImage rotate(BufferedImage img, double angleRad) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(angleRad, img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotation = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        return op.filter(img, rotation);
    }

    public static BufferedImage flipHorizontally(BufferedImage img) {
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-img.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotation = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        return op.filter(img, rotation);
    }

    public static BufferedImage flipVertically(BufferedImage img) {
        AffineTransform transform = new AffineTransform();
        transform.scale(1, -1);
        transform.translate(0, -img.getHeight());
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotation = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        return op.filter(img, rotation);
    }
}
