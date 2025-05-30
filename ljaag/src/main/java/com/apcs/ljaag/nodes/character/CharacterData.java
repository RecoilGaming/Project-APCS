package com.apcs.ljaag.nodes.character;

import com.apcs.ljaag.nodes.stats.Statset;

public class CharacterData {

	/* ================ [ FIELDS ] ================ */

	// Character id
	public final String id;

	// Default stats
	public final Statset BASE_STATS;

	// Constructors
	public CharacterData(String id, Statset baseStats) {
		this.id = id;
		this.BASE_STATS = baseStats;
	}
	
}
