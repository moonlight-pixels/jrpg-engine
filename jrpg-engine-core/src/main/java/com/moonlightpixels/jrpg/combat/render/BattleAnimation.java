package com.moonlightpixels.jrpg.combat.render;

public interface BattleAnimation {
    /**
     * Starts the animation.
     */
    void start();

    /**
     * Update teh state of the animation.
     *
     * @param elapsedTime Time elapsed since last call to update (in seconds).
     */
    void update(float elapsedTime);

    /**
     * Returns true if animation is complete.
     *
     * @return true if animation is complete
     */
    boolean isComplete();
}
