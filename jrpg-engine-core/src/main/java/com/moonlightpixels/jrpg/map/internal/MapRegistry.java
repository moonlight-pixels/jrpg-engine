package com.moonlightpixels.jrpg.map.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.map.MapDefinition;

import java.util.HashMap;
import java.util.Map;

public final class MapRegistry {
    private final Map<MapDefinition.Key, MapDefinition> maps = new HashMap<>();

    public void register(final MapDefinition map) {
        maps.put(map.getKey(), map);
    }

    public MapDefinition getMap(final MapDefinition.Key key) {
        Preconditions.checkState(maps.containsKey(key), String.format("Map with key {%s} does not exist", key));
        return maps.get(key);
    }
}
