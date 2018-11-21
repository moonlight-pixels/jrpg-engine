package com.moonlightpixels.jrpg.story;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Preconditions;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@ToString
public final class Goal {
    @Getter
    private final Key<? extends GoalContainer.Key> key;
    @Getter
    private final String hint;
    @Getter
    private final Task task;
    @Getter(AccessLevel.PACKAGE)
    private final Set<Goal> dependencies = new HashSet<>();

    /**
     * Whether of not this Goal has been completed.
     *
     * @return true if complete
     */
    public boolean isComplete() {
        return task.isComplete();
    }

    /**
     * Add a dependency on another goal.
     *
     * @param goal Goal this goal depends on
     */
    public void dependsOn(final Goal goal) {
        Preconditions.checkArgument(
            doesNotIntroduceCircularDependency(goal),
            "Adding this dependency causes a circular dependcy"
        );
        dependencies.add(goal);
    }

    /**
     * Rerurns true if all dependencies of this goal have been completed.
     *
     * @return true if all dependecies are complete
     */
    public boolean canStart() {
        return dependencies.stream().allMatch(Goal::isComplete);
    }

    Stream<Task> allTasks() {
        return StreamSupport.stream(
            Traverser.forGraph(Task.SUCCESSORS_FUNCTION).breadthFirst(task).spliterator(),
            false
        );
    }

    private boolean doesNotIntroduceCircularDependency(final Goal newGoal) {
        for (Goal goal : Traverser.forGraph(SUCCESSORS_FUNCTION).breadthFirst(newGoal)) {
            if (goal == this) {
                return false;
            }
        }

        return true;
    }

    static final SuccessorsFunction<Goal> SUCCESSORS_FUNCTION = new SuccessorsFunction<Goal>() {
        @Override
        public Iterable<? extends Goal> successors(@NonNull final Goal goal) {
            Preconditions.checkNotNull(goal);
            return goal.getDependencies();
        }
    };

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public interface Key<C extends GoalContainer.Key> {
        /**
         * Key identifying the container this goal belongs to.
         *
         * @return Parent GoalContainer's key
         */
        C getContainerKey();
    }
}
