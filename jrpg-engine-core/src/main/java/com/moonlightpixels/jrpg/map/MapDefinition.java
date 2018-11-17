package com.moonlightpixels.jrpg.map;

import lombok.Data;

/**
 * Map definitions encapsulate the setup of game maps.
 */
@Data
public abstract class MapDefinition {
    private final String id;
    private final String mapPath;

    /**
     * Method to configure a map after being loaded form it's .tmx file.
     *
     * @param map Map to configure
     */
    protected abstract void configure(JRPGMap map);

    /**
     * Load map for use.
     *
     * @param factory Factory to initialize map from it's .tmx file
     * @return Loaded/configured map
     */
    public final JRPGMap load(final JRPGMapFactory factory) {
        JRPGMap map = factory.create(mapPath);
        configure(map);

        return map;
    }
}
