package com.apcs.ljaag.nodes.indexed;

import com.apcs.disunity.game.nodes.twodim.Node2D;
import com.apcs.disunity.math.Vector2;

public class FollowNodeVector implements VectorSupplier {

    private final Node2D leader, follower;
    private final String name;

    public FollowNodeVector(String name, Node2D leader, Node2D follower) {
        this.leader = leader;
        this.follower = follower;
        this.name = name;
    }

    @Override
    public Vector2 get() {
        return leader.getPosition().sub(follower.getPosition()).normalized();
    }

    @Override
    public String index() {
        return name;
    }
    
}
