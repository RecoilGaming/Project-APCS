package com.apcs.disunity.game.nodes.sprite;

import com.apcs.disunity.app.resources.Image;
import com.apcs.disunity.app.resources.Resources;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Vector2;

import java.awt.image.BufferedImage;

/// a composite location determined by image path and how to slice it
public class ImageLocation extends Node<Node<?>> {
    public final String PATH;
    public final Vector2 POS;
    public final Vector2 SIZE;

    public ImageLocation(String path, int x, int y, int w, int h) {
        PATH = path;
        POS = new Vector2(x, y);
        SIZE = new Vector2(w, h);
    }

    public ImageLocation(String path) {
        PATH = path;
        BufferedImage img = Resources.loadResource(PATH, Image.class).getBuffer();
        POS = Vector2.ZERO;
        SIZE = Vector2.of(img.getWidth(), img.getHeight());
    }

    public BufferedImage getImage() {
        BufferedImage img = Resources.loadResource(PATH, Image.class).getBuffer();
        return img.getSubimage(POS.xi, POS.yi, SIZE.xi, SIZE.yi);
    }
}
