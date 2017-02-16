package com.github.jaystgelais.jrpg.state

import com.github.jaystgelais.jrpg.graphics.GraphicsService
import spock.lang.Specification

class StateMachineSpec extends Specification {
    void 'Constructor throws exception if states is null'() {
        when:
        new StateMachine(null, null)

        then:
        thrown IllegalArgumentException
    }

    void 'Constructor throws exception if states is empty'() {
        when:
        new StateMachine([] as Set<State>, null)

        then:
        thrown IllegalArgumentException
    }

    void 'Constructor throws exception if initialState is null'() {
        setup:
        State state = Mock(State)

        when:
        new StateMachine([state] as Set<State>, null)

        then:
        thrown IllegalArgumentException
    }

    void 'calls to update and render propegate to the current state'() {
        setup:
        State state = Mock(State)
        StateMachine stateMachine = new StateMachine([state] as Set<State>, state)
        GraphicsService graphicsService = Mock(GraphicsService)

        when:
        stateMachine.update(100)
        stateMachine.render(graphicsService)

        then:
        _ * state.getKey() >> 'somekey'
        1 * state.update(100)
        1 * state.render(graphicsService)
    }

    void 'change throws exception if stateKey is not recognized'() {
        setup:
        State state = Mock(State) {
            _ * getKey() >> 'key'
        }
        StateMachine stateMachine = new StateMachine([state] as Set<State>, state)

        when:
        stateMachine.change('notAKey')

        then:
        thrown IllegalArgumentException
    }

    void 'change throws exception if params is null'() {
        setup:
        State state = Mock(State) {
            _ * getKey() >> 'key'
        }
        StateMachine stateMachine = new StateMachine([state] as Set<State>, state)

        when:
        stateMachine.change('key', null)

        then:
        thrown IllegalArgumentException
    }

    void 'change calls onExit and on Enter correctly on states'() {
        setup:
        State state1 = Mock(State) {
            _ * getKey() >> 'key1'
        }
        State state2 = Mock(State) {
            _ * getKey() >> 'key2'
        }
        StateMachine stateMachine = new StateMachine([state1, state2] as Set<State>, state1)
        Map<String, Object> params = ['param': 'value']

        when:
        stateMachine.change('key2', params)

        then:
        1 * state1.onExit()
        1 * state2.onEnter(params)
    }

    void 'child classes can access and mutate the currentState'() {
        setup:
        State state1 = Mock(State) {
            _ * getKey() >> 'key1'
        }
        State state2 = Mock(State) {
            _ * getKey() >> 'key2'
        }
        StateMachine stateMachine = new StateMachine([state1, state2] as Set<State>, state1)

        when:
        stateMachine.setCurrentState(state2)

        then:
        stateMachine.currentState == state2
    }

    void 'change properly logs transitions for child classes'() {
        setup:
        State state1 = Mock(State) {
            _ * getKey() >> 'key1'
        }
        State state2 = Mock(State) {
            _ * getKey() >> 'key2'
        }
        StateMachine stateMachine = Spy(StateMachine, constructorArgs: [[state1, state2] as Set<State>, state1])

        when:
        stateMachine.change('key2')

        then:
        1 * stateMachine.onChange(state1, state2)
        _ * state1.onExit()
        _ * state2.onEnter(_)
    }
}
