package com.moonlightpixels.jrpg.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public interface TextureProvider {
    Texture getTexture(AssetManager assetManager);
}
