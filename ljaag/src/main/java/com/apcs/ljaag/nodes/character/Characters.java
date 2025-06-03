package com.apcs.ljaag.nodes.character;

import com.apcs.ljaag.nodes.character.immortals.ImmortalData;
import com.apcs.ljaag.nodes.stats.Statset;

public class Characters {

    public static final CharacterData BROKEN_VESSEL = new ImmortalData(
		"demon",
		new Statset(300, 0, 200, 10, 10, 50, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		null,
		null,
		null,
		null
	);

	public static final CharacterData EOW = new ImmortalData(
		"worm",
		new Statset(500, 0, 200, 20, 10, 50, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		null,
		null,
		null,
		null
	);

	public static final CharacterData SPAWNER = new ImmortalData(
		"spawner",
		new Statset(500, 0, 200, 20, 10, 50, 0, 100, 100, 0, 0, 0),
		new Statset(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
		15,
		null,
		null,
		null,
		null
	);

}
