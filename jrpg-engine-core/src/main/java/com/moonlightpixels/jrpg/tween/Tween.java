package com.moonlightpixels.jrpg.tween;

/**
 * A Tween tracks the transition over time between 2 values. Common uses for a tween ight be to move a sprite between
 * two coordinates or fade a scene in/out.
 *
 * @param <T> Type of value we are <i>'tweening'</i> between.
 */
public interface Tween<T> {
    /**
     * Returns the current value of this tween.
     *
     * @return Current value of tween
     */
    T getValue();

    /**
     * Whether or not this tween has completed, reaching it's end value.
     *
     * @return true if Tween is complete
     */
    boolean isComplete();

    /**
     * Updates the state of the Tween by recorind the time passed.
     *
     * @param elapsedTime Time passed (in seconds) since last update
     */
    void update(float elapsedTime);
}
