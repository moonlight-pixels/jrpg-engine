package com.moonlightpixels.jrpg.map.character.internal;

import com.badlogic.gdx.assets.AssetManager;
import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.animation.AnimationSetProvider;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public final class CharacterAnimationSetRegistry {
    private final Map<CharacterAnimationSet.Key, CharacterAnimationSet> animationSets = new HashMap<>();
    @SuppressWarnings("LineLength")
    private final Map<CharacterAnimationSet.Key, AnimationSetProvider<CharacterAnimationSet>> providers = new HashMap<>();
    private final AssetManager assetManager;

    @Inject
    public CharacterAnimationSetRegistry(final AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void register(final CharacterAnimationSet.Key key,
                         final AnimationSetProvider<CharacterAnimationSet> provider) {
        providers.put(key, provider);
    }

    public CharacterAnimationSet getCharacterAnimationSet(final CharacterAnimationSet.Key key) {
        return animationSets.computeIfAbsent(key, this::createCharacterAnimationSet);
    }

    private CharacterAnimationSet createCharacterAnimationSet(final CharacterAnimationSet.Key key) {
        Preconditions.checkState(
            providers.containsKey(key),
            String.format("AnimationSet with key {%s} does not exist", key)
        );
        return providers.get(key).get(assetManager);
    }
}
