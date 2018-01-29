package com.github.jaystgelais.jrpg.frontend;

import com.github.jaystgelais.jrpg.map.MapDefinition;
import com.github.jaystgelais.jrpg.map.TileCoordinate;

public interface NewGameLauncher {
    void initGameState();
    MapDefinition getInitialMap();
    TileCoordinate getInitialLocation();
}
