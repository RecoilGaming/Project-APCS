package com.apcs.ljaag.nodes.characters.immortals;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.characters.Character;
import com.apcs.ljaag.nodes.characters.CharacterData;
import com.apcs.ljaag.nodes.stats.StatType;

public class Demon extends Character<CharacterData> {


    // Constructors
	public Demon(CharacterData data) { this(new Transform(), data); }
	public Demon(Transform transform, CharacterData data, Node<?>... children) {
		super(transform, data, children);
	}

    @Override
	public void update(Transform offset, double delta) {
		if (health > 0) {
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
		super.update(offset, delta);
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
