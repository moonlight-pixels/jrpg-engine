package com.moonlightpixels.jrpg.util

import static spock.util.matcher.HamcrestMatchers.closeTo
import spock.lang.Specification

class TimeUtilSpec extends Specification {
    void 'ConvertMsToFloatSeconds'(long provided, float expected) {
        when:
        float calculation = TimeUtil.convertMsToFloatSeconds(provided)

        then:
        calculation closeTo(expected, 0.005f)

        where:
        provided | expected
        300      | 0.3f
        1200     | 1.2f
    }

    void 'CalculatePercentComplete'(long current, long total, float expected) {
        when:
        float calculation = TimeUtil.calculatePercentComplete(current, total)

        then:
        calculation closeTo(expected, 0.005f)

        where:
        current | total | expected
        300     | 1000  | 0.3f
        200     | 600   | 0.3333f
    }
}
