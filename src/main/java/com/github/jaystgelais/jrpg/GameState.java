package com.github.jaystgelais.jrpg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GameState implements Serializable {
    private static final long serialVersionUID = 115877165793665456L;

    private static final Map<String, Boolean> flags = new HashMap<>();

    private GameState() {}

    public static boolean checkFlag(final String key) {
        return checkFlag(key, false);
    }

    public static boolean checkFlag(final String key, final boolean defaultValue) {
        return flags.computeIfAbsent(key, k -> defaultValue);
    }

    public static void setFlag(final String key, final boolean value) {
        flags.put(key, value);
    }

}
