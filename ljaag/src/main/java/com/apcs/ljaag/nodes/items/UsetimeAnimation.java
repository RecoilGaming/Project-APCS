package com.apcs.ljaag.nodes.items;

import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite.RotationType;

public class UsetimeAnimation extends AnimatedSprite implements Usable {

    {
        setHidden(true);
        setRotationType(RotationType.UPRIGHT);
    }

    public UsetimeAnimation(String name, ImageLocation imageLocation, double... frameDurations) {
        super(name, imageLocation, frameDurations);
    }

    public UsetimeAnimation(String name, String path, double... frameDurations) {
        super(name, path, frameDurations);
    }
    @Override
    public void onUse() {
        reset();
        setHidden(false);
    }

    @Override
    public void onReady() { }

    

    
    

    
}
