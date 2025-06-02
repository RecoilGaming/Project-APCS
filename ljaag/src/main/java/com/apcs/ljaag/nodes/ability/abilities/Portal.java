package com.apcs.ljaag.nodes.ability.abilities;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.apcs.disunity.app.resources.Sound;
import com.apcs.disunity.game.nodes.collider.Collider;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.twodim.Area2D;
import com.apcs.disunity.game.nodes.twodim.Body;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.ability.Ability.TriggerType;
import com.apcs.ljaag.nodes.ability.AbilityData;
import com.apcs.ljaag.nodes.ability.Projectile;
import com.apcs.ljaag.nodes.character.Character;

public class Portal extends AbilityData {

    // Constructors
	public Portal() {
		super(
			TriggerType.MOUSE_POSITION,
			Vector2.ZERO,
			() -> new Collider(12, 48),
			() -> new Area2D(12, 48),
			() -> {
				new Thread(() -> {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					new Sound("sounds/swoosh.wav").play();
				}).start();
				return new AnimatedSprite("push", new ImageLocation("zhao/portal.png"), true, 0.12, 0.12, 0.12, 0.2);
			},
			0.1,
			1
		);
	}

	/* ================ [ ABILITY ] ================ */

	public Map<Body,Integer> cooldowns = new HashMap<>();
	public static final int COOLDOWN_FRAMES = 300;

	@Override
	public void onCollision(Character source, Projectile projectile, BodyEntered signal) {
		if (cooldowns.keySet().contains(signal.body)) return;
		Body b = null;
		if (!projectiles.isEmpty() && projectile != projectiles.get(0)) {
			b = projectiles.get(0);
		} else if (projectiles.size() > 1 && projectile != projectiles.get(1)) {
			b = projectiles.get(1);
		}
		if (b != null) {
			signal.body.setRotation(signal.body.getRotation() + b.getRotation() - projectile.getRotation());
			signal.body.setVelocity(Vector2.basis(signal.body.getRotation()).mul(signal.body.getVelocity().length()));
			signal.body.setPosition(b.getPosition());
			cooldowns.put(signal.body, COOLDOWN_FRAMES);
		}
	}

	private List<Projectile> projectiles = new LinkedList<>();

	@Override
	public void update(Character source, Projectile projectile, double delta) {
		if (!projectiles.contains(projectile)) projectiles.add(projectile);
		projectiles.sort((Projectile o1, Projectile o2) -> (int) (o1.getAliveTime() - o2.getAliveTime()));
		while (projectiles.size() > 2) {
			Projectile p = projectiles.removeLast();
			p.hide();
			p.disable();
		}
		cooldowns.replaceAll((key, val) -> val - 1);
		cooldowns.entrySet().removeIf(entry -> entry.getValue() <= 0);

	}

}
