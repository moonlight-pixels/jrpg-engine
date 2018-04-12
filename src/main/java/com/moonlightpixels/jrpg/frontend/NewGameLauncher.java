package com.moonlightpixels.jrpg.frontend;

import com.moonlightpixels.jrpg.map.MapDefinition;
import com.moonlightpixels.jrpg.map.TileCoordinate;

public interface NewGameLauncher {
    void initGameState();
    MapDefinition getInitialMap();
    TileCoordinate getInitialLocation();
}
