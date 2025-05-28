package com.apcs.ljaag.nodes.characters.immortals;

import com.apcs.ljaag.nodes.abilities.Ability;
import com.apcs.ljaag.nodes.characters.CharacterData;
import com.apcs.ljaag.nodes.stats.Statset;

public class ImmortalData extends CharacterData {

	/* ================ [ FIELDS ] ================ */

	// Leveling stats (added per level)
	public final Statset LEVELING_STATS;

	// Maximum level
	public final int MAX_LEVEL;

	// Abilities
	// public final Ability[] ABILITIES;

	// Constructors
	public ImmortalData(Statset baseStats, Statset levelingStats, int maxLevel) {
		super(baseStats);
		this.LEVELING_STATS = levelingStats;
		this.MAX_LEVEL = maxLevel;
	}
	
}
