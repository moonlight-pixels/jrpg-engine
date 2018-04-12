package com.moonlightpixels.jrpg.combat.action;

import com.moonlightpixels.jrpg.combat.Combatant;

import java.util.List;

public class TargetChoiceProvider {
    private List<? extends Combatant> targets;

    public final boolean isComplete() {
        return targets != null;
    }

    public final List<? extends Combatant> getTargets() {
        return targets;
    }

    public final void setTargets(final List<? extends Combatant> targets) {
        this.targets = targets;
    }
}
