package com.moonlightpixels.jrpg.story;

import lombok.Data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Data
public final class Story {
    private final List<Chapter> chapters = new LinkedList<>();

    /**
     * Get the current active chapter of the story.
     *
     * @return current chapter
     */
    public Chapter getCurrentChapter() {
        return chapters.stream()
            .filter(((Predicate<Chapter>) Chapter::isComplete).negate())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Story contains no incomplete chapters"));
    }

    /**
     * Stream all story tasks.
     *
     * @return A Stream of all story tasks
     */
    public Stream<Task> allTasks() {
        return allGoals().flatMap(Goal::allTasks);
    }

    /**
     * Stream all story goals.
     *
     * @return A Stream of all story goals
     */
    public Stream<Goal> allGoals() {
        return chapters.stream().flatMap(GoalContainer::allGoals);
    }

    /**
     * Returns a Task based on its Key if it is present in the Story.
     *
     * @param key Key of Task to find
     * @return Optional of Task
     */
    public Optional<Task> getTask(final Task.Key key) {
        return allTasks()
            .filter(task -> task.getKey().equals(key))
            .findFirst();
    }

    /**
     * Validates the composition of this story. Validations include:
     * <ul>
     *     <li>No repeated Chapter IDs</li>
     *     <li>No repeated Goal IDs</li>
     *     <li>No repeated Task IDs</li>
     * </ul>
     *
     * @return true if valid
     */
    public boolean isValid() {
        return chapters.stream().map(Chapter::getKey).allMatch(new HashSet<>()::add)
            && allGoals().map(Goal::getKey).allMatch(new HashSet<>()::add)
            && allTasks().map(Task::getKey).allMatch(new HashSet<>()::add);
    }
}
