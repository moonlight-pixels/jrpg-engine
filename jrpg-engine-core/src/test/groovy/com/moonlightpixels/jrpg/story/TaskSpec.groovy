package com.moonlightpixels.jrpg.story

import com.moonlightpixels.jrpg.story.event.internal.EventHandler
import spock.lang.Specification

class TaskSpec extends Specification {

    Task taskA
    Task taskB
    Task taskC

    void setup() {
        taskA = new DummyTask(new SimpleTaskKey())
        taskB = new DummyTask(new SimpleTaskKey())
        taskC = new DummyTask(new SimpleTaskKey())
    }

    void 'task canStart only if ALL dependencies are complete'() {
        when:
        taskA.dependsOn(taskB)
        taskA.dependsOn(taskC)

        then:
        !taskA.canStart()

        when:
        taskB.complete = true

        then:
        !taskA.canStart()

        when:
        taskC.complete = true

        then:
        taskA.canStart()
    }

    void 'dependsOn() throws IllegalArgumentException if attempting to create a circular dependency'() {
        setup:
        taskA.dependsOn(taskB)
        taskB.dependsOn(taskC)

        when:
        taskC.dependsOn(taskA)

        then:
        thrown IllegalArgumentException
    }

    static class DummyTask extends Task {

        DummyTask(final Task.Key key) {
            super(key)
        }

        @Override
        protected EventHandler<?> getEventHandler() {
            return null
        }
    }

    class SimpleTaskKey implements Task.Key {
        @Override
        Goal.Key getGoalKey() {
            return null
        }
    }
}
