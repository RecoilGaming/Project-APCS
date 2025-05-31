package com.apcs.ljaag.nodes.items;


import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class UsetimeItem<T extends Node<?> & Usable> extends Node2D<T> {

    private final double usetime;

    private Vector2 offset;

    private final String useAction;
    
    private double cooldown = 0;

    public UsetimeItem(double distance, double usetime, String useAction, T... children) {
        this(Vector2.of(distance, 0), usetime, useAction, children);

    }

    public UsetimeItem(Vector2 offset, double usetime, String useAction, T... children) {
        super(children);
        this.offset = offset;
        this.usetime = usetime;
        this.useAction = useAction;
    }

    @Override
    public void update(double delta) {
        Vector2 mouseDir = Inputs.getMousePos().sub(getParentAs(Node2D.class).getPosition()).normalized();
        Vector2 pos = mouseDir.mul(this.offset.x).add(Vector2.of(-mouseDir.y, mouseDir.x).mul(this.offset.y));
        setPosition(pos);
        setRotation(pos.heading());
        if (cooldown <= 0) {
            for (Usable u : getAllChildren()) {
                u.onReady();
            }
            if (Inputs.getAction(useAction)) {
                for (Usable u : getAllChildren()) {
                    u.onUse();
                }
                cooldown = usetime;
            }
        } else {
            cooldown -= delta;
        }

        super.update(delta);
        
    }
    
    @Override
    public void draw(Transform offset) {
        offset = new Transform(offset.pos, offset.scale, 0);
        super.draw(offset);
    }
    
}
