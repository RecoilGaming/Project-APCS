package com.apcs.ljaag.nodes.character;

import com.apcs.disunity.game.nodes.FieldChild;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.SelectorNode;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.AABB;
import com.apcs.disunity.game.physics.BodyEntered;
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
            s1 = new AnimatedSprite("idle", "player/idle.png", Double.MAX_VALUE),
            s2 = new AnimatedSprite("run", "player/run.png", true, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15)
		);

        s1.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
        s2.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
    }


	// Character data
	protected final T data;

	// Current stats
	private Statset stats = new Statset();
	private Statset tempStats = new Statset();

	// Resource amounts
	protected int health = getStat(StatType.HEALTH);
	protected int aura = getStat(StatType.DIVINITY);

	// Constructors
	public Character(Transform transform, T data, Node<?>... children) {
		super(
			transform,
			new Collider(
				new Transform().addPos(Vector2.of(0, 15)),
				15, 15
			),
			new Area2D(15, 15),
			children
		);
		
		this.data = data;
		initialize();
	}

	/* ================ [ HELPERS ] ================ */

	// Modify stats
	public void modifyStats(Statset stats) { this.stats.addStats(stats); }

	// Modify temporary stats
	public void modifyTempStats(Statset stats) { this.tempStats.addStats(stats); }

	// Modify health
	public void modifyHealth(int amount) {
		this.health += amount;
	}

	// Modify aura
	public void modifyAura(int amount) {
		this.aura += amount;
	}
	
	/* ================ [ METHODS ] ================ */

	// Initialize character
	public void initialize() {
		// Initialize animations
		sprite.get("idle").setImageLocation(new ImageLocation(data.id + "/idle.png"));
		sprite.get("run").setBaseImage(new ImageLocation(data.id + "/run.png"));

		// Initialize stats
		this.stats = data.BASE_STATS.copy();
		this.health = stats.getStat(StatType.HEALTH);
		this.aura = stats.getStat(StatType.DIVINITY);
	}

	// Get total stats
	public int getStat(StatType stat) {
		return stats.getStat(stat) + stats.getStat(stat);
	}

	/* ================ [ NODE ] ================ */

	@Override
	public void update(double delta) {
		// Death
		if (health <= 0) {
			onDeath();
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

		super.update(delta);
	}

	protected void onDeath() {
		this.setVelocity(Vector2.ZERO);
		killAllSprites(this);
		setRotation(- Math.PI / 2);
	}

	/* ================ [ BODY ] ================ */

	@Override
	public void onBodyEntered(BodyEntered signal) {
		// TODO: handle collisions so that the player cannot phase through walls

	}

	private void killAllSprites(Node<? extends Node> n) {
		for (Node<? extends Node> c : n.getAllChildren()) {
			if (c instanceof Sprite s) {
				s.setRotationType(Sprite.RotationType.NORMAL);
			}
			killAllSprites(c);
		}
	}

    public int getHealth() {
        return health;
    }

    public int getAura() {
        return aura;
    }

}
