package com.github.jaystgelais.jrpg.map;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface Entity extends Renderable, Updatable, Interactable {

    boolean isOccupying(final TileCoordinate coordinate);

    TileCoordinate getLocation();

    int getHeight();

    int getWidth();

    int getPositionX();

    int getPositionY();
}
