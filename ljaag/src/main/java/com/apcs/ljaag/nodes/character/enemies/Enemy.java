package com.apcs.ljaag.nodes.character.enemies;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.character.CharacterData;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.stats.StatType;

public class Enemy extends Character<CharacterData> {
    // Constructors
	public Enemy(CharacterData data) { this(new Transform(), data); }
	public Enemy(Transform transform, CharacterData data, Node<?>... children) {
		super(transform, data, children);
	}

    // contact damage
    private double lastAttack = 0;
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
