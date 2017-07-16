package com.github.jaystgelais.jrpg.map;

import java.util.Objects;

public final class TileCoordinate {
    private final int x;
    private final int y;
    private final int mapLayer;

    public TileCoordinate(final int x, final int y) {
        this(x, y, 0);
    }


    public TileCoordinate(final int x, final int y, final int mapLayer) {
        this.x = x;
        this.y = y;
        this.mapLayer = mapLayer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMapLayer() {
        return mapLayer;
    }

    public TileCoordinate getAbove() {
        return new TileCoordinate(x, y + 1, mapLayer);
    }

    public TileCoordinate getBelow() {
        return new TileCoordinate(x, y - 1, mapLayer);
    }

    public TileCoordinate getLeft() {
        return new TileCoordinate(x - 1, y, mapLayer);
    }

    public TileCoordinate getRight() {
        return new TileCoordinate(x + 1, y, mapLayer);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TileCoordinate)) {
            return false;
        }
        TileCoordinate that = (TileCoordinate) o;
        return x == that.x
                && y == that.y
                && mapLayer == that.mapLayer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, mapLayer);
    }

    @Override
    public String toString() {
        return String.format("{%d, %d}[%d]", x, y, mapLayer);
    }
}
