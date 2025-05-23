package com.apcs.ljaag.nodes.stats;

public enum StatType {

	/* ================ [ ENUM ] ================ */

	// Depletable resources
	HEALTH("Health", 100), // The number of hit points a character has
	DIVINITY("Divinity", 100), // The max amount of aura a character can hold

	// Basic stats
	RANGE("Range", 100), // The distance a character can attack from (used in ability calculations)
	SPEED("Speed", 100), // The number of units a character can move per second

	// Physical damage
	ATTACK_DAMAGE("Attack Damage", 100),
	ATTACK_SPEED("Attack Speed", 100),
	DEFENSE("Defense", 0),
	// Ability damage
	ABILITY_POWER("Ability Power", 100),
	ABILITY_HASTE("Ability Haste", 100),
	RESISTANCE("Resistance", 0),

	// Other stats
	LIFESTEAL("Lifesteal", 0), // Allows health to be gained from attacking
	SPLENDOR("Splendor", 0); // Increases the rate of gaining aura

	/* ================ [ FIELDS ] ================ */

	// Name
	private final String name;

	// Initial value
	private final int initial;

	// Construcors
	StatType(String name, int initial) {
		this.name = name;
		this.initial = initial;
	}

	/* ================ [ METHODS ] ================ */

	// Get name
	public String getName() { return name; }

	// Get initial value
	public int getInitial() { return initial; }
	
}
