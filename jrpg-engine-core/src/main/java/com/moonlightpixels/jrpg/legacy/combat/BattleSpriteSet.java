package com.moonlightpixels.jrpg.legacy.combat;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moonlightpixels.jrpg.legacy.animation.SpriteSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class BattleSpriteSet extends SpriteSet {
    private final TextureRegion[] idle;
    private final TextureRegion[] walking;
    private final TextureRegion[] dead;
    private final Map<String, TextureRegion[]> actions = new HashMap<>();
    private final Map<String, TextureRegion[]> statuses = new HashMap<>();
    private final Map<String, TextureRegion[]> custom = new HashMap<>();
    private TextureRegion[] takeHit;
    private TextureRegion[] dodge;
    private TextureRegion[] victory;
    private TextureRegion[] critical;

    public BattleSpriteSet(final int spriteHeight, final int spriteWidth, final TextureRegion[] idle,
                           final TextureRegion[] walking, final TextureRegion[] dead) {
        super(spriteHeight, spriteWidth);
        this.idle = Arrays.copyOf(idle, idle.length);
        this.walking = Arrays.copyOf(walking, walking.length);
        this.dead = Arrays.copyOf(dead, dead.length);
    }

    public Animation<TextureRegion> getIdle(final long animationTimeMs) {
        return buildAnimation(idle, animationTimeMs);
    }

    public Animation<TextureRegion> getWalking(final long animationTimeMs) {
        return buildAnimation(walking, animationTimeMs);
    }

    public Animation<TextureRegion> getDead(final long animationTimeMs) {
        return buildAnimation(dead, animationTimeMs);
    }

    public void addActionAnimation(final String actionType, final TextureRegion[] animation) {
        actions.put(actionType, Arrays.copyOf(animation, animation.length));
    }

    public Animation<TextureRegion> getActionAnimation(final String actionType, final long animationTimeMs) {
        return buildAnimation(actions.get(actionType), animationTimeMs);
    }

    public void addStatusAnimation(final String status, final TextureRegion[] animation) {
        statuses.put(status, Arrays.copyOf(animation, animation.length));
    }

    public Optional<Animation<TextureRegion>> getStatusAnimation(final String status, final long animationTimeMs) {
        return Optional.ofNullable(statuses.get(status)).map(frames -> buildAnimation(frames, animationTimeMs));
    }

    public void addCustomAnimation(final String customType, final TextureRegion[] animation) {
        custom.put(customType, Arrays.copyOf(animation, animation.length));
    }

    public Optional<Animation<TextureRegion>> getCustomAnimation(final String customType, final long animationTimeMs) {
        return Optional.ofNullable(statuses.get(customType)).map(frames -> buildAnimation(frames, animationTimeMs));
    }

    public void setTakeHit(final TextureRegion[] takeHit) {
        this.takeHit = Arrays.copyOf(takeHit, takeHit.length);
    }

    public Optional<Animation<TextureRegion>> getTakeHit(final long animationTimeMs) {
        return Optional.ofNullable(takeHit).map(frames -> buildAnimation(frames, animationTimeMs));
    }

    public void setDodge(final TextureRegion[] dodge) {
        this.dodge = Arrays.copyOf(dodge, dodge.length);
    }

    public Optional<Animation<TextureRegion>> getDodge(final long animationTimeMs) {
        return Optional.ofNullable(dodge).map(frames -> buildAnimation(frames, animationTimeMs));
    }

    public void setVictory(final TextureRegion[] victory) {
        this.victory = Arrays.copyOf(victory, victory.length);
    }

    public Optional<Animation<TextureRegion>> getVictory(final long animationTimeMs) {
        return Optional.ofNullable(victory).map(frames -> buildAnimation(frames, animationTimeMs));
    }

    public void setCritical(final TextureRegion[] critical) {
        this.critical = Arrays.copyOf(critical, critical.length);
    }

    public Optional<Animation<TextureRegion>> getCritical(final long animationTimeMs) {
        return Optional.ofNullable(critical).map(frames -> buildAnimation(frames, animationTimeMs));
    }
}
