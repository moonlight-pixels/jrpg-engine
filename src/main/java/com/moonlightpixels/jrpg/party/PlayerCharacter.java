package com.moonlightpixels.jrpg.party;

import com.moonlightpixels.jrpg.combat.Battle;
import com.moonlightpixels.jrpg.combat.BattleSpriteSet;
import com.moonlightpixels.jrpg.combat.Combatant;
import com.moonlightpixels.jrpg.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.combat.action.CombatActionType;
import com.moonlightpixels.jrpg.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.combat.stats.MaxHP;
import com.moonlightpixels.jrpg.combat.stats.MaxMP;
import com.moonlightpixels.jrpg.combat.stats.MissingStatException;
import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.map.actor.ActorSpriteSet;
import com.moonlightpixels.jrpg.animation.SpriteSetDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCharacter implements Combatant {
    private final String name;
    private final SpriteSetDefinition<ActorSpriteSet> spriteSetDefinition;
    private final SpriteSetDefinition<? extends BattleSpriteSet> battlerSpriteSetDefinition;
    private final CharacterClass characterClass;
    private int currentHp;
    private int currentMp;
    private final Map<Class<? extends Stat>, Stat> stats = new HashMap<>();
    private int level;
    private int xp;
    private boolean backrow = false;

    public PlayerCharacter(final String name, final SpriteSetDefinition<ActorSpriteSet> spriteSetDefinition,
                           final SpriteSetDefinition<? extends BattleSpriteSet> battlerSpriteSetDefinition,
                           final CharacterClass characterClass, final Stat... stats) {
        this.name = name;
        this.spriteSetDefinition = spriteSetDefinition;
        this.battlerSpriteSetDefinition = battlerSpriteSetDefinition;
        this.characterClass = characterClass;
        for (Stat stat : stats) {
            this.stats.put(stat.getClass(), stat);
        }
    }

    @Override
    public final String getName() {
        return name;
    }

    public final List<CombatActionType> getActionTypes() {
        return characterClass.getActionTypes();
    }

    public final SpriteSetDefinition<ActorSpriteSet> getSpriteSetDefinition() {
        return spriteSetDefinition;
    }

    public final SpriteSetDefinition<? extends BattleSpriteSet> getBattlerSpriteSetDefinition() {
        return battlerSpriteSetDefinition;
    }

    public final int getCurrentHp() {
        return Math.min(currentHp, getStatValue(MaxHP.class));
    }

    public final void setCurrentHp(final int currentHp) {
        this.currentHp = Math.min(currentHp, getStatValue(MaxHP.class));
    }

    public final int getCurrentMp() {
        return Math.min(currentMp, getStatValue(MaxMP.class));
    }

    public final void setCurrentMp(final int currentMp) {
        this.currentMp = Math.min(currentMp, getStatValue(MaxMP.class));
    }

    @Override
    public final int getLevel() {
        return level;
    }

    @Override
    public final ActionTypeProvider getActionTypeProvider(final Battle battle) {
        final ActionTypeProvider provider = new ActionTypeProvider();
        battle.handlePlayerInput(this, provider);

        return provider;
    }

    @Override
    public final TargetableChoiceProvider getTargetableChoiceProvider(final CombatActionType actionType,
                                                                final Battle battle) {
        TargetableChoiceProvider provider = actionType.getTargetableChoiceProvider();
        if (!provider.isComplete()) {
            battle.handlePlayerInput(this, provider);
        }

        return provider;
    }

    @Override
    public final TargetChoiceProvider getTargetChoiceProvider(final AllowedTargets allowedTargets,
                                                              final Battle battle) {
        TargetChoiceProvider provider = new TargetChoiceProvider();
        battle.handlePlayerInput(this, provider, allowedTargets);

        return provider;
    }

    @Override
    public final boolean isAlive() {
        return currentHp > 0;
    }

    public final void setLevel(final int level) {
        this.level = level;
    }

    public final int getXp() {
        return xp;
    }

    public final void setXp(final int xp) {
        this.xp = xp;
    }

    public final boolean isBackrow() {
        return backrow;
    }

    public final void setBackrow(final boolean backrow) {
        this.backrow = backrow;
    }

    @Override
    public final <T extends Stat> Stat getStat(final Class<T> statClass) throws MissingStatException {
        if (!stats.containsKey(statClass)) {
            throw new MissingStatException(statClass);
        }

        return stats.get(statClass);
    }

    public final CharacterClass getCharacterClass() {
        return characterClass;
    }

    @Override
    public final void applyHpChange(final int hpChange) {
        setCurrentHp(Math.max(getCurrentHp() + hpChange, 0));
    }
}
