package com.github.jaystgelais.jrpg.party;

import com.github.jaystgelais.jrpg.combat.Combatant;
import com.github.jaystgelais.jrpg.combat.action.CombatActionType;
import com.github.jaystgelais.jrpg.combat.stats.MaxHP;
import com.github.jaystgelais.jrpg.combat.stats.MaxMP;
import com.github.jaystgelais.jrpg.combat.stats.MissingStatException;
import com.github.jaystgelais.jrpg.combat.stats.Stat;
import com.github.jaystgelais.jrpg.map.actor.ActorSpriteSet;
import com.github.jaystgelais.jrpg.animation.SpriteSetDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCharacter implements Combatant {
    private final String name;
    private final SpriteSetDefinition<ActorSpriteSet> spriteSetDefinition;
    private final CharacterClass characterClass;
    private int currentHp;
    private int currentMp;
    private final Map<Class<? extends Stat>, Stat> stats = new HashMap<>();
    private int level;
    private int xp;

    public PlayerCharacter(final String name, final SpriteSetDefinition<ActorSpriteSet> spriteSetDefinition,
                           final CharacterClass characterClass, final Stat... stats) {
        this.name = name;
        this.spriteSetDefinition = spriteSetDefinition;
        this.characterClass = characterClass;
        for (Stat stat : stats) {
            this.stats.put(stat.getClass(), stat);
        }
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final List<CombatActionType> getActionTypes() {
        return characterClass.getActionTypes();
    }

    public final SpriteSetDefinition<ActorSpriteSet> getSpriteSetDefinition() {
        return spriteSetDefinition;
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

    public final void setLevel(final int level) {
        this.level = level;
    }

    public final int getXp() {
        return xp;
    }

    public final void setXp(final int xp) {
        this.xp = xp;
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
