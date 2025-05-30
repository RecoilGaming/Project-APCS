package com.apcs.ljaag.nodes.ability;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.Character;

public class Ability {

	/* ================ [ TYPES ] ================ */

	public enum TriggerType {

		SOURCE_POSITION, // Activate at player position
		SOURCE_DIRECTION, // Activate from player in the movement direction
		MOUSE_DIRECTION, // Activate from player in the mouse direction
		MOUSE_POSITION, // Activate at mouse position
		
	}

	/* ================ [ FIELDS ] ================ */

	// Ability data
	private final AbilityData data;

	// Last used timestamp
	private double lastUsed = 0;

	// Constructors
	public Ability(AbilityData data) {
		this.data = data;
	}

	/* ================ [ METHODS ] ================ */

	// Use the ability
	public boolean use(Character source) {
		if (System.currentTimeMillis() - lastUsed < data.cooldown * 1000) {
			return false;
		}

		lastUsed = System.currentTimeMillis();

		switch (data.trigger) {
			case SOURCE_POSITION:
				Game.getInstance().getScene().addChild(instantiate(source, source.getPosition(), Vector2.ZERO));
				break;
			case SOURCE_DIRECTION:
				Game.getInstance().getScene().addChild(instantiate(source, source.getPosition(), Vector2.fromAngle(source.getRotation())));
				break;
			case MOUSE_DIRECTION:
				Game.getInstance().getScene().addChild(instantiate(source, source.getPosition(), Inputs.getMousePos().sub(source.getPosition()).normalized()));
				break;
			case MOUSE_POSITION:
				Game.getInstance().getScene().addChild(instantiate(source, Inputs.getMousePos(), Inputs.getMousePos().sub(source.getPosition()).normalized()));
				break;
			default:
				break;
		}

		return true;
	}

	// Instantiate ability projectile
	public Projectile instantiate(Character source, Vector2 position, Vector2 direction) {
		return new Projectile(
			source,
			new Transform(
				position.add(data.offset),
				Vector2.of(1),
				direction.heading()
			),
			data.collider.get(),
			data.area2D.get(),
			data.sprite.get(),
			data::onCollision,
			data::update
		);
	}
	
}
