package com.apcs.ljaag.nodes;

import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class Indicator extends Sprite {

    private final Node2D target;
    private final double distance;

    public Indicator(String path, Node2D target, double distance) {
        super(path);
        this.target = target;
        this.distance = distance;
        setRotationType(RotationType.NORMAL);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        Vector2 dir = target.getGlobalTrans().pos.sub(getGlobalTrans().pos.sub(getPosition()));
        Vector2 pos = dir.normalized().mul(distance);
        setPosition(pos);
        setRotation(pos.heading());
        setHidden(target.getScale().length() == 0 || target.isHidden() || dir.length() < distance * 5);

    }

    @Override
    public void draw(Transform offset) {
        super.draw(new Transform(offset.pos, offset.scale, 0));
    }

}
