package com.moonlightpixels.jrpg.story;

import com.google.common.graph.Traverser;
import lombok.Data;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Data
public abstract class GoalContainer {
    private final Goal goal;

    /**
     * Returns a List of all Goals that can be started and are not yet complete.
     *
     * @return List of current Goals
     */
    public final List<Goal> getCurrentGoals() {
        return allGoals()
            .filter(Goal::canStart)
            .filter(((Predicate<Goal>) Goal::isComplete).negate())
            .collect(Collectors.toList());
    }

    final Stream<Goal> allGoals() {
        return StreamSupport.stream(
            Traverser.forGraph(Goal.SUCCESSORS_FUNCTION).breadthFirst(goal).spliterator(),
            false
        );
    }

    public interface Key { }
}
