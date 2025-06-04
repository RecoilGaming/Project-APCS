package com.apcs.ljaag.nodes.character;

import java.util.function.Supplier;

import com.apcs.ljaag.nodes.character.immortals.ImmortalData;
import com.apcs.ljaag.nodes.stats.Statset;

public class Characters {

    public static final Supplier<CharacterData> BROKEN_VESSEL = () -> new ImmortalData(
		"demon",
		new Statset(1000, 0, 200, 10, 10, 50, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		null,
		null,
		null,
		null
	);

	public static final Supplier<CharacterData> EOW = () -> new ImmortalData(
		"worm",
		new Statset(2000, 0, 200, 20, 10, 50, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		null,
		null,
		null,
		null
	);

	public static final Supplier<CharacterData> SPAWNER = () -> new ImmortalData(
		"spawner",
		new Statset(10000, 0, 200, 20, 10, 50, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		null,
		null,
		null,
		null
	);

}
