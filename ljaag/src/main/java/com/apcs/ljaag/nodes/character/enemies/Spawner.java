package com.apcs.ljaag.nodes.character.enemies;
import java.util.function.Consumer;

import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.CharacterData;

public class Spawner extends Enemy {


    // Constructors
	public Spawner(double cooldown, Consumer<Transform> spawnBehavior, Transform transform, CharacterData data, Node<?>... children) {
		super(transform, data, children);
        this.spawnBehavior = spawnBehavior;
        this.cooldown = cooldown;
        originalScale = getScale();
        originalHealth = getHealth();
	}

    private Consumer<Transform> spawnBehavior;

    private final double cooldown;
    private double curTimeout;
    private final Vector2 originalScale;
    private final int originalHealth;

    @Override
	public void update(double delta) {
		if (health > 0) {
            setScale(originalScale.mul((double) getHealth() / originalHealth * 0.75 + 0.25));
			if (curTimeout <= 0) {
                spawnBehavior.accept(getGlobalTrans().scaleTo(Vector2.ONE));
                curTimeout = cooldown;
            } else {
                curTimeout -= delta;
            }
		} else {
            setScale(Vector2.ZERO);
        }

		// Movement
		super.update(delta);
	}

    @Override
    public void setPosition(Vector2 pos) {
        // spawners should not be able to move
    }

    @Override
    public void onDeath() {
        super.onDeath();
    }

}
