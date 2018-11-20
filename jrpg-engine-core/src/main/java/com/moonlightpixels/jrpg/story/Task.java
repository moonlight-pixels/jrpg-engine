package com.moonlightpixels.jrpg.story;

import com.google.common.base.Preconditions;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import com.moonlightpixels.jrpg.story.event.internal.EventHandler;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@ToString
public abstract class Task {
    @Getter
    private final Key key;
    @Getter(AccessLevel.PACKAGE)
    private final Set<Task> dependencies = new HashSet<>();
    @Getter
    @Setter
    private boolean complete = false;

    /**
     * Add a dependency on another task.
     *
     * @param task Task this task depends on
     */
    public void dependsOn(final Task task) {
        Preconditions.checkArgument(
            doesNotIntroduceCircularDependency(task),
            "Adding this dependency causes a circular dependcy"
        );
        dependencies.add(task);
    }

    /**
     * Rerurns true if all dependencies of this task have been completed.
     *
     * @return true if all dependecies are complete
     */
    public boolean canStart() {
        return dependencies.stream().allMatch(Task::isComplete);
    }

    protected abstract EventHandler<?> getEventHandler();

    private boolean doesNotIntroduceCircularDependency(final Task newTask) {
        for (Task task : Traverser.forGraph(SUCCESSORS_FUNCTION).breadthFirst(newTask)) {
            if (task == this) {
                return false;
            }
        }

        return true;
    }

    static final SuccessorsFunction<Task> SUCCESSORS_FUNCTION = new SuccessorsFunction<Task>() {
        @Override
        public Iterable<? extends Task> successors(@NonNull final Task task) {
            Preconditions.checkNotNull(true);
            return task.getDependencies();
        }
    };

    public interface Key {
        /**
         * Key identifying the goal this task belongs to.
         *
         * @return Parent Goal's key
         */
        Goal.Key getGoalKey();
    }
}
