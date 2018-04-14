package com.moonlightpixels.jrpg.legacy.map.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.map.Entity;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.TileCoordinate;
import com.moonlightpixels.jrpg.legacy.util.TimeUtil;

public final class AnimatedDecoration implements Entity {
    private final TileCoordinate location;
    private final AnimatedDecorationSpriteSet spriteSet;
    private final int positionX;
    private final int positionY;
    private final int height;
    private final int width;
    private long animationTime = 0L;

    public AnimatedDecoration(final GameMap map, final TileCoordinate location,
                              final AnimatedDecorationSpriteSet spriteSet) {
        this.location = location;
        this.spriteSet = spriteSet;
        positionX = map.getAbsoluteX(location);
        positionY = map.getAbsoluteY(location);
        height = spriteSet.getSpriteHeight();
        width = spriteSet.getSpriteWidth();
    }

    @Override
    public void update(final long elapsedTime) {
        animationTime = (animationTime + elapsedTime)
                % TimeUtil.convertFloatSecondsToLongMs(spriteSet.getAnimation().getAnimationDuration());
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        final TextureRegion frame = spriteSet.getAnimation().getKeyFrame(
                TimeUtil.convertMsToFloatSeconds(animationTime)
        );
        graphicsService.drawSprite(
                frame,
                positionX - (frame.getRegionWidth() / 2.0f), positionY
        );
    }

    @Override
    public boolean isOccupying(final TileCoordinate coordinate) {
        return false;
    }

    @Override
    public void interactWith() {

    }

    @Override
    public TileCoordinate getLocation() {
        return location;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void dispose() {

    }
}
