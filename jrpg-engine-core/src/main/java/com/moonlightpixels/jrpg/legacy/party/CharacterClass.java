package com.moonlightpixels.jrpg.legacy.party;

import com.moonlightpixels.jrpg.legacy.combat.action.CombatActionType;

import java.util.List;

public class CharacterClass {
    private final String name;
    private final List<CombatActionType> actionTypes;
    private final NextLevelFunction nextLevelFunction;

    public CharacterClass(final String name, final List<CombatActionType> actionTypes,
                          final NextLevelFunction nextLevelFunction) {
        this.name = name;
        this.actionTypes = actionTypes;
        this.nextLevelFunction = nextLevelFunction;
    }

    public final String getName() {
        return name;
    }

    public final List<CombatActionType> getActionTypes() {
        return actionTypes;
    }

    public final int getXPNeededForNextLevel(final int currentLevel) {
        return nextLevelFunction.getNextLevel(currentLevel);
    }

    public final int getXPNeededForCurrentLevel(final int currentLevel) {
        return nextLevelFunction.getNextLevel(currentLevel - 1);
    }
}
