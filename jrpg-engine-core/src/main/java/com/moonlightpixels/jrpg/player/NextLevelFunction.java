package com.moonlightpixels.jrpg.player;

@FunctionalInterface
public interface NextLevelFunction {
    /**
     * Gets the total experience needed to reach a given level.
     *
     * @param level Level.
     * @return Experience needed to reach level.
     */
    int getExperienceNeededForLevel(int level);
}
