package com.apcs.ljaag.nodes.character.enemies;

import java.util.LinkedList;
import java.util.List;

import com.apcs.disunity.game.nodes.Node;
import com.apcs.ljaag.nodes.character.immortals.Immortal;

public class EnemyManager extends Node {

    public final double deactivationRadius;

    public EnemyManager(double deactivationRadius) {
        this.deactivationRadius = deactivationRadius;
    }

    private final List<Enemy> enemies = new LinkedList<>();
    private final List<Immortal> players = new LinkedList<>();

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Immortal> getPlayers() {
        return players;
    }

    @Override
    public void update(double dt) {
        for (Enemy e : enemies) {
            double closest = Double.MAX_VALUE;
            for (Immortal p : players) {
                closest = Math.min(e.getGlobalTrans().pos.sub(p.getGlobalTrans().pos).length(), closest);
            }
            e.setDisabled(closest > deactivationRadius);
        }
        
    }
}
