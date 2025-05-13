package com.apcs.disunity.game;

import com.apcs.disunity.app.network.packet.annotation.SyncedObject;
import com.apcs.disunity.game.nodes.selector.Indexed;

import java.util.HashMap;
import java.util.List;

/// wrapper of map with some extra restrictions
public class Selector<K,V extends Indexed<K>> {
    private final V fallback;
    protected final HashMap<K, V> indexedValues = new HashMap<>();

    @SyncedObject
    private K index;

    public Selector(V fallback, V... values) {
        this.fallback = fallback;
        this.index = fallback.index();
        add(fallback);
        addAll(values);
    }

    public void add(V value) { indexedValues.put(value.index(), value); }
    public void addAll(V... values) { for(V value: values) add(value);}

    public V getSelected() { return indexedValues.getOrDefault(index, fallback); }
    public V select(K index) {
        this.index = index;
        return getSelected();
    }

    public List<V> values() { return indexedValues.values().stream().toList(); }
}
