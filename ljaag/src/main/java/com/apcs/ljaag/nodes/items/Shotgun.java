package com.apcs.ljaag.nodes.items;


import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Vector2;

public class Shotgun extends Node2D<Node<?>> implements Usable {
    
    private static final double SPREAD = 0.1;

    private static final double BULLET_COUNT = 3;

    private static final double BULLET_SPEED = 100;


    public Shotgun() {

    }

    public Shotgun(Vector2 offset) {
        setPos(offset);
    }

    @Override
    public void onUse() {
        double range = BULLET_COUNT * SPREAD;
        double startingAngle = - range / 2;
        for (int i = 0; i < BULLET_COUNT; i++) {
            double angle = startingAngle + SPREAD * (Math.random() - 0.5 + i);
            getRootAs(Scene.class).addChild(new Bullet(getParentAs(Node2D.class).getTransform().addPos(getParent().getParentAs(Node2D.class).getPos()).rotate(angle).scale(0.1), Vector2.basis(angle + getParentAs(Node2D.class).getTransform().rot).mul(BULLET_SPEED).mul(Math.random() * 0.1 + 0.95)));
        }
    }

    @Override
    public void onReady() { }
    
}
