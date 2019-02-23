package com.moonlightpixels.jrpg.combat.internal;

import com.moonlightpixels.jrpg.combat.CombatConfig;
import com.moonlightpixels.jrpg.combat.render.internal.CombatLayout;

public final class DefaultCombatConfig implements CombatConfig {
    private float timePerTick;
    private CombatLayout combatLayout;

    @Override
    public float getTimePerTick() {
        return timePerTick;
    }

    @Override
    public void setTimePerTick(final float timePerTick) {
        this.timePerTick = timePerTick;
    }

    @Override
    public CombatLayout getCombatLayout() {
        return combatLayout;
    }

    @Override
    public void setCombatLayout(final CombatLayout combatLayout) {
        this.combatLayout = combatLayout;
    }
}
