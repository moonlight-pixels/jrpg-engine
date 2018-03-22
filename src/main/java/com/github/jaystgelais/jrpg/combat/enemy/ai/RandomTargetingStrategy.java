package com.github.jaystgelais.jrpg.combat.enemy.ai;

import com.badlogic.gdx.math.MathUtils;
import com.github.jaystgelais.jrpg.combat.Combatant;
import com.google.common.base.Preconditions;

import java.util.List;

public final class RandomTargetingStrategy implements TargetingStrategy {
    public static final RandomTargetingStrategy INSTANCE = new RandomTargetingStrategy();

    private RandomTargetingStrategy() { }

    @Override
    public Combatant chooseTarget(final List<Combatant> targets) {
        Preconditions.checkNotNull(targets);
        Preconditions.checkArgument(!targets.isEmpty());

        return targets.get(MathUtils.random(0, targets.size() - 1));
    }
}
