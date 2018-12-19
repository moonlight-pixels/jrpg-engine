package com.moonlightpixels.jrpg.combat;

import lombok.Data;

import java.util.List;

@Data
public abstract class CombatAction {
    private final Key key;
    private final String name;
    private final CombatTargetType targetType;

    protected CombatAction(final Key key, final String name, final CombatTargetType targetType) {
        this.key = key;
        this.name = name;
        this.targetType = targetType;
    }

    protected abstract int getDelayInTicks(Combatant combatant);

    protected abstract CombatActionOutcome calculateOutcome(Combatant combatant, List<Combatant> targets);

    public interface Key { }
}
