package com.apcs.ljaag.nodes.items;


import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public abstract class UsetimeItem extends Sprite {

    private final double usetime;

    private Vector2 offset;

    private final String useAction;
    
    private double cooldown = 0;
    
    {
        this.setHidden(true);
        this.setRotationType(RotationType.UPRIGHT);
    }

    public UsetimeItem(double distance, String image, double usetime, String useAction) {
        this(Vector2.of(distance, 0), image, usetime, useAction);

    }

    public UsetimeItem(Vector2 offset, String image, double usetime, String useAction) {
        super(image);
        this.offset = offset;
        this.usetime = usetime;
        this.useAction = useAction;
    }

    @Override
    public void update(double delta, Transform t) {
        Vector2 mouseDir = Inputs.getMousePos().sub(getParent(Node2D.class).getPos()).normalized();
        Vector2 pos = mouseDir.mul(offset.x).add(Vector2.of(-mouseDir.y, mouseDir.x).mul(offset.y));
        setPos(pos);
        setRot(pos.heading());
        if (cooldown <= 0) {
            setHidden(true);
            if (Inputs.getAction(useAction)) {
                setHidden(false);
                onUse();
                cooldown = usetime;
            }
        } else {
            cooldown -= delta;
        }

        super.update(delta, t);
        
    }

    @Override
    public void draw(Transform offset) {
        offset = new Transform(offset.pos, offset.scale, 0);
        super.draw(offset);
    }

    abstract void onUse();
    
}
