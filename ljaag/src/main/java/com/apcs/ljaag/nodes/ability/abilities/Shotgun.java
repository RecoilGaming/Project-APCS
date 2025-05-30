package com.apcs.ljaag.nodes.ability.abilities;

import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.ability.Ability;
import com.apcs.ljaag.nodes.ability.Projectile;
import com.apcs.ljaag.nodes.character.Character;

public class Shotgun extends Ability {

	/* ================ [ FIELDS ] ================ */

	// Constructors
	public Shotgun() {
		super(TriggerType.MOUSE_DIRECTION, new Collider(1, 1), new Area2D(1, 1), new Sprite("weapons/bullet.png"));
	}

	/* ================ [ ABILITY ] ================ */

	@Override
	public void update(Character source, Projectile projectile, Transform offset, double delta) {
		projectile.setVelocity(Vector2.fromAngle(projectile.getRotation()));
		// if (projectile.getPosition().sub(source.getPosition()).length() > 100) {
		// 	projectile.getParent().removeChild(projectile);
		// }
	}

}
