package com.moonlightpixels.jrpg.config.internal

import com.badlogic.gdx.assets.AssetManager
import com.moonlightpixels.jrpg.animation.AnimationSetProvider
import com.moonlightpixels.jrpg.config.ContentRegistry
import com.moonlightpixels.jrpg.map.JRPGMap
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet
import com.moonlightpixels.jrpg.map.character.internal.CharacterAnimationSetRegistry
import com.moonlightpixels.jrpg.map.internal.MapRegistry
import spock.lang.Specification

class DefaultContentRegistrySpec extends Specification {
    AssetManager assetManager
    MapRegistry mapRegistry
    CharacterAnimationSetRegistry characterAnimationSetRegistry
    ContentRegistry contentRegistry

    void setup() {
        assetManager = Mock()
        mapRegistry = new MapRegistry()
        characterAnimationSetRegistry = new CharacterAnimationSetRegistry(assetManager)
        contentRegistry = new DefaultContentRegistry(characterAnimationSetRegistry, mapRegistry)
    }

    void 'registering a MapDefinition adds it to the correct registry'() {
        setup:
        MapDefinition mapDefinition = new MapDefinition(Mock(MapDefinition.Key), 'path') {
            @Override
            protected void configure(final JRPGMap map) { }
        }

        when:
        contentRegistry.register(mapDefinition)

        then:
        mapRegistry.getMap(mapDefinition.key) == mapDefinition
    }

    void 'registering a CharacterAnimationSetProvider adds it to the correct registry'() {
        setup:
        CharacterAnimationSet.Key key = Mock()
        CharacterAnimationSet animationSet = new CharacterAnimationSet(key, 16, 16, 2)
        AnimationSetProvider<CharacterAnimationSet> provider = new AnimationSetProvider<CharacterAnimationSet>() {
            @Override
            CharacterAnimationSet get(final AssetManager assetManager) {
                return animationSet
            }
        }

        when:
        contentRegistry.register(key, provider)

        then:
        characterAnimationSetRegistry.getCharacterAnimationSet(key) == animationSet
    }
}
