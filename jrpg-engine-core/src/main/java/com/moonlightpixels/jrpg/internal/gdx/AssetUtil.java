package com.moonlightpixels.jrpg.internal.gdx;

import com.badlogic.gdx.assets.AssetManager;

public final class AssetUtil {
    private AssetUtil() { }

    public static <T> T onDemand(final AssetManager assetManager, final String path, final Class<T> assetType) {
        if (!assetManager.isLoaded(path, assetType)) {
            assetManager.load(path, assetType);
            assetManager.finishLoadingAsset(path);
        }

        return assetManager.get(path, assetType);
    }
}
