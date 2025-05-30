package com.apcs.ljaag.nodes.ability.abilities;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.ability.AbilityData;
import com.apcs.ljaag.nodes.ability.Projectile;
import com.apcs.ljaag.nodes.ability.Ability.TriggerType;
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.stats.StatType;

public class Shotgun extends AbilityData {

	/* ================ [ FIELDS ] ================ */

	// Constructors
	public Shotgun() {
		super(
			TriggerType.MOUSE_DIRECTION,
			new Collider(1, 1), new Area2D(1, 1), new Sprite("weapons/bullet.png"),
			0.5
		);
	}

	/* ================ [ ABILITY ] ================ */

	@Override
	public void update(Character source, Projectile projectile, Transform offset, double delta) {
		projectile.setVelocity(Vector2.fromAngle(projectile.getRotation()).mul(100));

		if (projectile.getPosition().sub(source.getPosition()).length() > source.getStat(StatType.RANGE)) {
			// ((Node) projectile.getParent()).removeChild(projectile);
			;
		}
	}

}
