package com.moonlightpixels.jrpg.map;

import lombok.Data;

@Data
public final class Location {
    private final MapDefinition.Key map;
    private final TileCoordinate tileCoordinate;
}
