package com.apcs.ljaag.nodes.characters;

import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.CollisionInfo;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.stats.Statset;

public class Immortal extends Body {

	/* ================ [ FIELDS ] ================ */

	// Default values
	protected final Statset STATS = new Statset();

	// Current stats
	private Statset stats = new Statset();

	// Resource amounts
	private int health = 100;
	private int aura = 100;

	// Constructors
	public Immortal(Transform transform, Node<?>... children) {
		super(
			transform,
			new Collider(
				new Transform().addPos(Vector2.of(0, -8)),
				8, 8
			),
			children
		);
	}

	/* ================ [ BODY ] ================ */

	@Override
	public void onCollision(CollisionInfo info) {
		;
	}
	
}
