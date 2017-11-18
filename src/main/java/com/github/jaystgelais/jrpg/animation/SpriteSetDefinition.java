package com.github.jaystgelais.jrpg.animation;

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

    protected final Animation<TextureRegion> buildAnimation(final long totalAnimationTimeMs,
                                                            final TextureRegion ...frames) {
        return new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(totalAnimationTimeMs) / frames.length,
                frames
        );
    }

}
