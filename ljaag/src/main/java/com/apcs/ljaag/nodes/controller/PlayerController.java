package com.apcs.ljaag.nodes.controller;

import com.apcs.disunity.input.Inputs;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.controller.Controller;
import com.apcs.disunity.signals.Signals;

/** 
 * A controller that is controlled by player inputs
 * 
 * @author Qinzhao Li
 */
public class PlayerController extends Controller {

    /* ================ [ NODE ] ================ */

    @Override
    public void update(double delta) {
        // Trigger walking
        Vector2 walkDir = new Vector2(
            (Inputs.getAction("left") ? -1 : 0) + (Inputs.getAction("right") ? 1 : 0),
            (Inputs.getAction("up") ? -1 : 0) + (Inputs.getAction("down") ? 1 : 0)
        ).normalized();

        Signals.trigger(Signals.getSignal(getId(), "walk"), walkDir);

        // Trigger animations
        if (walkDir.length() > 0) {
            Signals.trigger(Signals.getSignal(getId(), "animate"), "run");
        } else {
            Signals.trigger(Signals.getSignal(getId(), "animate"), null);
        }
    }
    
}
