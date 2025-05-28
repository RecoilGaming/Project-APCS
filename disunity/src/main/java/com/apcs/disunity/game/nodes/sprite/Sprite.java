package com.apcs.disunity.game.nodes.sprite;

import java.awt.geom.AffineTransform;

import com.apcs.disunity.app.network.packet.annotation.SyncedBoolean;
import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
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

    public void setRotationType(RotationType rotationType) { this.rotationType = rotationType; }

    public enum RotationType {
        NORMAL, LOCKED, BIDIRECTIONAL, UPRIGHT
    }

    /* ================ [ FIELDS ] ================ */

    // Sprite image id
    @FieldChild
    private ImageLocation imageLocation;

    @SyncedBoolean
    private boolean hidden;

    @SyncedObject
    private RotationType rotationType = RotationType.NORMAL;

    public RotationType getRotationType() { return rotationType; }

    // Constructors
    public Sprite(ImageLocation imageLocation) { this.imageLocation = imageLocation; }

    public Sprite(String path) { this.imageLocation = new ImageLocation(path); }

    /* ================ [ NODE ] ================ */

    public ImageLocation getImageLocation() { return imageLocation; }

    public void setImageLocation(ImageLocation imageLocation) { this.imageLocation = imageLocation; }

    @Override
    public void draw(Transform offset) {

        if (isHidden()) return;

        AffineTransform imageTransform = new AffineTransform();
        // apply ancestor transform after child transform
        imageTransform.preConcatenate(offset.toAT());

        Transform localTransform = getTransform();
        // quadrant of the net angle, counting from 0
        double quadrant = (int) ((2*(localTransform.rot+offset.rot)/Math.PI % 4 + 4) % 4);

        imageTransform.translate(localTransform.pos.x, localTransform.pos.y);

        switch (rotationType) {
            // cancel offset's rotation with opposite rotation
            case RotationType.LOCKED -> {
                imageTransform.rotate(-offset.rot);
            }
            // rotate normally
            case RotationType.NORMAL -> {
                imageTransform.rotate(localTransform.rot);
            }
            // cancel offset's rotation + flip
            case RotationType.BIDIRECTIONAL -> {
                imageTransform.rotate(-offset.rot);
                if (quadrant == 1 || quadrant == 2) {
                    imageTransform.scale(-1,1);
                }
            }
            // rotate + flip
            case RotationType.UPRIGHT -> {
                imageTransform.rotate(localTransform.rot);
                if (quadrant == 1 || quadrant == 2) {
                    imageTransform.scale(1,-1);
                }
            }
        }

        // scale before rotation
        imageTransform.scale(localTransform.scale.x, localTransform.scale.y);
        // move center to origin
        imageTransform.translate(-imageLocation.SIZE.x/2, -imageLocation.SIZE.y/2);

        // Draw image to buffer
        Game.getInstance().getBuffer().drawImage(imageLocation.getImage(), imageTransform);

        // Draw children
        super.draw(offset);
    }

    public void setHidden(boolean hidden) { this.hidden = hidden; }

    public boolean isHidden() { return hidden; }

}
