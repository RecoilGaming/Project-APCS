package com.apcs.ljaag.nodes.stats;

public class Stat {

	/* ================ [ FIELDS ] ================ */

	// Stat type
	public final StatType stat;

	// Stat amount
	public final int amount;

	// Constructors
	public Stat(StatType stat, int amount) {
		this.stat = stat;
		this.amount = amount;
	}

}
