package com.moonlightpixels.jrpg.map.internal;

import com.moonlightpixels.jrpg.map.MapDefinition;

import java.util.HashMap;
import java.util.Map;

public final class MapRegistry {
    private final Map<String, MapDefinition> maps = new HashMap<>();

    public void register(final MapDefinition map) {
        maps.put(map.getId(), map);
    }

    public MapDefinition getMap(final String mapId) {
        return maps.get(mapId);
    }
}
