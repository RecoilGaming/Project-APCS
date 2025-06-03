package com.apcs.ljaag.nodes.character.enemies;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.math.Transform;
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.character.CharacterData;

public class Enemy extends Character<CharacterData> {
    // Constructors
	public Enemy(CharacterData data) { this(new Transform(), data); }
	public Enemy(Transform transform, CharacterData data, Node<?>... children) {
		super(transform, data, children);
	}

}
