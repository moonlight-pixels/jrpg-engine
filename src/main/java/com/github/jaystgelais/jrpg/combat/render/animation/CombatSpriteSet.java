package com.github.jaystgelais.jrpg.combat.render.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.animation.SpriteSet;
import com.github.jaystgelais.jrpg.map.actor.Direction;

import java.util.HashMap;
import java.util.Map;

public final class CombatSpriteSet extends SpriteSet {
    private final Map<Direction, TextureRegion[]> idle = new HashMap<>();
    private final Map<Direction, TextureRegion[]> walking = new HashMap<>();
    private final Map<Direction, TextureRegion> knockedOut = new HashMap<>();
    private final Map<String, Map<Direction, TextureRegion[]>> attacks = new HashMap<>();

    public CombatSpriteSet(final int spriteHeight, final int spriteWidth) {
        super(spriteHeight, spriteWidth);
    }

    public Animation<TextureRegion> getIdleAnimation(final Direction direction, final long cycleTimeMs) {
        return buildAnimation(idle.get(direction), cycleTimeMs);
    }

    public void setIdleAnimation(final Direction direction, final TextureRegion[] frames) {
        idle.put(direction, frames);
    }

    public Animation<TextureRegion> getWalkingAnimation(final Direction direction, final long cycleTimeMs) {
        return buildAnimation(walking.get(direction), cycleTimeMs);
    }

    public void setWalkingAnimation(final Direction direction, final TextureRegion[] frames) {
        walking.put(direction, frames);
    }

    public TextureRegion getKoImage(final Direction direction) {
        return knockedOut.get(direction);
    }

    public void setKoImage(final Direction direction, final TextureRegion textureRegion) {
        knockedOut.put(direction, textureRegion);
    }

    public Animation<TextureRegion> getAttackAnamiation(final String attackType, final Direction direction,
                                                        final long cycleTimeMs) {
        return buildAnimation(attacks.get(attackType).get(direction), cycleTimeMs);
    }


    public void setAttackAnimation(final String attackType, final Direction direction, final TextureRegion[] frames) {
        attacks.computeIfAbsent(attackType, key -> new HashMap<>()).put(direction, frames);
    }
}
