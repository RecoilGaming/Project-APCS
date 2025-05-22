package com.apcs.ljaag.nodes.items;

import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.nodes.twodim.Collider;
import com.apcs.disunity.game.physics.CollisionInfo;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class Bullet extends Body {

    public static final double LIFECYCLE = 5;

    private double lifetime = 0;

    @FieldChild
    private final Sprite sprite = new Sprite("weapons/bullet.png");

    public Bullet(Transform t, Vector2 vel) {
        super(new Collider(1, 1));
        setPos(t.pos);
        setRot(t.rot);
        setScale(t.scale);
        setVelocity(vel);
    }

    @Override
    public void update(Transform offset, double delta) {
        lifetime += delta;
        if (lifetime > LIFECYCLE) {
            // getParent().removeChild((Node) this);
            sprite.setHidden(true);
        }
        super.update(offset, delta);
    }

    @Override
    public void onCollision(CollisionInfo info) { }
    
}
