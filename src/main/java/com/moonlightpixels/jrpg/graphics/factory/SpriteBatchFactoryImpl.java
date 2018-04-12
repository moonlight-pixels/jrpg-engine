package com.moonlightpixels.jrpg.graphics.factory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class SpriteBatchFactoryImpl implements SpriteBatchFactory {
    @Override
    public SpriteBatch createSpriteBatch() {
        return new SpriteBatch();
    }
}
