package com.apcs.ljaag.nodes.controller;

import com.apcs.disunity.input.Inputs;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.controller.Controller;
import com.apcs.disunity.signals.Signals;

public class PaddleController extends Controller {

    /* ================ [ NODE ] ================ */

    @Override
    public void update(double delta) {
        Signals.trigger(Signals.getSignal(getId(), "walk"), new Vector2(
            (Inputs.getAction("left") ? -1 : 0) + (Inputs.getAction("right") ? 1 : 0),
            0
        ));
    }
    
}
