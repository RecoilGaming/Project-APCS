package com.apcs.ljaag.nodes.characters;

import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.SelectorNode;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.CollisionInfo;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.indexed.InputVector;
import com.apcs.ljaag.nodes.stats.Statset;

public class Immortal extends Body {

	/* ================ [ NODES ] ================ */

	@FieldChild
    private final SelectorNode<String, AnimatedSprite> sprite;

	/* ================ [ FIELDS ] ================ */

	{
        AnimatedSprite s1, s2;
        sprite = new SelectorNode<String, AnimatedSprite>(
            s1 = new AnimatedSprite("idle", "player/player.png", Double.MAX_VALUE),
            s2 = new AnimatedSprite("run", "player/run.png", 0.15, 0.15, 0.15, 0.15, 0.15, 0.15)
		);

        s1.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
        s2.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
    }

	// Default values
	protected final Statset STATS = new Statset();

	// Current stats
	private Statset stats = new Statset();

	// Resource amounts
	private int health = 100;
	private int aura = 100;

	// Movement input
	private final InputVector moveDir = new InputVector("move");

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
