package com.moonlightpixels.jrpg.combat.render;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface BattleBackground {
    /**
     * Renders this background using the provided Batch.
     *
     * @param batch Batch used in rendering
     */
    void render(Batch batch);

    /**
     * Update Background's state.
     *
     * @param elapsedTime Time elapsed (in seconds) since last update.
     */
    void update(float elapsedTime);
}
