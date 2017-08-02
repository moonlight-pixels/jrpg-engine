package com.github.jaystgelais.jrpg;

import com.github.jaystgelais.jrpg.map.Location;
import com.github.jaystgelais.jrpg.map.TileCoordinate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class GameState implements Serializable {
    private static final long serialVersionUID = 115877165793665456L;

    private static final Map<String, Boolean> FLAGS = new HashMap<>();
    private static Location location;
    private static TileCoordinate locationOnMap;

    private GameState() { }

    public static boolean checkFlag(final String key) {
        return checkFlag(key, false);
    }

    public static boolean checkFlag(final String key, final boolean defaultValue) {
        return FLAGS.computeIfAbsent(key, k -> defaultValue);
    }

    public static void setFlag(final String key, final boolean value) {
        FLAGS.put(key, value);
    }

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        GameState.location = location;
    }

    public static TileCoordinate getLocationOnMap() {
        return locationOnMap;
    }

    public static void setLocationOnMap(TileCoordinate locationOnMap) {
        GameState.locationOnMap = locationOnMap;
    }

    public static String getLocationDescription() {
        return (location == null) ? "?????" : location.getDescription(locationOnMap);
    }
}
