package com.apcs.ljaag.nodes.character.enemies;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.character.CharacterData;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.stats.StatType;

public class Demon extends Enemy {

    // Constructors
	public Demon(CharacterData data) { this(new Transform(), data); }
	public Demon(Transform transform, CharacterData data, Node<?>... children) {
		super(transform, data, children);
	}

    @Override
	public void update(double delta) {
		if (!isStunned()) {
			Immortal closestPlayer = null;
            for (Node n : getParent().getAllChildren()) {
                if (n instanceof Immortal i) {
                    if (closestPlayer == null || closestPlayer.getPosition().sub(getPosition()).length() < i.getPosition().sub(getPosition()).length()) {
                        closestPlayer = i;
                    }
                }
            }
            setVelocity(closestPlayer.getPosition().sub(getPosition()).normalized().mul(getStat(StatType.SPEED)));
		}
		// Movement
		super.update(delta);
	}

}
