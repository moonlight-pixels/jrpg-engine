package com.moonlightpixels.jrpg.legacy.map;

import com.moonlightpixels.jrpg.legacy.graphics.Renderable;
import com.moonlightpixels.jrpg.legacy.state.Updatable;

public interface Entity extends Renderable, Updatable, Interactable {

    boolean isOccupying(final TileCoordinate coordinate);

    TileCoordinate getLocation();

    int getHeight();

    int getWidth();

    int getPositionX();

    int getPositionY();
}
