package com.moonlightpixels.jrpg.tween

import spock.lang.Specification

class AbstractTweenSpec extends Specification {
    void 'Tween gets correct values'() {
        setup:
        IntegerTween tween = IntegerTween.builder()
            .start(0)
            .end(10)
            .totalTweenTime(1.0f)
            .build()

        when:
        tween.update(0.1f)

        then:
        tween.getValue() == 1
        !tween.isComplete()

        when:
        tween.update(1.0f)

        then:
        tween.getValue() == 10
        tween.isComplete()
    }

    void 'Tween rolls over values when repeating'() {
        setup:
        FloatTween tween = FloatTween.builder()
            .start(0)
            .end(10)
            .totalTweenTime(1.0f)
            .isRepeating(true)
            .build()

        when:
        tween.update(0.1f)

        then:
        tween.getValue() == 1

        when:
        tween.update(1.5f)

        then:
        tween.getValue() == 6
        !tween.isComplete()
    }
}
