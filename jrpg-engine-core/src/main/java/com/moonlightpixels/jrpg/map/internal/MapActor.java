package com.moonlightpixels.jrpg.map.internal;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonlightpixels.jrpg.animation.AnimationSet;
import com.moonlightpixels.jrpg.map.JRPGMap;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
public abstract class MapActor<T extends AnimationSet.Type> extends Actor {
    @Getter(value = AccessLevel.PROTECTED)
    private final JRPGMap map;

    @Getter(value = AccessLevel.PROTECTED)
    private final AnimationSet<T> animationSet;

    @Getter
    @Setter
    private TileCoordinate position;
    private Animation<TextureRegion> animation;
    private float timeInAnimation = 0f;

    public MapActor(final JRPGMap map, final AnimationSet<T> animationSet, final TileCoordinate position) {
        this.map = map;
        this.animationSet = animationSet;
        this.position = position;
    }

    protected final Optional<Animation<TextureRegion>> getAnimation() {
        return Optional.ofNullable(animation);
    }

    protected final void setAnimation(final T animationType, final float animationDuration) {
        animation = animationSet.getAnimation(animationType, animationDuration);
    }

    protected abstract float calculateX();

    protected abstract float calculateY();

    @Override
    public final void draw(final Batch batch, final float parentAlpha) {
        getAnimation()
            .ifPresent(animation -> {
                batch.draw(animation.getKeyFrame(timeInAnimation, true), getX(), getY());
            });
    }

    @Override
    public final void act(final float delta) {
        super.act(delta);
        timeInAnimation += delta;
        actInternal(delta);
    }

    protected void actInternal(final float delta) {

    }

    @Override
    public final float getX() {
        return calculateX();
    }

    @Override
    public final float getY() {
        return calculateY();
    }

    protected final float calculateXForCenteredOnTile(final TileCoordinate tileCoordinate) {
        return map.getTileCoordinateXY(tileCoordinate).x + (map.getTileWidth() / 2f) - (animationSet.getWidth() / 2f);
    }

    protected final float calculateYForCenteredOnTile(final TileCoordinate tileCoordinate) {
        return map.getTileCoordinateXY(tileCoordinate).y + (map.getTileHeight() / 2f) - (animationSet.getHeight() / 2f);
    }

}
