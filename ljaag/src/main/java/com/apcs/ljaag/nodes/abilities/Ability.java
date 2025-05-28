package com.apcs.ljaag.nodes.abilities;

import java.util.UUID;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class Ability {

	/* ================ [ FIELDS ] ================ */

	// Projectile components
	private final Collider collider;
	private final Area2D area2D;
	private final Sprite sprite;

	// Constructors
	public Ability(Collider collider, Area2D area2D, Sprite sprite) {
		this.collider = collider;
		this.area2D = area2D;
		this.sprite = sprite;
	}

	/* ================ [ METHODS ] ================ */

	// Trigger the ability
	public void trigger(UUID source, Transform transform) {
		Game.getInstance().getScene().addChild(instantiate(source, transform.pos, Inputs.getMousePos().normalized()));
	}

	// Instantiate ability projectile
	public Projectile instantiate(UUID source, Vector2 position, Vector2 direction) {
		return new Projectile(
			source,
			new Transform(
				position,
				Vector2.of(1),
				direction.heading()
			),
			collider, area2D, sprite, this::update
		);
	}

	// Update ability projectile
	public void update(UUID source, Transform offset, double delta) {
		;
	}
	
}
