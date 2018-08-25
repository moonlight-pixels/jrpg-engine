package com.moonlightpixels.jrpg.tween

import spock.lang.Specification

class SineTweenFunctionSpec extends Specification {
    void 'Always returns a positive value'() {
        setup:
        TweenFunction function = new SineTweenFunction()

        expect:
        for (float percent = 0f; percent <= 1f; percent += 0.01) {
            assert function.getPercentBetweenPoints(percent) >= 0f
        }
    }
}
