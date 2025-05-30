package com.apcs.ljaag.nodes.character.immortals;

import com.apcs.ljaag.nodes.ability.abilities.Test;
import com.apcs.ljaag.nodes.ability.abilities.Zhao1;
import com.apcs.ljaag.nodes.stats.Statset;

public class Immortals {

	/* ================ [ IMMORTALS ] ================ */

	public static final ImmortalData ZHAO = new ImmortalData(
		"zhao",
		new Statset(100, 100, 100, 100, 100, 100, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		new Test(),
		new Zhao1(),
		new Test(),
		new Test()
	);
	
}
