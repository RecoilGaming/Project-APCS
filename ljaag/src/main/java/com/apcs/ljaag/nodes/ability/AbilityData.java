package com.apcs.ljaag.nodes.ability;

import java.util.function.Supplier;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.ability.Ability.TriggerType;
import com.apcs.ljaag.nodes.character.Character;

public class AbilityData {

	/* ================ [ FIELDS ] ================ */

	// Trigger type
	public final TriggerType trigger;

	// Offset
	public final Vector2 offset;

	// Projectile components
	public final Supplier<Collider> collider;
	public final Supplier<Area2D> area2D;
	public final Supplier<Sprite> sprite;

	// Cooldown
	public final double cooldown;

	// Constructors
	public AbilityData(TriggerType trigger, Vector2 offset, Supplier<Collider> collider, Supplier<Area2D> area2D, Supplier<Sprite> sprite, double cooldown) {
		this.trigger = trigger;
		this.offset = offset;
		this.collider = collider;
		this.area2D = area2D;
		this.sprite = sprite;
		this.cooldown = cooldown;
	}

	/* ================ [ METHODS ] ================ */

	// On collision
	public void onCollision(Character source, Projectile projectile, BodyEntered signal) {
		;
	}

	// Update ability projectile
	public void update(Character source, Projectile projectile, Transform offset, double delta) {
		;
	}
	
}
