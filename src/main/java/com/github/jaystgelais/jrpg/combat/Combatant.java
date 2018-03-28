package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.action.ActionTypeProvider;
import com.github.jaystgelais.jrpg.combat.action.AllowedTargets;
import com.github.jaystgelais.jrpg.combat.action.CombatActionType;
import com.github.jaystgelais.jrpg.combat.action.TargetChoiceProvider;
import com.github.jaystgelais.jrpg.combat.action.TargetableChoiceProvider;
import com.github.jaystgelais.jrpg.combat.stats.StatHolder;

public interface Combatant extends StatHolder {
    String getName();
    void applyHpChange(final int hpChange);
    int getLevel();
    ActionTypeProvider getActionTypeProvider(Battle battle);
    TargetableChoiceProvider getTargetableChoiceProvider(CombatActionType actionType, Battle battle);
    TargetChoiceProvider getTargetChoiceProvider(AllowedTargets allowedTargets, Battle battle);
    boolean isAlive();
}
