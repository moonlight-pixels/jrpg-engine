package com.github.jaystgelais.jrpg.map;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface Entity extends Renderable, Updatable {

    boolean isOccupying(final TileCoordinate coordinate);

    void interactWith();

    TileCoordinate getLocation();

    int getHeight();

    int getWidth();

    int getPositionX();

    int getPositionY();
}
