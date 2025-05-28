package com.apcs.ljaag.nodes.abilities;

import java.util.function.BiConsumer;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class Projectile extends Body {

	/* ================ [ FIELDS ] ================ */

	// Aim direction
	private final Vector2 direction;

	// Update function
	private final BiConsumer<Transform, Double> update;

	// Constructors
	public Projectile(Collider collider, Area2D area2D, Sprite sprite, Vector2 direction, BiConsumer<Transform, Double> update) {
		super(collider, area2D, sprite);
		this.direction = direction;
		this.update = update;
	}

	/* ================ [ BODY ] ================ */

	@Override
	public void onBodyEntered(BodyEntered signal) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onBodyEntered'");
	}

	/* ================ [ NODE ] ================ */

	@Override
	public void update(Transform offset, double delta) {
		update.accept(offset, delta);
		super.update(offset, delta);
	}
	
}
