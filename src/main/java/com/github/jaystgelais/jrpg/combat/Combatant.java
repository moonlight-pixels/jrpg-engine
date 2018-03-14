package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.action.CombatActionType;
import com.github.jaystgelais.jrpg.combat.stats.StatHolder;

import java.util.List;

public interface Combatant extends StatHolder {
    String getName();
    List<CombatActionType> getActionTypes();
    void applyHpChange(final int hpChange);
    int getLevel();
}
