package com.github.jaystgelais.jrpg;

import com.github.jaystgelais.jrpg.inventory.Inventory;
import com.github.jaystgelais.jrpg.map.Location;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.party.Party;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class GameState implements Serializable {
    private static final long serialVersionUID = 115877165793665456L;

    private static final Map<String, Boolean> FLAGS = new HashMap<>();
    private static Location location;
    private static TileCoordinate locationOnMap;
    private static Party party;
    private static Inventory inventory;
    private static long timePlayed = 0;

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

    public static void setLocation(final Location location) {
        GameState.location = location;
    }

    public static TileCoordinate getLocationOnMap() {
        return locationOnMap;
    }

    public static void setLocationOnMap(final TileCoordinate locationOnMap) {
        GameState.locationOnMap = locationOnMap;
    }

    public static String getLocationDescription() {
        return (location == null) ? "?????" : location.getDescription(locationOnMap);
    }

    public static void initParty(final int size) {
        party = new Party(size);
    }

    public static Party getParty() {
        return party;
    }

    public static void initInventory(final int maxQuantityPerItem) {
        inventory = new Inventory(maxQuantityPerItem);
    }

    public static void initInventory() {
        inventory = new Inventory();
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static long getTimePlayed() {
        return timePlayed;
    }

    public static void incrementTimePlayed(final long timeMs) {
        timePlayed += timeMs;
    }
}
