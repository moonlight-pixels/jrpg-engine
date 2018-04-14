package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.legacy.combat.action.CombatActionType;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.stats.StatHolder;

public interface Combatant extends StatHolder {
    String getName();
    void applyHpChange(final int hpChange);
    int getLevel();
    ActionTypeProvider getActionTypeProvider(Battle battle);
    TargetableChoiceProvider getTargetableChoiceProvider(CombatActionType actionType, Battle battle);
    TargetChoiceProvider getTargetChoiceProvider(AllowedTargets allowedTargets, Battle battle);
    boolean isAlive();
}
