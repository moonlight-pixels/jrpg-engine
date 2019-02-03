package com.moonlightpixels.jrpg.combat.render.internal;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonlightpixels.jrpg.combat.render.CharacterBattleAnimationSet;

public final class CombatActor extends Actor {
    private final CharacterBattleAnimationSet animationSet;

    public CombatActor(final CharacterBattleAnimationSet animationSet) {
        this.animationSet = animationSet;
    }

    public float getHeight() {
        return animationSet.getHeight();
    }

    public float getWidth() {
        return animationSet.getWidth();
    }
}
