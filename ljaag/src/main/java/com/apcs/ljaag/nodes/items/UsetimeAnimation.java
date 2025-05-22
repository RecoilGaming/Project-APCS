package com.apcs.ljaag.nodes.items;

import com.apcs.disunity.game.nodes.sprite.AnimationSprite;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;

public class UsetimeAnimation extends AnimationSprite implements Usable {

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
