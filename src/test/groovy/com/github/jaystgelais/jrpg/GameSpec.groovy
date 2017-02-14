package com.github.jaystgelais.jrpg

import com.github.jaystgelais.jrpg.state.StackedStateMachine
import com.github.jaystgelais.jrpg.state.State
import spock.lang.Specification

import java.time.Clock

class GameSpec extends Specification {
    void 'render() calls update and render correctly on the active game mode'() {
        setup:
        State mockState = Mock(State) {
            _ * getKey() >> 'key1'
        }
        StackedStateMachine gameModes = new StackedStateMachine([mockState] as Set<State>, mockState)

        when:
        Game game = new Game(gameModes)
        game.render()

        then:
        1 * mockState.update(_)
        1 * mockState.render()
    }

    void 'Game correctly measures time elapsed'() {
        setup:
        Clock mockClock = Mock(Clock) {
            2 * millis() >>> [1, 2]
        }
        State mockState = Mock(State) {
            _ * getKey() >> 'key1'
        }
        StackedStateMachine gameModes = new StackedStateMachine([mockState] as Set<State>, mockState)

        when:
        Game game = new Game(gameModes, mockClock)
        game.render()

        then:
        1 * mockState.update(1)
        1 * mockState.render()
    }
}
