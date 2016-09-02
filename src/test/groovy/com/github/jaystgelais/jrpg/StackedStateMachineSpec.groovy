package com.github.jaystgelais.jrpg

import spock.lang.Specification

class StackedStateMachineSpec extends Specification {
    void 'exiting the current state throws an exception if the stack is empty'() {
        setup:
        State state1 = Mock(State) {
            _ * getKey() >> 'key1'
        }
        State state2 = Mock(State) {
            _ * getKey() >> 'key2'
        }
        StackedStateMachine stateMachine = new StackedStateMachine([state1, state2] as Set<State>, state1)

        when:
        stateMachine.exitCurrentState()

        then:
        thrown IllegalStateException
    }

    void 'exiting the current state returns to the previous'() {
        setup:
        State state1 = Mock(State) {
            _ * getKey() >> 'key1'
        }
        State state2 = Mock(State) {
            _ * getKey() >> 'key2'
        }
        StackedStateMachine stateMachine = new StackedStateMachine([state1, state2] as Set<State>, state1)

        when:
        stateMachine.change('key2')

        then:
        stateMachine.getCurrentState() == state2

        when:
        stateMachine.exitCurrentState()

        then:
        stateMachine.getCurrentState() == state1
    }
}
