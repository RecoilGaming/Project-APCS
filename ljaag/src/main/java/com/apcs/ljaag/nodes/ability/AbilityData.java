package com.apcs.ljaag.nodes.ability;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.ability.Ability.TriggerType;
import com.apcs.ljaag.nodes.character.Character;

public class AbilityData {

	/* ================ [ FIELDS ] ================ */

	// Trigger type
	public final TriggerType trigger;

	// Projectile components
	public final Collider collider;
	public final Area2D area2D;
	public final Sprite sprite;

	// Cooldown
	public final double cooldown;

	// Constructors
	public AbilityData(TriggerType trigger, Collider collider, Area2D area2D, Sprite sprite, double cooldown) {
		this.trigger = trigger;
		this.collider = collider;
		this.area2D = area2D;
		this.sprite = sprite;
		this.cooldown = cooldown;
	}

	/* ================ [ METHODS ] ================ */

	// Update ability projectile
	public void update(Character source, Projectile projectile, double delta) {
		;
	}
	
}
