package com.apcs.ljaag.nodes;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.LJAAG;

public class DeathScreen extends Sprite {

	/* ================ [ FIELDS ] ================ */

	// Constructors
	public DeathScreen(Vector2 position) {
		super(
			new Transform(position, Vector2.of(1), 0),
			new ImageLocation("death.png")
		);
	}

	/* ================ [ NODE ] ================ */

	@Override
	public void update(double delta) {
		if (Inputs.getAction("restart")) {
			LJAAG.restart();
			Inputs.clearAction("restart");
		}

		super.update(delta);
	}
	
}
