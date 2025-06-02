package com.apcs.ljaag.nodes;

import java.awt.Color;

import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Node;
import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;
import com.apcs.ljaag.nodes.character.Character;
import com.apcs.ljaag.nodes.stats.StatType;

public class HealthBar extends Node2D<Node<?>> {

    public HealthBar(Node<?>... children) {
        super(children);
    }

    public HealthBar(Transform localTrans, Node<?>... children) {
        super(localTrans, children);
    }

    public static final Vector2 HEALTH_BAR_DIMENSIONS = Vector2.of(10, 2);

    @Override
    public void draw(Transform offset) {
        super.draw(offset);
        if (getParent() instanceof Character c) {
            double healthProportion = Math.max(0, 2 * (double) c.getHealth() / c.getStat(StatType.HEALTH));
            if (healthProportion == 0) return;
            Game.getInstance().getBuffer().drawRect((int) (HEALTH_BAR_DIMENSIONS.xi * healthProportion * getScale().x), (int) (HEALTH_BAR_DIMENSIONS.yi * getScale().y), Color.green, offset.addPos(Vector2.of(-HEALTH_BAR_DIMENSIONS.xi / 2 * getScale().x, 0)).addPos(getPosition()));
            Game.getInstance().getBuffer().drawRect((int) (HEALTH_BAR_DIMENSIONS.xi * (1 - healthProportion) * getScale().x), (int) (HEALTH_BAR_DIMENSIONS.yi * getScale().y), Color.red, offset.addPos(Vector2.of(HEALTH_BAR_DIMENSIONS.xi * (healthProportion - 0.5) * getScale().x, 0)).addPos(getPosition()));
        } else {
            throw new RuntimeException("A health bar must belong to a character");
        }
        
    }
    
    
}
