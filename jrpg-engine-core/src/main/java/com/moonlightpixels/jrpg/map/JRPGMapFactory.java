package com.moonlightpixels.jrpg.map;

public interface JRPGMapFactory {
    /**
     * Creates a JRPGMap rom the path to it's .tmx file.
     *
     * @param mapPath path to .tmx file
     * @return Loaded map
     */
    JRPGMap create(String mapPath);
}
