package com.apcs.ljaag.nodes.ability.abilities;

import java.util.HashMap;
import java.util.Map;

import com.apcs.disunity.app.resources.Sound;
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
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.character.immortals.Immortal;

public class Zhao3 extends AbilityData {

	/* ================ [ FIELDS ] ================ */

	public Map<Character, Double> stunned = new HashMap<>();

	// Constructors
	@SuppressWarnings("resource")
	public Zhao3() {
		super(
			TriggerType.MOUSE_POSITION,
			Vector2.ZERO,
			() -> new Collider(48, 48),
			() -> new Area2D(48, 48),
			() -> {
				new Thread(() -> {
					// try {
					// 	Thread.sleep(100);
					// } catch (InterruptedException e) { e.printStackTrace(); }
					new Sound("sounds/doom.wav").play();
				}).start();
				return new AnimatedSprite(new Transform(
					Vector2.of(0, -48),
					Vector2.of(1),
					0
				), "sword", new ImageLocation("zhao/abil3.png"), 0.1, 0.1, 0.1, 0.1, 0.1, 1);
			},
			2,
			10
		);
	}

	/* ================ [ ABILITY ] ================ */

	@Override
	public void onCollision(Character source, Projectile projectile, BodyEntered signal) {
		if (signal.body instanceof Character target) {
			if (target instanceof Immortal) return;
			stunned.put(target, 1.0);
			target.modifyHealth(-200);
		}
	}

	@Override
	public void update(Character source, Projectile projectile, double delta) {		
		for (Map.Entry<Character, Double> stun : stunned.entrySet()) {
			stun.getKey().setVelocity(Vector2.ZERO);
		};

		stunned.replaceAll((key, val) -> val - delta);
		stunned.entrySet().removeIf(entry -> entry.getValue() <= 0);

		// Base update behavior
		super.update(source, projectile, delta);
	}
	
}
