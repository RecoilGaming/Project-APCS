package com.apcs.ljaag.nodes.abilities;

import java.util.UUID;

import com.apcs.disunity.game.signals.SignalBus;
import com.apcs.ljaag.nodes.stats.Statset;

public class StatAbility extends Ability {

	/* ================ [ FIELDS ] ================ */

	// Statset
	private final Statset statset;

	// Constructors
	public StatAbility(Statset statset) {
		this.statset = statset;
	}

	/* ================ [ ABILITY ] ================ */

	@Override
	public void trigger(UUID source) {
		
	}

}
