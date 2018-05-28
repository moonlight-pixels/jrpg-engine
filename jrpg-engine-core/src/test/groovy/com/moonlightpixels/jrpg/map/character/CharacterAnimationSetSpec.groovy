package com.moonlightpixels.jrpg.map.character

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import spock.lang.Specification

class CharacterAnimationSetSpec extends Specification {
    void 'validate() raises error if any animations are missing'() {
        when:
        new CharacterAnimationSet(0, 0).validate()

        then:
        thrown IllegalStateException
    }

    void 'validate() passes if no animations are missing'() {
        setup:
        TextureRegion frame = Mock(TextureRegion)
        TextureRegion[] frames = [frame]

        when:
        CharacterAnimationSet animationSet = new CharacterAnimationSet(0, 0)
        CharacterAnimation.values().collect().forEach { type ->
            animationSet.addAnimationFrames(type, frames)
        }
        animationSet.validate()

        then:
        noExceptionThrown()
    }

    void 'getAnimation() returns an animation if it is in the set'() {
        setup:
        TextureRegion frame = Mock(TextureRegion)
        TextureRegion[] frames = [frame, frame]

        when:
        CharacterAnimationSet animationSet = new CharacterAnimationSet(0, 0)
        animationSet.addAnimationFrames(CharacterAnimation.StandDown, frames)
        Animation<TextureRegion> animation = animationSet.getAnimation(CharacterAnimation.StandDown, 1.0f)

        then:
        animation.frameDuration == 0.5f
    }
}
