package com.apcs.ljaag.nodes.abilities;

import java.util.UUID;

import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.twodim.Node2D;

public class ProjectileAbility extends Ability {

	/* ================ [ FIELDS ] ================ */

	// Projectile
	private final Node2D projectile;

	// Constructors
	public ProjectileAbility(Node2D projectile) {
		this.projectile = projectile;
	}

	/* ================ [ ABILITY ] ================ */

	@Override
	public void trigger(UUID source) {
		Game.getInstance().getScene().addChild(projectile);
	}
	
}
