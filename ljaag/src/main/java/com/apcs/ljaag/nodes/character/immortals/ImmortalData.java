package com.apcs.ljaag.nodes.character.immortals;

import com.apcs.ljaag.nodes.ability.AbilityData;
import com.apcs.ljaag.nodes.character.CharacterData;
import com.apcs.ljaag.nodes.stats.Statset;

public class ImmortalData extends CharacterData {

	/* ================ [ FIELDS ] ================ */

	// Leveling stats (added per level)
	public final Statset LEVELING_STATS;

	// Maximum level
	public final int MAX_LEVEL;

	// Abilities
	public final AbilityData BASIC;
	public final AbilityData SKILL_1;
	public final AbilityData SKILL_2;
	public final AbilityData ULTIMATE;

	// Constructors
	public ImmortalData(String id, Statset baseStats, Statset levelingStats, int maxLevel, AbilityData basic, AbilityData skill1, AbilityData skill2, AbilityData ultimate) {
		super(id, baseStats);
		this.LEVELING_STATS = levelingStats;
		this.MAX_LEVEL = maxLevel;
		this.BASIC = basic;
		this.SKILL_1 = skill1;
		this.SKILL_2 = skill2;		
		this.ULTIMATE = ultimate;
	}
	
}
