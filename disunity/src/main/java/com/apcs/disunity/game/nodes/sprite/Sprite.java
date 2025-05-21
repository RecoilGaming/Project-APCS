package com.apcs.disunity.game.nodes.sprite;

import java.awt.image.BufferedImage;

import com.apcs.disunity.app.network.packet.annotation.SyncedBoolean;
import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.app.rendering.Util;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Transform;

/**
 * A 2d node that renders an image
 * 
 * @author Qinzhao Li
 */
public class Sprite extends Node2D<Node<?>> {

    public void setRotationType(RotationType rotationType) {
        this.rotationType = rotationType;
    }

    public enum RotationType {
        NORMAL,
        LOCKED,
        BIDIRECTIONAL,
        UPRIGHT
    }

    /* ================ [ FIELDS ] ================ */

    // Sprite image id
    @FieldChild
    private ImageLocation imageLocation;

    @SyncedBoolean
    private boolean hidden;

    @SyncedObject
    private RotationType rotationType = RotationType.NORMAL;

    public RotationType getRotationType() {
        return rotationType;
    }

    // Constructors
    public Sprite(ImageLocation imageLocation) { this.imageLocation = imageLocation; }

    public Sprite(String path) { this.imageLocation = new ImageLocation(path); }

    /* ================ [ NODE ] ================ */

    public ImageLocation getImageLocation() { return imageLocation; }

    public void setImageLocation(ImageLocation imageLocation) { this.imageLocation = imageLocation; }

    @Override
    public void draw(Transform offset) {

        if (isHidden()) return;

        // Load sprite image
        BufferedImage img = imageLocation.getImage();

        switch (rotationType) {
            case RotationType.LOCKED -> {}
            case RotationType.BIDIRECTIONAL -> {
                double angle = getTransform().rot + offset.rot;
                if (Math.abs(angle) > Math.PI / 2) {
                    img = Util.flipHorizontally(img);
                }
            }
            case RotationType.NORMAL -> {
                img = Util.rotate(img, getTransform().rot + offset.rot);
            }
            case RotationType.UPRIGHT -> {
                double angle = getTransform().rot + offset.rot;
                if (Math.abs(angle) > Math.PI / 2) {
                    img = Util.flipVertically(img);
                }
                img = Util.rotate(img, getTransform().rot + offset.rot);
            }
        }

        // Draw image to buffer
        Game.getInstance().getBuffer().drawImage(img, getTransform().apply(offset));

        // Draw children
        super.draw(offset);
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

}
