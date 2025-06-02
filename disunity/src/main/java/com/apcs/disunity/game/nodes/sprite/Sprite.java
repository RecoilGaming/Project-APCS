package com.apcs.disunity.game.nodes.sprite;

import com.apcs.disunity.app.network.packet.annotation.SyncedBoolean;
import com.apcs.disunity.app.network.packet.annotation.SyncedDouble;
import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

/**
 * A 2d node that renders an image
 * 
 * @author Qinzhao Li
 */
public class Sprite extends Node2D<Node<?>> {

    public void setRotationType(RotationType rotationType) { this.rotationType = rotationType; }

    public double getRotationOffset() {
        return rotationOffset;
    }

    public void setRotationOffset(double rotationOffset) {
        this.rotationOffset = rotationOffset;
    }

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

    @SyncedDouble
    private double rotationOffset = 0;

    public RotationType getRotationType() { return rotationType; }

    // Constructors
    public Sprite(ImageLocation imageLocation) { this.imageLocation = imageLocation; }

    public Sprite(String path) { this.imageLocation = new ImageLocation(path); }

    /* ================ [ NODE ] ================ */

    public ImageLocation getImageLocation() { return imageLocation; }

    public void setImageLocation(ImageLocation imageLocation) { this.imageLocation = imageLocation; }

    @Override
    public void draw(Transform offset) {

        offset = offset.rotate(rotationOffset);

        if (isHidden()) return;

        Transform localTransform = getTransform();
        // quadrant of the net angle, counting from 0
        double quadrant = (int) ((2*(localTransform.rot+offset.rot)/Math.PI % 4 + 4) % 4);

        switch (rotationType) {
            // cancel offset's rotation with opposite rotation
            case RotationType.LOCKED -> {
                localTransform = localTransform.rotateTo(-offset.rot);
            }
            // rotate normally
            case RotationType.NORMAL -> {}
            // cancel offset's rotation + flip
            case RotationType.BIDIRECTIONAL -> {
                localTransform = localTransform.rotateTo(-offset.rot);
                if (quadrant == 1 || quadrant == 2) {
                    localTransform = localTransform.scale(Vector2.of(-1,1));
                }
            }
            // rotate + flip
            case RotationType.UPRIGHT -> {
                if (quadrant == 1 || quadrant == 2) {
                    localTransform = localTransform.scale(Vector2.of(1,-1));
                }
            }
        }

        // Draw image to buffer
        Game.getInstance().getBuffer().drawImage(
            imageLocation.getImage(),
            offset.apply(localTransform).apply(new Transform(imageLocation.SIZE.mul(-0.5), Vector2.ONE, 0))
        );

        // Draw children
        super.draw(offset);
    }

    public void setHidden(boolean hidden) { this.hidden = hidden; }

    public boolean isHidden() { return hidden; }

}
