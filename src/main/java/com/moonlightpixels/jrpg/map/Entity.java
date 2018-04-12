package com.moonlightpixels.jrpg.map;

import com.moonlightpixels.jrpg.graphics.Renderable;
import com.moonlightpixels.jrpg.state.Updatable;

public interface Entity extends Renderable, Updatable, Interactable {

    boolean isOccupying(final TileCoordinate coordinate);

    TileCoordinate getLocation();

    int getHeight();

    int getWidth();

    int getPositionX();

    int getPositionY();
}
