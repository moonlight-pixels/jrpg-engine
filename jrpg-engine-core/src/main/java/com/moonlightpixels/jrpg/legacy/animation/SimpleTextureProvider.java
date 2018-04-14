package com.moonlightpixels.jrpg.legacy.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public final class SimpleTextureProvider implements TextureProvider {
    private final String texturePath;

    public SimpleTextureProvider(final String texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public Texture getTexture(final AssetManager assetManager) {
        if (!assetManager.isLoaded(texturePath, Texture.class)) {
            assetManager.load(texturePath, Texture.class);
            assetManager.finishLoading();
        }
        return assetManager.get(texturePath, Texture.class);
    }
}
