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
import com.apcs.ljaag.nodes.indexed.InputVector;
import com.apcs.ljaag.nodes.leveling.LevelInfo;
import com.apcs.ljaag.nodes.stats.StatType;
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
            s2 = new AnimatedSprite("run", "player/run.png", true, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15)
		);

        s1.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
        s2.setRotationType(Sprite.RotationType.BIDIRECTIONAL);
    }

	// Character id
	private UUID id;

	// Immortal data
	private final ImmortalData data;

	// Current stats
	private Statset stats = new Statset();
	private Statset tempStats = new Statset();

	// Leveling information
	private LevelInfo levelInfo;

	// Resource amounts
	private int health = 100;
	private int aura = 100;

	// Movement input
	private final InputVector moveDir = new InputVector("move");

	// Constructors
	public Immortal(ImmortalData data) { this(new Transform(), data); }
	public Immortal(Transform transform, ImmortalData data, Node<?>... children) {
		super(
			transform,
			new Collider(
				new Transform().addPos(Vector2.of(0, -8)),
				8, 8
			),
			new Area2D(8,8),
			children
		);

		this.data = data;
		initialize();
	}

	/* ================ [ HELPERS ] ================ */

	// Modify stats
	private void modifyStats(Statset stats) {
		this.stats.addStats(stats);
	}

	// Modify temporary stats
	private void modifyTempStats(Statset stats) {
		this.tempStats.addStats(stats);
	}

	/* ================ [ METHODS ] ================ */

	// Get immortal id
	public UUID getId() { return id; }

	// Initialize character
	public void initialize() {
		this.id = UUID.randomUUID();

		// Initialize leveling
		this.levelInfo = new LevelInfo(data.MAX_LEVEL);

		// Initialize stats
		this.stats = data.BASE_STATS.copy();
		this.health = stats.getStat(StatType.HEALTH);
		this.aura = stats.getStat(StatType.DIVINITY);

		// Connect signals
		Signals.connect(Signals.getSignal(id, "MODIFY_STATS"), this::modifyStats);
		Signals.connect(Signals.getSignal(id, "MODIFY_TEMP_STATS"), this::modifyTempStats);
	}

	// Get total stats
	public int getStat(StatType stat) {
		return stats.getStat(stat) + stats.getStat(stat);
	}

	/* ================ [ BODY ] ================ */

	@Override
	public void onBodyEntered(BodyEntered signal) { }

	/* ================ [ NODE ] ================ */

	@Override
	public void update(Transform offset, double delta) {
		// Movement
		setVelocity(moveDir.get().mul(getStat(StatType.SPEED)));

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
	
}
