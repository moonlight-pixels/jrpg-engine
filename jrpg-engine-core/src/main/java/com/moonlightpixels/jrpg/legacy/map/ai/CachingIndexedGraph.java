package com.moonlightpixels.jrpg.legacy.map.ai;

import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;

public interface CachingIndexedGraph<N> extends IndexedGraph<N> {
    N getCachedNode(N matching);
}
