package com.moonlightpixels.jrpg.map.character.internal;

import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;

import java.util.HashMap;
import java.util.Map;

public final class CharacterAnimationSetRegistry {
    private final Map<String, CharacterAnimationSet> animationSets = new HashMap<>();

    public void register(final CharacterAnimationSet characterAnimationSet) {
        animationSets.put(characterAnimationSet.getId(), characterAnimationSet);
    }

    public CharacterAnimationSet getCharacterAnimationSet(final String animationSetId) {
        return animationSets.get(animationSetId);
    }
}
