package com.apcs.ljaag.nodes.controller;

import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.controller.Controller;
import com.apcs.disunity.signals.Signals;

public class BallController extends Controller {

    /* ================ [ FIELDS ] ================ */

    // Direction
    private Vector2 dir = new Vector2(1, -1);

    /* ================ [ NODE ] ================ */

    @Override
    public void update(double delta) {
        Signals.trigger(Signals.getSignal(getId(), "walk"), dir.normalized());
    }
    
}
