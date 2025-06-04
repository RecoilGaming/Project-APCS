package com.apcs.ljaag.nodes;

import java.util.function.Supplier;

import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.immortals.Immortal;

public class Gate extends Body {

    public static final int CLOSED_FRAME = 2;
    public static final int OPEN_FRAME = 1;

    /* ================ [ NODES ] ================ */

	@FieldChild
    protected final AnimatedSprite sprite = new AnimatedSprite("closed", "gate.png", true, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    
    {
        sprite.setRotationType(Sprite.RotationType.LOCKED);
        sprite.setFrame(CLOSED_FRAME);
        sprite.setScale(Vector2.of(2));
        sprite.show();
    }

    private final Supplier<Boolean> shouldBeOpen;
    private final Runnable usedAction;

    

    public Gate(Transform t, Supplier<Boolean> shouldBeOpen, Runnable usedAction, Collider collider, Area2D area2d, Node<?>... children) {
        super(t, collider, area2d, children);
        this.shouldBeOpen = shouldBeOpen;
        this.usedAction = usedAction;
    }

    private boolean firstTime = true;

    @Override
    public void update(double delta) {
        super.update(delta);
        boolean open = shouldBeOpen.get();
        sprite.setFrame(open ? OPEN_FRAME : CLOSED_FRAME);
        if (open && firstTime) {
            new Sound("sounds/portal_activated.wav").play();
            firstTime = false;
        }
        
    }

    @Override
    public void onBodyEntered(BodyEntered signal) {
        if (shouldBeOpen.get() && (signal.body instanceof Immortal)) {
            new Sound("sounds/teleport.wav").play();
            usedAction.run();
        }
    }

    @Override
    public void setPosition(Vector2 pos) { }
    
}
