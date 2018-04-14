package com.moonlightpixels.jrpg.legacy.graphics.factory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class SpriteBatchFactoryImpl implements SpriteBatchFactory {
    @Override
    public SpriteBatch createSpriteBatch() {
        return new SpriteBatch();
    }
}
