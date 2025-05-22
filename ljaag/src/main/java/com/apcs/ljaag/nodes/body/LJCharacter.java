package com.apcs.ljaag.nodes.body;

import com.apcs.disunity.app.network.packet.SyncHandler;
import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.SelectorNode;
import com.apcs.disunity.game.nodes.sprite.AnimationSprite;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.nodes.twodim.Collider;
import com.apcs.disunity.game.physics.CollisionInfo;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.LJAAG;
import com.apcs.ljaag.nodes.indexed.InputVector;

/// base character of LJAAG. can be controlled by player.
/// demo of disunity
public class LJCharacter extends Body {
    @FieldChild
    private final SelectorNode<String, AnimationSprite> spriteSelector;
    {
        AnimationSprite s1, s2;
        spriteSelector = new SelectorNode<String, AnimationSprite>(
            s1 = new AnimationSprite("stand", "player/player.png", true, Double.MAX_VALUE),
            s2 = new AnimationSprite("run", "player/run.png", true,  0.15, 0.15, 0.15, 0.15, 0.15, 0.15));

        s1.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
        s2.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
    }

    private final InputVector input = new InputVector("input");

    public LJCharacter(int x, int y, int owner) { this(Vector2.of(x, y), owner); }

    public LJCharacter(Vector2 pos, int owner) {
        super(new Collider(8, 20));
        LJAAG.own(this, owner);
        setPos(pos);
    }

    static Sound coinSound = new Sound("sounds/smw_coin.wav");
    boolean collided = false;
    boolean collidedBefore = false;

    @Override
    public void update(double delta, Transform t) {

        collidedBefore = collided;
        collided = false;
        // movement
        if (isPlayer()) {
            setVel(input.get());
        }

        // sprite
        if (Math.abs(getVel().x) + Math.abs(getVel().y) < 0.1) {
            spriteSelector.select("stand");
        } else {
            spriteSelector.select("run");
        }

        // if (Math.abs(getVel().x) >= 0.1) {
        //     setScale(Vector2.of(getVel().x > 0 ? 1 : -1, 1));
        // }
        setRot(getVel().heading());

        super.update(delta, t);
    }

    @Override
    public void onCollision(CollisionInfo info) {
        if (!collidedBefore && !collided && isPlayer())
            coinSound.play();
        collided = true;
        Vector2 vel = getVel().mul(info.delta);
        addPos(vel.mul(-1));

        while (vel.length() > 0) {
            vel = vel.mul(0.5);
            addPos(vel);
            if (info.you.isColliding(info.me.setPos(getPos()))) {
                addPos(vel.mul(-1));
            }
        }
        spriteSelector.select("stand");
    }

    private boolean isPlayer() {
        return SyncHandler.getInstance() == null || SyncHandler.getInstance().getEndpointId() == owner;
    }
}