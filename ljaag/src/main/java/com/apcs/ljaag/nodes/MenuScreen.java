package com.apcs.ljaag.nodes;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class MenuScreen extends Sprite {

	/* ================ [ FIELDS ] ================ */

	// Constructors
	public MenuScreen(Vector2 position) {
		super(
			new Transform(position, Vector2.of(1), 0),
			new ImageLocation("title.png")
		);
	}

	/* ================ [ NODE ] ================ */

	@Override
	public void update(double delta) {
		Game.getInstance().setTransform(new Transform());
		if (Inputs.getAction("restart") && !Inputs.getAction("menu")) {
			Game.getInstance().setScene("levels/0_start.txt");
		}

		super.update(delta);
	}
	
}
