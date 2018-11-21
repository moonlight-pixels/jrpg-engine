package com.moonlightpixels.jrpg.map.character.internal;

import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;

import java.util.HashMap;
import java.util.Map;

public final class CharacterAnimationSetRegistry {
    private final Map<CharacterAnimationSet.Key, CharacterAnimationSet> animationSets = new HashMap<>();

    public void register(final CharacterAnimationSet characterAnimationSet) {
        animationSets.put(characterAnimationSet.getKey(), characterAnimationSet);
    }

    public CharacterAnimationSet getCharacterAnimationSet(final CharacterAnimationSet.Key key) {
        return animationSets.get(key);
    }
}
