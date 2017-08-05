package com.github.jaystgelais.jrpg.party;

import com.github.jaystgelais.jrpg.map.actor.SpriteSetDefinition;

import java.util.Objects;

public class Character {
    private final String name;
    private final SpriteSetDefinition spriteSetDefinition;
    private int maxHp;
    private int currentHp;
    private int maxMp;
    private int currentMp;
    private int level;
    private int xp;

    public Character(final String name, final SpriteSetDefinition spriteSetDefinition) {
        this.name = name;
        this.spriteSetDefinition = spriteSetDefinition;
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

    public final int getMaxHp() {
        return maxHp;
    }

    public final void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public final int getCurrentHp() {
        return currentHp;
    }

    public final void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public final int getMaxMp() {
        return maxMp;
    }

    public final void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public final int getCurrentMp() {
        return currentMp;
    }

    public final void setCurrentMp(int currentMp) {
        this.currentMp = currentMp;
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
}
