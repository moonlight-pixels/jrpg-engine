package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.outcome.CombatOutcome;

import java.util.LinkedList;
import java.util.List;

public abstract class CombatAction {
    private final List<ActionRenderer> renderers = new LinkedList<>();

    public final void start(final Battle battle) {
        perform(battle).forEach(outcome -> {
            renderers.forEach(renderer -> renderer.registerOutcome(outcome));
        });
        renderers.forEach(renderer -> renderer.start(battle));
    };

    public abstract List<CombatOutcome> perform(final Battle battle);

    public final boolean isComplete() {
        return renderers.stream().allMatch(ActionRenderer::isComplete);
    }
}
