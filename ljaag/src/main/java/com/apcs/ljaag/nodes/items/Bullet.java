package com.apcs.ljaag.nodes.items;

import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.characters.Character;

public class Bullet extends Body {

    public static final double LIFECYCLE = 5;

    public static final double BULLET_DAMAGE = 20;

    private double lifetime = 0;

    @FieldChild
    private final Sprite sprite = new Sprite("weapons/bullet.png");

    public Bullet(Transform t, Vector2 vel) {
        super(new Collider(1, 1), new Area2D(1,1));
        setPosition(t.pos);
        setRotation(t.rot);
        setScale(t.scale);
        setVelocity(vel);
    }

    @Override
    public void update(Transform offset, double delta) {
        if (lifetime > LIFECYCLE) {
            // getParent().removeChild((Node) this);
            sprite.setHidden(true);
            return;
        }
        super.update(offset, delta);
    }



    // TODO: set proper collision layer
    // currently, false collision is detected every time gun is shot,
    // because bullets are close enough to collide with each other.
    // setting the collision layer/mask to proper value will solve this issue.
    @Override
    public void onBodyEntered(BodyEntered signal) {
        if (signal.body instanceof Character ch) {
            ch.modifyHealth((int) -BULLET_DAMAGE);
        }
    }

}
