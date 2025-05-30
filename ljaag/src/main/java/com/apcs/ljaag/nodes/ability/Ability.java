package com.apcs.ljaag.nodes.ability;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.Character;

public class Ability {

	/* ================ [ TYPES ] ================ */

	public enum TriggerType {

		/* ================ [ VALUES ] ================ */

		SOURCE_POSITION, // Activate at player position
		SOURCE_DIRECTION, // Activate from player in the movement direction
		MOUSE_DIRECTION, // Activate from player in the mouse direction
		MOUSE_POSITION, // Activate at mouse position
		
	}


	/* ================ [ FIELDS ] ================ */

	// Trigger type
	private final TriggerType trigger;

	// Projectile components
	private final Collider collider;
	private final Area2D area2D;
	private final Sprite sprite;

	// Constructors
	public Ability(TriggerType trigger, Collider collider, Area2D area2D, Sprite sprite) {
		this.trigger = trigger;
		this.collider = collider;
		this.area2D = area2D;
		this.sprite = sprite;
	}

	/* ================ [ METHODS ] ================ */

	// Trigger the ability
	public void trigger(Character source, Transform transform) {
		switch (trigger) {
			case SOURCE_POSITION:
				Game.getInstance().getScene().addChild(instantiate(source, transform.pos, Vector2.ZERO));
				break;
			case SOURCE_DIRECTION:
				Game.getInstance().getScene().addChild(instantiate(source, transform.pos, Vector2.fromAngle(transform.rot)));
				break;
			case MOUSE_DIRECTION:
				Game.getInstance().getScene().addChild(instantiate(source, transform.pos, Inputs.getMousePos().normalized()));
				break;
			case MOUSE_POSITION:
				Game.getInstance().getScene().addChild(instantiate(source, Inputs.getMousePos(), Inputs.getMousePos().normalized()));
				break;
		}
	}

	// Instantiate ability projectile
	public Projectile instantiate(Character source, Vector2 position, Vector2 direction) {
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
	public void update(Character source, Projectile projectile, Transform offset, double delta) {
		;
	}
	
}
