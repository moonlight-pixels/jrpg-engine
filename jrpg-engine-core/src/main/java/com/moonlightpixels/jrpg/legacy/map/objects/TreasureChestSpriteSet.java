package com.moonlightpixels.jrpg.legacy.map.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moonlightpixels.jrpg.legacy.animation.SpriteSet;

public final class TreasureChestSpriteSet extends SpriteSet {
    private final TextureRegion closedImage;
    private final TextureRegion openImage;
    private final Animation<TextureRegion> openingAnimation;

    public TreasureChestSpriteSet(final TextureRegion closedImage, final TextureRegion openImage,
                                  final Animation<TextureRegion> openingAnimation) {
        super(closedImage.getRegionHeight(), closedImage.getRegionWidth());
        this.closedImage = closedImage;
        this.openImage = openImage;
        this.openingAnimation = openingAnimation;
    }

    public TextureRegion getClosedImage() {
        return closedImage;
    }

    public TextureRegion getOpenImage() {
        return openImage;
    }

    public Animation<TextureRegion> getOpeningAnimation() {
        return openingAnimation;
    }
}
