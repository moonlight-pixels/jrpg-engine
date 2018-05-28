package com.moonlightpixels.jrpg.map;

import lombok.Data;

@Data
public final class Location {
    private final MapDefinition map;
    private final TileCoordinate tileCoordinate;
}
