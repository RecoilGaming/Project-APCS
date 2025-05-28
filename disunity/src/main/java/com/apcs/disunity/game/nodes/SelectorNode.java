package com.apcs.disunity.game.nodes;

import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.selector.Indexed;
import com.apcs.disunity.game.selector.Selector;
import com.apcs.disunity.math.Transform;

import java.util.List;

/// Node that selects one of its children to be updated or drawn.
public class SelectorNode<K, V extends Node<?> & Indexed<K>> extends Node<V> {

    @SyncedObject
    private final Selector<K, V> children;

    public SelectorNode(V fallback, V... children) {
        super();
        this.children = new Selector<>(fallback, children);
    }

    // stop update propagation
    @Override
    public void update(double delta) { getSelected().update(delta); }

    @Override
    public void draw(Transform transform) { getSelected().draw(transform); }

    @Override
    public void addChild(V node) { children.add(node); }

    @Override
    public List<V> getDynamicChildren() { return children.values().stream().toList(); }

    public V getSelected() { return children.getSelected(); }

    public V select(K index) { return children.select(index); }
}
