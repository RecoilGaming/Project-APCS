package com.apcs.ljaag.nodes.leveling;

public class LevelInfo {

	/* ================ [ FIELDS ] ================ */

	// Experience level requirements
	public static final int LEVELUP_EXP = 100;
	public static final double LEVEPUP_EXP_MULT = 1.2;

	// Level
	private int level = 1;
	private final int maxLevel;

	// Experience
	private int experience = 0;

	// Skill points
	private int skillPoints = 1;

	// Constructors
	public LevelInfo(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	/* ================ [ METHODS ] ================ */

	// Get level
	public int getLevel() { return level; }

	// Get max level
	public int getMaxLevel() { return maxLevel; }

	// Get experience
	public int getExperience() { return experience; }

	// Get skill points
	public int getSkillPoints() { return skillPoints; }

	// Get experience requirement
	public int getLevelingRequirement() {
		return (int) (LEVELUP_EXP * Math.pow(LEVEPUP_EXP_MULT, level - 1));
	}

	/* ================ [ LEVELING ] ================ */

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
		if (level < maxLevel) {
			level++;
			skillPoints++;
		}
	}
	
}
