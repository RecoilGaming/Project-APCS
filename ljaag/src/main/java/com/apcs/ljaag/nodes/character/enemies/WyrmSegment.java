package com.apcs.ljaag.nodes.character.enemies;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.sprite.AnimatedSprite;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.game.physics.BodyEntered;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.character.CharacterData;
import com.apcs.ljaag.nodes.character.immortals.Immortal;
import com.apcs.ljaag.nodes.stats.StatType;

public class WyrmSegment extends Character<CharacterData> {

    private Character leader;

    private static final double FOLLOW_RADIUS = 10;

    // Constructors
	public WyrmSegment(Character leader, CharacterData data) {
        this(new Transform(), leader, data);
    }
	public WyrmSegment(Transform transform, Character leader, CharacterData data, Node<?>... children) {
		super(transform, data, children);
        this.leader = leader;
        if (leader != null && leader instanceof WyrmSegment) {
            setPosition(leader.getPosition());
        }
        AnimatedSprite idle = sprite.get("idle");
        AnimatedSprite run = sprite.get("run");
        idle.setBaseImage(new ImageLocation("worm/idle.png"));
        run.setBaseImage(new ImageLocation("worm/idle.png"));
        idle.setFrameDurations(Double.MAX_VALUE);
		run.setFrameDurations(Double.MAX_VALUE);
        
        idle.setScale(Vector2.of(0.05, 0.05));
		run.setScale(Vector2.of(0.05, 0.05));
        idle.setRotationType(Sprite.RotationType.NORMAL);
        run.setRotationType(Sprite.RotationType.NORMAL);
        idle.setRotationOffset(Math.PI / 2);
        run.setRotationOffset(Math.PI / 2);
	}

    @Override
	public void update(double delta) {
        super.update(delta);
		if (health >= 0) {
            if (leader == null || leader instanceof WyrmSegment && leader.getHealth() <= 0) {
                Immortal closestPlayer = null;
                for (Node n : getParent().getAllChildren()) {
                    if (n instanceof Immortal i) {
                        if (closestPlayer == null || closestPlayer.getPosition().sub(getPosition()).length() < i.getPosition().sub(getPosition()).length()) {
                            closestPlayer = i;
                        }
                    }
                }
                leader = closestPlayer;
            }
            setVelocity(leader.getPosition().sub(getPosition()).normalized().mul(getStat(StatType.SPEED)));
            if (leader instanceof WyrmSegment) {
                setVelocity(getVelocity().mul(leader.getPosition().sub(getPosition()).length() / FOLLOW_RADIUS));
            }
		}
	}

    @Override
    protected void onDeath() {
        setVelocity(Vector2.ZERO);
    }

    double lastAttack = 0;

    @Override
    public void onBodyEntered(BodyEntered signal) {
        if (!(signal.body instanceof WyrmSegment)) {
            super.onBodyEntered(signal);
        }
        if (health <= 0) {
            return;
        }
        if (signal.body instanceof Immortal m) {
            if (System.currentTimeMillis() - lastAttack > StatType.ATTACK_SPEED.getInitial()) {
                m.modifyHealth(-getStat(StatType.ATTACK_DAMAGE) * (leader == m ? 2 : 1));
                lastAttack = System.currentTimeMillis();
            }
        }
    }

}
