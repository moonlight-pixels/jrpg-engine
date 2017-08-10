package com.github.jaystgelais.jrpg.party;

import com.github.jaystgelais.jrpg.combat.stats.*;
import com.github.jaystgelais.jrpg.map.actor.SpriteSetDefinition;

import java.util.*;

public class Character implements StatHolder {
    private final String name;
    private final SpriteSetDefinition spriteSetDefinition;
    private final CharacterClass characterClass;
    private int currentHp;
    private int currentMp;
    private final Map<Class<? extends Stat>, Stat> stats = new HashMap<>();
    private int level;
    private int xp;

    public Character(final String name, final SpriteSetDefinition spriteSetDefinition,
                     final CharacterClass characterClass, final Collection<Stat> stats) {
        this.name = name;
        this.spriteSetDefinition = spriteSetDefinition;
        this.characterClass = characterClass;
        for (Stat stat : stats) {
            this.stats.put(stat.getClass(), stat);
        }
    }

    public final String getName() {
        return name;
    }

    public final SpriteSetDefinition getSpriteSetDefinition() {
        return spriteSetDefinition;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Character character = (Character) o;
        return Objects.equals(name, character.name)
                && Objects.equals(spriteSetDefinition, character.spriteSetDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, spriteSetDefinition);
    }

    public final int getCurrentHp() {
        return Math.min(currentHp, getStatValue(MaxHP.class));
    }

    public final void setCurrentHp(int currentHp) {
        this.currentHp = Math.min(currentHp, getStatValue(MaxHP.class));
    }

    public final int getCurrentMp() {
        return Math.min(currentMp, getStatValue(MaxMP.class));
    }

    public final void setCurrentMp(int currentMp) {
        this.currentMp = Math.min(currentMp, getStatValue(MaxMP.class));
    }

    public final int getLevel() {
        return level;
    }

    public final void setLevel(int level) {
        this.level = level;
    }

    public final int getXp() {
        return xp;
    }

    public final void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public <T extends Stat> Stat getStat(Class<T> statClass) throws MissingStatException {
        if (!stats.containsKey(statClass)) {
            throw new MissingStatException(statClass);
        }

        return stats.get(statClass);
    }

    public final CharacterClass getCharacterClass() {
        return characterClass;
    }
}
