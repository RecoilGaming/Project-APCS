package com.apcs.ljaag.nodes.character.immortals;

import com.apcs.disunity.app.input.Inputs;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.DeathScreen;
import com.apcs.ljaag.nodes.PauseScreen;
import com.apcs.ljaag.nodes.ability.Ability;
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.indexed.InputVector;
import com.apcs.ljaag.nodes.stats.StatType;

public class Immortal extends Character<ImmortalData> {

	/* ================ [ FIELDS ] ================ */

	// Experience level requirements
	public static final int LEVELUP_EXP = 100;
	public static final double LEVEPUP_EXP_MULT = 1.2;

	// Levelling
	private int level = 1;
	private int experience = 0;
	private int skillPoints = 1;

	// Abilities
	private Ability basicAbility;
	private Ability skill1Ability;
	private Ability skill2Ability;
	private Ability ultimateAbility;
	
	// Movement input
	private final InputVector moveDir = new InputVector("move");

	// Constructors
	public Immortal(ImmortalData data) { this(new Transform(), data); }
	public Immortal(Transform transform, ImmortalData data, Node<?>... children) {
		super(transform, data, children);
	}

	public Immortal(Transform transform, ImmortalData data, String idle, String run, int numFrames, Node<?>... children) {
		this(transform, data, children);
		sprite.get("idle").setBaseImage(new ImageLocation(idle));
		sprite.get("run").setBaseImage(new ImageLocation(run));
		double[] d = new double[numFrames];
		for (int i = 0; i < numFrames; i++) {
			d[i] = 0.15;
		}
		sprite.get("run").setFrameDurations(d);
	}

	/* ================ [ METHODS ] ================ */

	// Get level
	public int getLevel() { return level; }

	// Get max level
	public int getMaxLevel() { return data.MAX_LEVEL; }

	// Get experience
	public int getExperience() { return experience; }

	// Get skill points
	public int getSkillPoints() { return skillPoints; }

	// Get experience requirement
	public int getLevelingRequirement() {
		return (int) (LEVELUP_EXP * Math.pow(LEVEPUP_EXP_MULT, level - 1));
	}

	// Add experience
	public void addExperience(int exp) {
		experience += exp;
		while (experience >= getLevelingRequirement()) {
			experience -= getLevelingRequirement();
			levelUp();
		}
	}

	// Level up
	public void levelUp() {
		if (level < data.MAX_LEVEL) {
			level++;
			skillPoints++;
			modifyStats(data.LEVELING_STATS);
		}
	}

	/* ================ [ CHARACTER ] ================ */

	@Override
	public void initialize() {
		super.initialize();

		// Initialize abilities
		this.basicAbility = new Ability(data.BASIC);
		this.skill1Ability = new Ability(data.SKILL_1);
		this.skill2Ability = new Ability(data.SKILL_2);
		this.ultimateAbility = new Ability(data.ULTIMATE);
	}

	@Override
	public void onDeath() {
		if (Game.getInstance().isPaused) return;

		super.onDeath();
		Game.getInstance().pause();

		Game.getInstance().getScene().addChild(new DeathScreen(getPosition()));
	}

	/* ================ [ NODE ] ================ */

	@Override
	public void update(double delta) {
		if (!Game.getInstance().isPaused && Inputs.getAction("pause")) {
			Game.getInstance().pause();
			Game.getInstance().getScene().addChild(new PauseScreen(getPosition()));
		}

		// Movement
		if (health > 0) {
			setVelocity(moveDir.get().mul(getStat(StatType.SPEED)));
		}

		// Abilities
		if (Inputs.getAction("basic") && !Game.getInstance().isPaused) {
			basicAbility.use(this);
		}
		if (Inputs.getAction("skill1") && !Game.getInstance().isPaused) {
			skill1Ability.use(this);
		}
		if (Inputs.getAction("skill2") && !Game.getInstance().isPaused) {
			skill2Ability.use(this);
		}
		if (Inputs.getAction("ultimate") && !Game.getInstance().isPaused) {
			ultimateAbility.use(this);
		}

		super.update(delta);
	}
	
}
