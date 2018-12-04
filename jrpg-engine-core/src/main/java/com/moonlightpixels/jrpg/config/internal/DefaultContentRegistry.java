package com.moonlightpixels.jrpg.config.internal;

import com.moonlightpixels.jrpg.animation.AnimationSetProvider;
import com.moonlightpixels.jrpg.config.ContentRegistry;
import com.moonlightpixels.jrpg.inventory.Item;
import com.moonlightpixels.jrpg.inventory.internal.ItemRegistry;
import com.moonlightpixels.jrpg.map.MapDefinition;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.map.character.internal.CharacterAnimationSetRegistry;
import com.moonlightpixels.jrpg.map.internal.MapRegistry;

import javax.inject.Inject;

public final class DefaultContentRegistry implements ContentRegistry {
    private final CharacterAnimationSetRegistry characterAnimationSetRegistry;
    private final MapRegistry mapRegistry;
    private final ItemRegistry itemRegistry;

    @Inject
    public DefaultContentRegistry(final CharacterAnimationSetRegistry characterAnimationSetRegistry,
                                  final MapRegistry mapRegistry,
                                  final ItemRegistry itemRegistry) {
        this.characterAnimationSetRegistry = characterAnimationSetRegistry;
        this.mapRegistry = mapRegistry;
        this.itemRegistry = itemRegistry;
    }

    @Override
    public void register(final CharacterAnimationSet.Key key,
                         final AnimationSetProvider<CharacterAnimationSet> provider) {
        characterAnimationSetRegistry.register(key, provider);
    }

    @Override
    public void register(final MapDefinition map) {
        mapRegistry.register(map);
    }

    @Override
    public void register(final Item item) {
        itemRegistry.register(item);
    }
}
