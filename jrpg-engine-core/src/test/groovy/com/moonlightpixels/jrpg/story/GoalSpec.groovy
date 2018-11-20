package com.moonlightpixels.jrpg.story

import com.moonlightpixels.jrpg.story.event.internal.EventHandler
import spock.lang.Specification

import java.util.stream.Collectors

class GoalSpec extends Specification {
    Goal goalA
    Goal goalB
    Goal goalC

    void setup() {
        goalA = new Goal(new SimpleGoalKey(), 'hint A', new SimpleTask(Mock(Task.Key)))
        goalB = new Goal(new SimpleGoalKey(), 'hint B', new SimpleTask(Mock(Task.Key)))
        goalC = new Goal(new SimpleGoalKey(), 'hint C', new SimpleTask(Mock(Task.Key)))
    }

    void 'isComplete() is true if task is complete'() {
        when:
        goalA.task.complete = true
        goalB.task.complete = false

        then:
        goalA.complete
        !goalB.complete
    }

    void 'goal canStart only if ALL dependencies are complete'() {
        when:
        goalA.dependsOn(goalB)
        goalA.dependsOn(goalC)

        then:
        !goalA.canStart()

        when:
        goalB.task.complete = true

        then:
        !goalA.canStart()

        when:
        goalC.task.complete = true

        then:
        goalA.canStart()
    }

    void 'dependsOn() throws IllegalArgumentException if attempting to create a circular dependency'() {
        setup:
        goalA.dependsOn(goalB)
        goalB.dependsOn(goalC)

        when:
        goalC.dependsOn(goalA)

        then:
        thrown IllegalArgumentException
    }

    void 'allTasks() returns all tasks'() {
        setup:
        Task taskA = new SimpleTask(Mock(Task.Key))
        Task taskB = new SimpleTask(Mock(Task.Key))
        Task taskC = new SimpleTask(Mock(Task.Key))
        Task taskD = new SimpleTask(Mock(Task.Key))
        taskA.dependsOn(taskB)
        taskA.dependsOn(taskC)
        taskB.dependsOn(taskD)
        Goal goal = new Goal(new SimpleGoalKey(), 'hint', taskA)

        when:
        List<Task> tasks = goal.allTasks().collect(Collectors.toList())

        then:
        tasks.size() == 4
        tasks.contains(taskA)
        tasks.contains(taskB)
        tasks.contains(taskC)
        tasks.contains(taskD)
    }

    static class SimpleGoalKey implements Goal.Key<Chapter.Key> {
        @Override
        Chapter.Key getContainerKey() {
            return null
        }
    }

    static class SimpleTask extends Task {

        SimpleTask(final Task.Key key) {
            super(key)
        }

        @Override
        protected EventHandler<?> getEventHandler() {
            return null
        }
    }
}
