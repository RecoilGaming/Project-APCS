package com.apcs.ljaag.nodes.items;


import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.game.physics.CollisionLayer;
import com.apcs.disunity.math.Vector2;

public class Shotgun extends UsetimeItem {

    private static final double USE_TIME = 1.;

    private static final double DISTANCE = 20;
    
    private static final double SPREAD = 0.1;

    private static final String ACTION = "fire";

    private static final String WAV_PATH = "boomstick.wav";

    private static final double BULLET_COUNT = 3;

    private static final double BULLET_SPEED = 100;

    private static Sound boom = new Sound(WAV_PATH);

    
    {
        this.setHidden(true);
        this.setRotationType(RotationType.UPRIGHT);
    }

    public Shotgun(String image) {
        this(Vector2.of(DISTANCE, 0), image);
    }

    public Shotgun(Vector2 offset, String image) {
        super(offset, image, USE_TIME, ACTION);
    }

    public Shotgun(double yOffset, String image) {
        this(Vector2.of(DISTANCE, yOffset), image);
    }

    @Override
    void onUse() {
        boom.play();
        double range = BULLET_COUNT * SPREAD;
        double startingAngle = - range / 2;
        for (int i = 0; i < BULLET_COUNT; i++) {
            double angle = startingAngle + SPREAD * (Math.random() - 0.5 + i);
            getRoot(Scene.class).addChild(new Bullet(getTransform().addPos(getParent(Node2D.class).getPos()).rotate(angle), Vector2.basis(angle + getTransform().rot).mul(BULLET_SPEED).mul(Math.random() * 0.1 + 0.95)));
        }
    }
    
}
