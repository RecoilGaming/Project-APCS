package com.apcs.ljaag.nodes.body;


import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.nodes.twodim.Collider;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.game.physics.CollisionInfo;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.LJAAG;
import com.apcs.disunity.game.nodes.Scene;

public class Shotgun extends Sprite {

    private static final double USE_TIME = 0.25;

    private static final double DISTANCE = 20;

    private Vector2 offset;

    private static final double BULLET_SPEED = 100;

    static Sound boom = new Sound("boomstick.wav");
    
    private double cooldown = 0;
    
    {
        this.setHidden(true);
        this.setRotationType(RotationType.UPRIGHT);
    }

    public Shotgun(String image) {
        this(Vector2.of(DISTANCE, 0), image);
    }

    public Shotgun(Vector2 offset, String image) {
        super(image);
        this.offset = offset;
    }

    public Shotgun(double yOffset, String image) {
        this(Vector2.of(DISTANCE, yOffset), image);
    }

    @Override
    public void update(double delta) {
        if (cooldown <= 0) {
            if (Inputs.getAction("fire")) {
                Vector2 mouseDir = Inputs.getMousePos().sub(getParentAs(Node2D.class).getPos()).normalized();
                setRot(mouseDir.heading());
                setPos(mouseDir.mul(offset.x).add(Vector2.of(-mouseDir.y, mouseDir.x).mul(offset.y)));
                setHidden(false);
                cooldown = USE_TIME;
                boom.play();
                getRootAs(Scene.class).addChild(new Bullet(getTransform().addPos(getParentAs(Node2D.class).getPos()), mouseDir.mul(BULLET_SPEED)));
                
            } else {
                setHidden(true);
            }
        } else {
            cooldown -= delta;
        }
        
    }

    @Override
    public void draw(Transform offset) {
        offset = new Transform(offset.pos, offset.scale, 0);
        super.draw(offset);
    }
    
}
