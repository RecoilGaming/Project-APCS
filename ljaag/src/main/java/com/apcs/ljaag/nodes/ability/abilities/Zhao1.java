package com.apcs.ljaag.nodes.ability.abilities;

import java.util.HashMap;
import java.util.Map;

import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.ability.Ability.TriggerType;
import com.apcs.ljaag.nodes.ability.AbilityData;
import com.apcs.ljaag.nodes.ability.Projectile;
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
;

public class Zhao1 extends AbilityData {

	/* ================ [ FIELDS ] ================ */

	public Map<Character, Double> pushing = new HashMap<>();
	public static int PUSH_FRAMES = 20;
	public double PUSH_PX = 2;

	// Constructors
	@SuppressWarnings("resource")
	public Zhao1() {
		super(
			TriggerType.SOURCE_POSITION,
			Vector2.ZERO,
			() -> new Collider(48, 12),
			() -> new Area2D(48, 12),
			() -> {
				new Thread(() -> {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) { e.printStackTrace(); }
					new Sound("sounds/swoosh.wav").play();
				}).start();
				return new AnimatedSprite("push", new ImageLocation("zhao/abil1.png"), 0.15, 0.15, 0.15);
			},
			0.5,
			3
		);
	}

	/* ================ [ ABILITY ] ================ */

	@Override
	public void onCollision(Character source, Projectile projectile, BodyEntered signal) {
		if (signal.body instanceof Character target) {
			if (target instanceof Immortal) {
				return;
			}
			pushing.put(target, 0.5);
			target.modifyHealth(-20);
		}
	}

	@Override
	public void update(Character source, Projectile projectile, double delta) {
		projectile.setPosition(source.getPosition().add(Vector2.of(0, 4)));
		
		for (Map.Entry<Character, Double> push : pushing.entrySet()) {
			Node2D key = push.getKey();
			key.addPosition(key.getPosition().sub(source.getPosition()).normalized().mul(PUSH_PX));
		};

		pushing.replaceAll((key, val) -> val - delta);
		pushing.entrySet().removeIf(entry -> entry.getValue() <= 0);

		// Base update behavior
		super.update(source, projectile, delta);
	}
	
}
