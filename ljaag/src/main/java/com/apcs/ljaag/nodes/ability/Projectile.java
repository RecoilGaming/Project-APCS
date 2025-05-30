package com.apcs.ljaag.nodes.ability;

import com.apcs.disunity.app.interfaces.QuadConsumer;
import com.apcs.disunity.app.interfaces.TriConsumer;
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

	// Functions
	private final TriConsumer<Character, Projectile, BodyEntered> onCollision;
	private final QuadConsumer<Character, Projectile, Transform, Double> update;

	// Alive time
	private double aliveTime = 0;

	// Constructors
	public Projectile(Character source, Transform transform, Collider collider, Area2D area2d, Sprite sprite, TriConsumer<Character, Projectile, BodyEntered> onCollision, QuadConsumer<Character, Projectile, Transform, Double> update) {
		super(transform, collider, area2d, sprite);
		this.source = source;
		this.onCollision = onCollision;
		this.update = update;
	}

	/* ================ [ METHODS ] ================ */
	
	public double getAliveTime() {
		return aliveTime;
	}

	/* ================ [ BODY ] ================ */

	@Override
	public void onBodyEntered(BodyEntered signal) {
		onCollision.accept(source, this, signal);
	}

	/* ================ [ NODE ] ================ */

	@Override
	public void update(Transform offset, double delta) {
		update.accept(source, this, offset, delta);
		aliveTime += delta;
		super.update(offset, delta);
	}
	
}
