package com.github.jaystgelais.jrpg.map;

import java.util.Objects;

public final class TileCoordinate {
    private final int x;
    private final int y;

    public TileCoordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileCoordinate getAbove() {
        return new TileCoordinate(x, y + 1);
    }

    public TileCoordinate getBelow() {
        return new TileCoordinate(x, y - 1);
    }

    public TileCoordinate getLeft() {
        return new TileCoordinate(x - 1, y);
    }

    public TileCoordinate getRight() {
        return new TileCoordinate(x + 1, y);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TileCoordinate that = (TileCoordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
