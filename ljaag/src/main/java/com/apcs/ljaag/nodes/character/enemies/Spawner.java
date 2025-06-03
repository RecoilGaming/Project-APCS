package com.apcs.ljaag.nodes.character.enemies;
import java.util.function.Consumer;

import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.CharacterData;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.stats.StatType;

public class Spawner extends Enemy {


    // Constructors
	public Spawner(double cooldown, Consumer<Transform> spawnBehavior, CharacterData data) { this(cooldown, spawnBehavior, new Transform(), data); }
	public Spawner(double cooldown, Consumer<Transform> spawnBehavior, Transform transform, CharacterData data, Node<?>... children) {
		super(transform, data, children);
        this.spawnBehavior = spawnBehavior;
        this.cooldown = cooldown;
	}

    private Consumer<Transform> spawnBehavior;

    private final double cooldown;
    private double curTimeout;

    @Override
	public void update(double delta) {
		if (health > 0) {
			if (curTimeout <= 0) {
                spawnBehavior.accept(getGlobalTrans());
                curTimeout = cooldown;
            } else {
                curTimeout -= delta;
            }
		} else {
            setScale(Vector2.of(0.5));
        }
		// Movement
		super.update(delta);
	}

    double lastAttack = 0;

    @Override
    public void onBodyEntered(BodyEntered signal) {
        super.onBodyEntered(signal);
        if (health <= 0) {
            return;
        }
        if (signal.body instanceof Immortal m) {
            if (System.currentTimeMillis() - lastAttack > StatType.ATTACK_SPEED.getInitial()) {
                m.modifyHealth(-getStat(StatType.ATTACK_DAMAGE));
                lastAttack = System.currentTimeMillis();
            }
        }
    }

}
