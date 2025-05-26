package com.apcs.disunity.game.nodes.collider;

import com.apcs.disunity.game.physics.CollisionLayer;
import com.apcs.disunity.game.physics.CollisionMask;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class AnimatedCollider extends Collider {

	private final Vector2 initialSize;
	private final Vector2 finalSize;

	private final double[] frameDurations;
	private long prevFrame = System.nanoTime();
	private int frameCount = 0;

	public AnimatedCollider(int w, int h, int w2, int h2, CollisionLayer layer, double... frameDurations) {
		super(w, h, layer);
		initialSize = Vector2.of(w, h);
		finalSize = Vector2.of(w2, h2);
		this.frameDurations = frameDurations;
		updateFrame();
	}

	protected double frameDuration() { return frameDurations[frameCount]; }

	protected int length() { return frameDurations.length; }

	public void updateFrame() {
        frameCount %= length();

		SIZE = initialSize.moveTowards(finalSize, (frameCount + 1) / length());
    }

	@Override
    public void update(Transform offset, double delta) {
        // Update frame
        if (System.nanoTime() - prevFrame >= frameDuration() * 1e9) {
            prevFrame = System.nanoTime();
            frameCount++;
            updateFrame();
        }

        super.update(offset, delta);
    }


	
}
