package com.moonlightpixels.jrpg.ui;

/**
 * Wrapper around scene2d.ui Skin that allows for fallback styles.
 */
public interface UiStyle {
    /**
     * Default style name.
     */
    String DEFAULT_STYLE = "default";

    /**
     * Will return object matching requested style, or default style if requested style not present.
     *
     * @param style Reqeusted Style.
     * @param type Class of requested type.
     * @param <T> Requested type.
     * @return Object of requested type matching requested or default style.
     */
    <T> T get(String style, Class<T> type);

    /**
     * Will return object matching the default style.
     *
     * @param type Class of requested type.
     * @param <T> Requested type.
     * @return Object of requested type matching default style.
     */
    <T> T get(Class<T> type);

    /**
     * Set the default style for an object type.
     *
     * @param style Name of stye.
     * @param value Class ot styled type.
     * @param <T> Styled type.
     */
    <T> void set(String style, T value);
}
