package com.apcs.ljaag.nodes.ability;

import com.apcs.disunity.app.interfaces.QuadConsumer;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.character.Character;

public class Projectile extends Body {

	/* ================ [ FIELDS ] ================ */

	// Source character
	private final Character source;

	// Update function
	private final QuadConsumer<Character, Projectile, Transform, Double> update;

	// Constructors
	public Projectile(Character source, Transform transform, Collider collider, Area2D area2D, Sprite sprite, QuadConsumer<Character, Projectile, Transform, Double> update) {
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
		update.accept(source, this, offset, delta);
		super.update(offset, delta);
	}
	
}
