package com.apcs.ljaag.nodes.character.immortals;

import java.util.function.Supplier;

import com.apcs.ljaag.nodes.ability.abilities.Portal;
import com.apcs.ljaag.nodes.ability.abilities.Zhao1;
import com.apcs.ljaag.nodes.ability.abilities.Zhao3;
import com.apcs.ljaag.nodes.stats.Statset;

public class Immortals {

	/* ================ [ IMMORTALS ] ================ */

	public static final Supplier<ImmortalData> ZHAO = () -> new ImmortalData(
		"zhao",
		new Statset(100, 100, 100, 100, 100, 100, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		null,
		new Zhao1(),
		new Portal(),
		new Zhao3()
	);
	
}
