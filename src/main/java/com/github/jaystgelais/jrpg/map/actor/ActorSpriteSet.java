package com.github.jaystgelais.jrpg.map.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public final class ActorSpriteSet {
    private final long timeToTraverseTileMs;
    private final Texture spriteSheet;
    private final Map<Direction, Animation<TextureRegion>> walking = new HashMap<>();
    private final Map<Direction, TextureRegion> standing = new HashMap<>();

    public ActorSpriteSet(final SpriteSetData spriteSetData, final long timeToTraverseTileMs,
                          final AssetManager assetManager) {
        this.timeToTraverseTileMs = timeToTraverseTileMs;
        if (!assetManager.isLoaded(spriteSetData.getSpriteSheetPath(), Texture.class)) {
            assetManager.load(spriteSetData.getSpriteSheetPath(), Texture.class);
            assetManager.finishLoading();
        }
        spriteSheet = assetManager.get(spriteSetData.getSpriteSheetPath(), Texture.class);
        TextureRegion[][] tmp = TextureRegion.split(
                spriteSheet,
                spriteSheet.getWidth() / spriteSetData.getColumns(),
                spriteSheet.getHeight() / spriteSetData.getRows()
        );
        walking.put(Direction.UP, new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(timeToTraverseTileMs) / spriteSetData.getColumns(),
                tmp[spriteSetData.getUpFacingRowIndex()]
        ));
        walking.put(Direction.RIGHT, new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(timeToTraverseTileMs) / spriteSetData.getColumns(),
                tmp[spriteSetData.getRightFacingRowIndex()]
        ));
        walking.put(Direction.DOWN, new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(timeToTraverseTileMs) / spriteSetData.getColumns(),
                tmp[spriteSetData.getDownFacingRowIndex()]
        ));
        walking.put(Direction.LEFT, new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(timeToTraverseTileMs) / spriteSetData.getColumns(),
                tmp[spriteSetData.getLeftFacingRowIndex()]
        ));
        standing.put(
                Direction.UP,
                tmp[spriteSetData.getUpFacingRowIndex()][spriteSetData.getUpFacingStandingIndex()]
        );
        standing.put(
                Direction.RIGHT,
                tmp[spriteSetData.getRightFacingRowIndex()][spriteSetData.getRightFacingStandingIndex()]
        );
        standing.put(
                Direction.DOWN,
                tmp[spriteSetData.getDownFacingRowIndex()][spriteSetData.getDownFacingStandingIndex()]
        );
        standing.put(
                Direction.LEFT,
                tmp[spriteSetData.getLeftFacingRowIndex()][spriteSetData.getLeftFacingStandingIndex()]
        );
    }

    public long getTimeToTraverseTileMs() {
        return timeToTraverseTileMs;
    }

    public Animation<TextureRegion> getWalkingAnimation(final Direction direction) {
        return walking.get(direction);
    }

    public TextureRegion getStandingImage(final Direction direction) {
        return standing.get(direction);
    }
}
