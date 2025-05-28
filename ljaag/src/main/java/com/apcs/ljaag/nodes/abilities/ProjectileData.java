package com.apcs.ljaag.nodes.abilities;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class ProjectileData {

	/* ================ [ FIELDS ] ================ */

	// Aim direction
	private final Vector2 direction;

	// Collision detection
	private final Collider collider;
	private final Area2D area2D;
	private final Sprite sprite;

	// Constructors
	public ProjectileData(Vector2 direction, Collider collider, Area2D area2D, Sprite sprite) {
		this.direction = direction;
		this.collider = collider;
		this.area2D = area2D;
		this.sprite = sprite;
	}

	/* ================ [ METHODS ] ================ */

	// Instantiate
	public Projectile instantiate(Vector2 direction) {
		return new Projectile(collider, area2D, sprite, direction, this::update);
	}

	// Update
	public void update(Transform offset, double delta) {
		;
	}
	
}
