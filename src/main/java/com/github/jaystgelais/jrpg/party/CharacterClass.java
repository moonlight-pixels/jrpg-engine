package com.github.jaystgelais.jrpg.party;

public class CharacterClass {
    private final String name;
    private final NextLevelFunction nextLevelFunction;

    public CharacterClass(final String name, final NextLevelFunction nextLevelFunction) {
        this.name = name;
        this.nextLevelFunction = nextLevelFunction;
    }

    public CharacterClass(final String name) {
        this(name, new DefaultNextLevelFunction());
    }

    public final String getName() {
        return name;
    }

    public final int getXPNeededForNextLevel(final int currentLevel) {
        return nextLevelFunction.getNextLevel(currentLevel);
    }

    public final int getXPNeededForCurrentLevel(final int currentLevel) {
        return nextLevelFunction.getNextLevel(currentLevel - 1);
    }
}
