package com.apcs.disunity.game.physics;

/// Represents collision layers available in game.
/// nth bit of BITSET is set to 1, where n is the ordinal.
/// You can "name" layers by storing them in a static final field.
/// static import is recommended upon using.
public enum CollisionLayer {
    LAYER_0,  LAYER_1,  LAYER_2,  LAYER_3,  LAYER_4,  LAYER_5,  LAYER_6,  LAYER_7,
    LAYER_8,  LAYER_9,  LAYER_10, LAYER_11, LAYER_12, LAYER_13, LAYER_14, LAYER_15,
    LAYER_16, LAYER_17, LAYER_18, LAYER_19, LAYER_20, LAYER_21, LAYER_22, LAYER_23,
    LAYER_24, LAYER_25, LAYER_26, LAYER_27, LAYER_28, LAYER_29, LAYER_30, LAYER_31;
    public final int BITSET;

    CollisionLayer() {
        BITSET = 1<<this.ordinal();
    }
    public static CollisionLayer of(int layerId) {
        if (layerId >= Integer.SIZE || layerId < 0)
            throw new IllegalArgumentException("layerId was out of supported range");
        return values()[layerId];
    }
}
