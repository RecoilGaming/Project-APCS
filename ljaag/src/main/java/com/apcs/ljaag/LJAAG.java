package com.apcs.ljaag;

import com.apcs.disunity.App;
import com.apcs.disunity.Game;
import com.apcs.disunity.input.Inputs;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.disunity.nodes.Node2D;
import com.apcs.disunity.nodes.body.Body;
import com.apcs.disunity.nodes.sprite.Sprite;
import com.apcs.disunity.scenes.Scenes;
import com.apcs.ljaag.nodes.action.WalkAction;
import com.apcs.ljaag.nodes.controller.BallController;
import com.apcs.ljaag.nodes.controller.PaddleController;

/**
 * Untitled game
 * 
 * @author Aayushya Patel
 * @author Qinzhao Li
 * @author Sharvil Phadke
 * @author Toshiki Takeuchi
 */
public class LJAAG {

    /* ================ [ DRIVER ] ================ */

    public static void main(String[] args) {
        // Import keybinds from a JSON file
        Inputs.fromJSON("keybinds.json");

        // Main menu scene
        Scenes.addScene("menu", new Node2D(
            new Sprite("background.png"),
            new Sprite("title.png"),
            new Sprite(
                "play.png",
                new Transform().setPos(new Vector2(-22.5, 13.5))
            )
        ));

        // Actual game scene
        Scenes.addScene("level1", new Node2D(
            new Sprite("background.png"),
            new Body(
                new Transform().setPos(new Vector2(0, 15.5)),
                new Sprite("ball.png"),
                new BallController(),
                new WalkAction()
            ),
            new Body(
                new Transform().setPos(new Vector2(0, 17.5)),
                new Sprite("paddle.png"),
                new PaddleController(),
                new WalkAction()
            )
        ));

        // Create game
        Game game = new Game(
            Vector2.of(80, 45),
            "level1"
        );

        // Create app
        new App(
            "Breakout - Qinzhao Li", 
            800, 450,
            game
        );

        // Start game
        game.start();
    }
    
}