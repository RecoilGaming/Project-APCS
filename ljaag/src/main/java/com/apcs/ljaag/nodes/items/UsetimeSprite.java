package com.apcs.ljaag.nodes.items;

import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.sprite.Sprite.RotationType;
import com.apcs.disunity.math.Vector2;

public class UsetimeSprite extends Sprite implements Usable {
    {
        this.setHidden(true);
        this.setRotationType(RotationType.UPRIGHT);
    }

    public UsetimeSprite(String image) {
        super(image);
    }

    public UsetimeSprite(Vector2 offset, String image) {
        super(image);
        setPosition(offset);
    }

    @Override
    public void onUse() {
        setHidden(false);
    }

    @Override
    public void onReady() {
        setHidden(true);
    }
}
