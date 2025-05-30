package com.apcs.ljaag.nodes.characters;

import java.util.UUID;

import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.SelectorNode;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.game.signals.Signals;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.stats.StatType;
import com.apcs.ljaag.nodes.stats.Statset;

public class Character<T extends CharacterData> extends Body {

	/* ================ [ NODES ] ================ */

	@FieldChild
    protected final SelectorNode<String, AnimatedSprite> sprite;

	/* ================ [ FIELDS ] ================ */

	{
        AnimatedSprite s1, s2;
        sprite = new SelectorNode<String, AnimatedSprite>(
            s1 = new AnimatedSprite("idle", "player/player.png", Double.MAX_VALUE),
            s2 = new AnimatedSprite("run", "player/run.png", true, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15)
		);

        s1.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
        s2.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
    }

	// Character id
	private final UUID id;

	// Character data
	protected final T data;

	// Current stats
	private Statset stats = new Statset();
	private Statset tempStats = new Statset();

	// Resource amounts
	protected int health = 100;
	protected int aura = 100;

	// Constructors
	public Character(Transform transform, T data, Node<?>... children) {
		super(
			transform,
			new Collider(
				new Transform().addPos(Vector2.of(0, -8)),
				8, 8
			),
			new Area2D(8,8),
			children
		);
		
		this.id = UUID.randomUUID();
		this.data = data;
		initialize();
	}

	/* ================ [ HELPERS ] ================ */

	// Modify stats
	protected void modifyStats(Statset stats) { this.stats.addStats(stats); }

	// Modify temporary stats
	protected void modifyTempStats(Statset stats) { this.tempStats.addStats(stats); }

	// Modify health
	public void modifyHealth(int amount) {
		this.health += amount;
	}

	// Modify aura
	public void modifyAura(int amount) {
		this.aura += amount;
	}
	
	/* ================ [ METHODS ] ================ */

	// Get character id
	public UUID getId() { return id; }

	// Initialize character
	public void initialize() {
		// Initialize stats
		this.stats = data.BASE_STATS.copy();
		this.health = stats.getStat(StatType.HEALTH);
		this.aura = stats.getStat(StatType.DIVINITY);

		// Connect signals
		Signals.connect(Signals.getSignal(getId(), "MODIFY_STATS"), this::modifyStats);
		Signals.connect(Signals.getSignal(getId(), "MODIFY_TEMP_STATS"), this::modifyTempStats);
		Signals.connect(Signals.getSignal(getId(), "MODIFY_HEALTH"), this::modifyHealth);
		Signals.connect(Signals.getSignal(getId(), "MODIFY_AURA"), this::modifyAura);
	}

	// Get total stats
	public int getStat(StatType stat) {
		return stats.getStat(stat) + stats.getStat(stat);
	}

	/* ================ [ NODE ] ================ */

	@Override
	public void update(Transform offset, double delta) {

		if (health <= 0) {
			// simple dealth thingy
			this.setVelocity(Vector2.ZERO);
			killAllSprites(this);
			setRotation(- Math.PI / 2);
		}

		// Rotation
		if (getVelocity().xi != 0) {
			setRotation(getVelocity().heading());
		}

		// Animation
		if (getVelocity().length() < 0.1) {
            sprite.select("idle");
        } else {
            sprite.select("run");
        }

		super.update(offset, delta);
	}

	/* ================ [ BODY ] ================ */

	@Override
	public void onBodyEntered(BodyEntered signal) { }

	private void killAllSprites(Node<? extends Node> n) {
		for (Node<? extends Node> c : n.getAllChildren()) {
			if (c instanceof Sprite s) {
				s.setRotationType(Sprite.RotationType.NORMAL);
			}
			killAllSprites(c);
		}
	}

}
