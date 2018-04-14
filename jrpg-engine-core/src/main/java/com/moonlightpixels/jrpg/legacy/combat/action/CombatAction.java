package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.outcome.CombatOutcome;

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
