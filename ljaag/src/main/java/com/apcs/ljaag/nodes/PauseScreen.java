package com.apcs.ljaag.nodes;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class PauseScreen extends Sprite {

	/* ================ [ FIELDS ] ================ */

	// Constructors
	public PauseScreen(Vector2 position) {
		super(
			new Transform(position, Vector2.of(1), 0),
			new ImageLocation("pause.png")
		);
	}

	/* ================ [ NODE ] ================ */

	private boolean unpaused = false;

	@Override
	public void update(double delta) {
		if (!Inputs.getAction("pause")) {
			unpaused = true;
		} else if (unpaused) {
			Game.getInstance().unpause();
			hide();
			disable();
		}

		super.update(delta);
	}
	
}