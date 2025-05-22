package com.apcs.ljaag.nodes.items;

import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.Node;

public class UsetimeSound extends Node implements Usable {

    private final Sound s;

    public UsetimeSound(String s) {
        this(new Sound(s));
    }

    public UsetimeSound(Sound s) {
        this.s = s;
    }

    @Override
    public void onUse() {
        s.play();
    }

    @Override
    public void onReady() { }

}
