package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatMeter;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;
import com.moonlightpixels.jrpg.player.PlayerCharacter;

import javax.inject.Inject;

public final class PlayerCombatant implements Combatant {
    private final PlayerCharacter playerCharacter;
    private final CombatTurnMeter combatTurnMeter;

    @Inject
    PlayerCombatant(final StatSystem statSystem,
                    final PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
        this.combatTurnMeter = new CombatTurnMeter(statSystem, playerCharacter);
    }

    @Override
    public StatMeter getHitPoints() {
        return playerCharacter.getHitPoints();
    }

    @Override
    public StatMeter getMeter(final Stat.Key maxValueKey) {
        return playerCharacter.getMeter(maxValueKey);
    }

    @Override
    public int getStatValue(final Stat.Key key) {
        return playerCharacter.getStatValue(key);
    }

    @Override
    public DecisionHandler getDecisionHandler() {
        return null;
    }

    @Override
    public CombatTurnMeter getCombatTurnMeter() {
        return combatTurnMeter;
    }
}
