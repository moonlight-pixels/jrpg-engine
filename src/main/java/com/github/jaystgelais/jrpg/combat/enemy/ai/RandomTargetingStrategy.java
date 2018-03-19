package com.github.jaystgelais.jrpg.combat.enemy.ai;

import com.github.jaystgelais.jrpg.combat.Combatant;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomTargetingStrategy implements TargetingStrategy {
    @Override
    public Combatant chooseTarget(final List<Combatant> targets) {
        Preconditions.checkNotNull(targets);
        Preconditions.checkArgument(!targets.isEmpty());

        return targets.get(ThreadLocalRandom.current().nextInt(0, targets.size()));
    }
}
