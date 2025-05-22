package com.apcs.ljaag.nodes.stats;

import java.util.HashMap;
import java.util.Map;

public class Statset {

	/* ================ [ FIELDS ] ================ */

	// Maps stats to their values
	private final Map<StatType, Integer> stats = new HashMap<>();
	{
		stats.put(StatType.HEALTH, StatType.HEALTH.getInitial());
		stats.put(StatType.DIVINITY, StatType.DIVINITY.getInitial());
		stats.put(StatType.RANGE, StatType.RANGE.getInitial());
		stats.put(StatType.SPEED, StatType.SPEED.getInitial());
		stats.put(StatType.ATTACK_DAMAGE, StatType.ATTACK_DAMAGE.getInitial());
		stats.put(StatType.ATTACK_SPEED, StatType.ATTACK_SPEED.getInitial());
		stats.put(StatType.DEFENSE, StatType.DEFENSE.getInitial());
		stats.put(StatType.ABILITY_POWER, StatType.ABILITY_POWER.getInitial());
		stats.put(StatType.ABILITY_HASTE, StatType.ABILITY_HASTE.getInitial());
		stats.put(StatType.RESISTANCE, StatType.RESISTANCE.getInitial());
		stats.put(StatType.LIFESTEAL, StatType.LIFESTEAL.getInitial());
		stats.put(StatType.SPLENDOR, StatType.SPLENDOR.getInitial());
	}

	/* ================ [ METHODS ] ================ */

	// Add one stat
	public void addStat(StatType stat, int amount) {
		stats.put(stat, stats.get(stat) + amount);
	}

	// Add all stats
	public void addStats(Statset other) {
		for (StatType stat : StatType.values()) {
			stats.put(stat, stats.get(stat) + other.stats.get(stat));
		}
	}

	// Get one stat
	public int getStat(StatType stat) {
		return stats.get(stat);
	}
	
}
