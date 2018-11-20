package com.moonlightpixels.jrpg.story

import com.moonlightpixels.jrpg.story.event.internal.EventHandler
import spock.lang.Specification

class ChapterSpec extends Specification {
    Chapter chapter
    Goal goal
    Task task

    void setup() {
        task = new SimpleTask(Mock(Task.Key))
        goal = new Goal(Mock(Goal.Key), 'hint', task)
        chapter = new Chapter(Mock(Chapter.Key), 'chapter', goal)
    }

    void 'isComplete() is true if goal is complete'() {
        when:
        task.complete = false

        then:
        !chapter.isComplete()

        when:
        task.complete = true

        then:
        chapter.isComplete()
    }

    void 'getCurrentGoals() returns startabel incomplete goals'() {
        setup:
        Goal dependencyA = new Goal(Mock(Goal.Key), 'A', new SimpleTask(Mock(Task.Key)))
        Goal dependencyB = new Goal(Mock(Goal.Key), 'B', new SimpleTask(Mock(Task.Key)))
        goal.dependsOn(dependencyA)
        goal.dependsOn(dependencyB)

        when:
        dependencyA.task.complete = false
        dependencyB.task.complete = true

        then:
        chapter.currentGoals == [ dependencyA ]
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
