package com.moonlightpixels.jrpg.legacy.frontend;

import com.moonlightpixels.jrpg.legacy.map.MapDefinition;
import com.moonlightpixels.jrpg.legacy.map.TileCoordinate;

public interface NewGameLauncher {
    void initGameState();
    MapDefinition getInitialMap();
    TileCoordinate getInitialLocation();
}
