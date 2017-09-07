package com.github.jaystgelais.jrpg.map.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.util.TimeUtil;

public abstract class SpriteSetDefinition<T extends SpriteSet> {
    public abstract T getSpriteSet(final AssetManager assetManager);

    protected final TextureRegion[][] loadSpriteSheet(final String spriteSheetPath, final AssetManager assetManager,
                                                      final int frameWidth, final int frameHeight) {
        if (!assetManager.isLoaded(spriteSheetPath, Texture.class)) {
            assetManager.load(spriteSheetPath, Texture.class);
            assetManager.finishLoading();
        }
        return TextureRegion.split(assetManager.get(spriteSheetPath, Texture.class), frameWidth, frameHeight);
    }

    private Animation<TextureRegion> buildAnimation(final TextureRegion[] frames, final long timeToTraverseTileMs) {
        return new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(timeToTraverseTileMs) / frames.length,
                frames
        );
    }

}
