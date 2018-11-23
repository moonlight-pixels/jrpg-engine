package com.moonlightpixels.jrpg.animation;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Provider that allows specification of content to be loaded after Game system initializes.
 *
 * @param <T> The specific type of AnimationSet provided.
 */
public interface AnimationSetProvider<T extends AnimationSet<?>> {
    /**
     * Returns an AnimationSet. The provided AssetManager should be used for loading any required Textues.
     *
     * @param assetManager AssetManager to be used for loading any required Textues
     * @return AnimationSet
     */
    T get(AssetManager assetManager);
}
