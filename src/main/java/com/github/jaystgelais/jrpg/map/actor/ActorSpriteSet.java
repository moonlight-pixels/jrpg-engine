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
        walking.put(Direction.UP, build3CycleWalk(tmp[spriteSetData.getUpFacingRowIndex()], timeToTraverseTileMs));
        walking.put(
                Direction.RIGHT, build3CycleWalk(tmp[spriteSetData.getRightFacingRowIndex()], timeToTraverseTileMs)
        );
        walking.put(Direction.DOWN, build3CycleWalk(tmp[spriteSetData.getDownFacingRowIndex()], timeToTraverseTileMs));
        walking.put(Direction.LEFT, build3CycleWalk(tmp[spriteSetData.getLeftFacingRowIndex()], timeToTraverseTileMs));
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

    private Animation<TextureRegion> build3CycleWalk(final TextureRegion[] frames, final long timeToTraverseTileMs) {
        TextureRegion[] animationFrames = {
                frames[0],
                frames[1],
                frames[2],
                frames[1]
        };
        return new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(timeToTraverseTileMs) / animationFrames.length,
                animationFrames
        );
    }
}
