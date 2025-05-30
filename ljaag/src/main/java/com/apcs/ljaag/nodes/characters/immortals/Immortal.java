package com.apcs.ljaag.nodes.characters.immortals;

import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.characters.Character;
import com.apcs.ljaag.nodes.indexed.InputVector;
import com.apcs.ljaag.nodes.stats.StatType;

public class Immortal extends Character<ImmortalData> {

	/* ================ [ FIELDS ] ================ */

	// Experience level requirements
	public static final int LEVELUP_EXP = 100;
	public static final double LEVEPUP_EXP_MULT = 1.2;

	// Level
	private int level = 1;

	// Experience
	private int experience = 0;

	// Skill points
	private int skillPoints = 1;

	// Movement input
	private final InputVector moveDir = new InputVector("move");

	// Constructors
	public Immortal(ImmortalData data) { this(new Transform(), data); }
	public Immortal(Transform transform, ImmortalData data, Node<?>... children) {
		super(transform, data, children);
	}

	public Immortal(Transform transform, ImmortalData data, String idle, String run, Node<?>... children) {
		this(transform, data, children);
		sprite.get("idle").setImageLocation(new ImageLocation(idle));
		sprite.get("run").setBaseImage(new ImageLocation(run));
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

	/* ================ [ NODE ] ================ */

	@Override
	public void update(Transform offset, double delta) {
		if (health > 0) {
			setVelocity(moveDir.get().mul(getStat(StatType.SPEED)));
		}
		// Movement
		super.update(offset, delta);
	}
	
}
