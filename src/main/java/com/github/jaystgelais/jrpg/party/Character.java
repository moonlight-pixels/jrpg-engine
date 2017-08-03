package com.github.jaystgelais.jrpg.party;

import com.github.jaystgelais.jrpg.map.actor.SpriteSetDefinition;

import java.util.Objects;

public final class Character {
    private final String name;
    private final SpriteSetDefinition spriteSetDefinition;

    public Character(final String name, final SpriteSetDefinition spriteSetDefinition) {
        this.name = name;
        this.spriteSetDefinition = spriteSetDefinition;
    }

    public String getName() {
        return name;
    }

    public SpriteSetDefinition getSpriteSetDefinition() {
        return spriteSetDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(name, character.name) &&
                Objects.equals(spriteSetDefinition, character.spriteSetDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, spriteSetDefinition);
    }
}
