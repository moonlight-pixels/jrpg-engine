package com.moonlightpixels.jrpg.combat

import com.moonlightpixels.jrpg.combat.event.CombatEvent
import spock.lang.Specification

class CombatEventSpec extends Specification {
    void 'CompareTo'() {
        given:
        CombatEvent a = new MockCombatEvent(1)
        CombatEvent a1 = new MockCombatEvent(1)
        CombatEvent b = new MockCombatEvent(2)

        expect:
        a < b
        b > a
        a == a1
    }

    private static class MockCombatEvent extends CombatEvent {

        MockCombatEvent(final long ticksRemaining) {
            super(ticksRemaining)
        }

        @Override
        void start(final Battle battle) {

        }

        @Override
        boolean isComplete() {
            return false
        }

        @Override
        void update(final long elapsedTime) {

        }
    }
}
