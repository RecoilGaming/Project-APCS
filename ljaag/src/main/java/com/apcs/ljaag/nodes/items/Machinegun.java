package com.apcs.ljaag.nodes.items;


import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Vector2;

public class Machinegun extends Node2D<Node<?>> implements Usable {

    private static final double BULLET_SPEED = 300;
    private static final double SPREAD = 0.1;


    public Machinegun() {

    }

    public Machinegun(Vector2 offset) {
        setPosition(offset);
    }

    @Override
    public void onUse() {
        double angle = 0 + SPREAD * (Math.random() - 0.5);
        Game.getInstance().getScene().addChild(new Bullet(getParentAs(Node2D.class).getTransform().addPos(getParent().getParentAs(Node2D.class).getPosition()).rotate(angle).scale(0.1), Vector2.basis(angle + getParentAs(Node2D.class).getTransform().rot).mul(BULLET_SPEED).mul(Math.random() * 0.1 + 0.95)));
    }

    @Override
    public void onReady() { }
    
}
