package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.stats.RequiredStats;
import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatAddition;
import com.moonlightpixels.jrpg.combat.stats.StatHolder;
import com.moonlightpixels.jrpg.combat.stats.StatMeter;
import com.moonlightpixels.jrpg.combat.stats.StatMultiplier;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class EnemyCombatant implements Combatant, StatHolder {
    private final StatSystem statSystem;
    private final Enemy enemy;
    private final CombatTurnMeter combatTurnMeter;
    private final Map<Stat.Key, StatMeter> meters = new HashMap<>();

    EnemyCombatant(final StatSystem statSystem, final Enemy enemy) {
        this.statSystem = statSystem;
        this.enemy = enemy;
        this.combatTurnMeter = new CombatTurnMeter(statSystem, this);
    }

    @Override
    public StatMeter getHitPoints() {
        return getMeter(RequiredStats.MaxHP);
    }

    @Override
    public StatMeter getMeter(final Stat.Key maxValueKey) {
        return meters.computeIfAbsent(maxValueKey, key -> new StatMeter(this, statSystem.getStat(key)));
    }

    @Override
    public int getStatValue(final Stat.Key key) {
        return statSystem.getStat(key).getValue(this);
    }

    @Override
    public DecisionHandler getDecisionHandler() {
        return battle -> Optional.of(enemy.getCombatAI().getNextAction(battle));
    }

    @Override
    public CombatTurnMeter getCombatTurnMeter() {
        return combatTurnMeter;
    }

    @Override
    public int getBaseValue(final Stat.Key key) {
        return enemy.getStatValues().get(key);
    }

    @Override
    public Type getHolderType() {
        return Type.Enemy;
    }

    @Override
    public List<StatAddition> getStatAdditions(final Stat.Key stat) {
        return Collections.emptyList();
    }

    @Override
    public List<StatMultiplier> getStatMultipliers(final Stat.Key stat) {
        return Collections.emptyList();
    }
}
