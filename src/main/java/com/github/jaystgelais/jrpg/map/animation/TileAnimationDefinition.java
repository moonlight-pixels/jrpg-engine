package com.github.jaystgelais.jrpg.map.animation;

import java.util.Arrays;

public final class TileAnimationDefinition {
    private final String id;
    private final long timePerFrameMs;
    private final int[] indexOrder;

    public TileAnimationDefinition(final String id, final long timePerFrameMs, final int... indexOrder) {
        this.id = id;
        this.timePerFrameMs = timePerFrameMs;
        this.indexOrder = Arrays.copyOf(indexOrder, indexOrder.length);
    }

    public String getId() {
        return id;
    }

    public long getTimePerFrameMs() {
        return timePerFrameMs;
    }

    public int[] getIndexOrder() {
        return Arrays.copyOf(indexOrder, indexOrder.length);
    }
}
