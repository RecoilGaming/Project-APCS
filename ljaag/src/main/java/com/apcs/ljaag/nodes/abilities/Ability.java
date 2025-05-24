package com.apcs.ljaag.nodes.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ability {

	/* ================ [ FIELDS ] ================ */

	// Components
	private final List<Ability> components = new ArrayList<>();

	// Constructors
	public Ability(Ability... components) {
		for (Ability c : components) {
			this.components.add(c);
		}
	}

	/* ================ [ METHODS ] ================ */

	// Trigger the ability
	public void trigger(UUID source) {
		for (Ability a : components) {
			a.trigger(source);
		}
	}
	
}
