package com.apcs.ljaag.nodes.abilities;

import java.util.UUID;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;

public class Ability {

	/* ================ [ FIELDS ] ================ */
	
	// Projectile data
	private final ProjectileData projectile;

	// Constructors
	public Ability(ProjectileData projectile) {
		this.projectile = projectile;
	}

	/* ================ [ METHODS ] ================ */

	// Trigger the ability
	public void trigger(UUID source) {
		Game.getInstance().getScene().addChild(projectile.instantiate(Inputs.getMousePos().normalized()));
	}
	
}
