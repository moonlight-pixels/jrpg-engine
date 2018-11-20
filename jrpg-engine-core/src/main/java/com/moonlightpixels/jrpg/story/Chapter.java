package com.moonlightpixels.jrpg.story;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class Chapter extends GoalContainer {
    private final Key key;
    private final String title;

    /**
     * Creates a new Chapter.
     *
     * @param key Key to uniquely identify this chapter.
     * @param title Title of chapter, suitable for use in UI
     * @param goal Goal, whose completion completes this chapter
     */
    public Chapter(final Key key, final String title, final Goal goal) {
        super(goal);
        this.key = key;
        this.title = title;
    }

    /**
     * Whether of not this Chapter has been completed.
     *
     * @return true if complete
     */
    public boolean isComplete() {
        return getGoal().isComplete();
    }

    public interface Key extends GoalContainer.Key { }
}
