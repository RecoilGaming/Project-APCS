package com.apcs.ljaag.nodes.ability.abilities;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.ability.Ability.TriggerType;
import com.apcs.ljaag.nodes.ability.AbilityData;
import com.apcs.ljaag.nodes.ability.Projectile;
import com.apcs.ljaag.nodes.character.Character;;

public class Zhao1 extends AbilityData {

	/* ================ [ FIELDS ] ================ */

	// Constructors
	public Zhao1() {
		super(
			TriggerType.SOURCE_POSITION,
			Vector2.ZERO,
			() -> new Collider(24, 8),
			() -> new Area2D(24, 8),
			() -> new AnimatedSprite("push", new ImageLocation("zhao/abil1.png"), 0.15, 0.15, 0.15),
			0.5
		);
	}

	/* ================ [ ABILITY ] ================ */

	@Override
	public void onCollision(Character source, Projectile projectile, BodyEntered signal) {
		if (signal.body instanceof Character target) {
			target.addPosition(target.getPosition().sub(source.getPosition()).normalized().mul(25));
		}
	}

	@Override
	public void update(Character source, Projectile projectile, Transform offset, double delta) {
		projectile.setPosition(source.getPosition().add(Vector2.of(0, 4)));

		if (projectile.getAliveTime() > 0.5) {
			// ((Node) projectile.getParent()).removeChild(projectile);
			;
		}
	}
	
}
