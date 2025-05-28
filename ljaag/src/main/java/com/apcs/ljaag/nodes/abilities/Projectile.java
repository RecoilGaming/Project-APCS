package com.apcs.ljaag.nodes.abilities;

import java.util.UUID;
import java.util.function.BiConsumer;

import com.apcs.disunity.app.interfaces.TriConsumer;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;

public class Projectile extends Body {

	/* ================ [ FIELDS ] ================ */

	// Source UUID
	private final UUID source;

	// Update function
	private final TriConsumer<UUID, Transform, Double> update;

	// Constructors
	public Projectile(UUID source, Transform transform, Collider collider, Area2D area2D, Sprite sprite, TriConsumer<UUID, Transform, Double> update) {
		super(transform, collider, area2D, sprite);
		this.source = source;
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
		update.accept(source, offset, delta);
		super.update(offset, delta);
	}
	
}
