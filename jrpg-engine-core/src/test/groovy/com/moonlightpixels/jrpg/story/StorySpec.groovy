package com.moonlightpixels.jrpg.story

import com.moonlightpixels.jrpg.story.event.internal.EventHandler
import spock.lang.Specification

class StorySpec extends Specification {
    Story story
    Chapter chapter1
    Chapter.Key chapterKey1
    Goal goal1
    Task task1
    Task.Key taskKey1
    Chapter chapter2
    Chapter.Key chapterKey2
    Goal goal2
    Task task2
    Task.Key taskKey2

    void setup() {
        taskKey1 = new TaskKey()
        task1 = new SimpleTask(taskKey1)
        goal1 = new Goal(Mock(Goal.Key), 'hint 1', task1)
        chapterKey1 = new ChapterKey()
        chapter1 = new Chapter(chapterKey1, 'chapter 1', goal1)
        taskKey2 = new TaskKey()
        task2 = new SimpleTask(taskKey2)
        goal2 = new Goal(Mock(Goal.Key), 'hint 2', task2)
        chapterKey2 = new ChapterKey()
        chapter2 = new Chapter(chapterKey2, 'chapter 2', goal2)
        story = new Story()
        story.chapters.add(chapter1)
        story.chapters.add(chapter2)
    }

    void 'getCurrentChapter() return earliest non-completed chapter'() {
        when:
        task1.complete = false

        then:
        story.currentChapter == chapter1

        when:
        task1.complete = true

        then:
        story.currentChapter == chapter2
    }

    void 'getCurrentChapter() throws IllegalStateException if all chapter are complete'() {
        when:
        task1.complete = true
        task2.complete = true
        story.currentChapter

        then:
        thrown IllegalStateException
    }

    void 'getTask() retieves task by key'() {
        expect:
        story.getTask(taskKey1).get() == task1
    }

    void 'isValid() returns true when there are no duplicate keys'() {
        expect:
        story.isValid()
    }

    void 'isValid() returns false when there are duplicate chapter keys'() {
        setup:
        Task.Key taskKey3 = new TaskKey()
        Task task3 = new SimpleTask(taskKey3)
        Goal goal3 = new Goal(Mock(Goal.Key), 'hint 3', task3)
        Chapter chapter3 = new Chapter(chapterKey2, 'chapter 3', goal3)
        story.chapters.add(chapter3)

        expect:
        !story.isValid()
    }

    void 'isValid() returns false when there are duplicate goal keys'() {
        setup:
        Task.Key taskKey3 = new TaskKey()
        Task task3 = new SimpleTask(taskKey3)
        Goal goal3 = new Goal(goal2.key, 'hint 3', task3)
        Chapter.Key chapterKey3 = new ChapterKey()
        Chapter chapter3 = new Chapter(chapterKey3, 'chapter 3', goal3)
        story.chapters.add(chapter3)

        expect:
        !story.isValid()
    }

    void 'isValid() returns false when there are duplicate task keys'() {
        setup:
        Task task3 = new SimpleTask(taskKey2)
        Goal goal3 = new Goal(Mock(Goal.Key), 'hint 3', task3)
        Chapter.Key chapterKey3 = new ChapterKey()
        Chapter chapter3 = new Chapter(chapterKey3, 'chapter 3', goal3)
        story.chapters.add(chapter3)

        expect:
        !story.isValid()
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

    @SuppressWarnings('EmptyClass')
    static class ChapterKey implements Chapter.Key { }

    static class TaskKey implements Task.Key {
        @Override
        Goal.Key getGoalKey() {
            return null
        }
    }
}
