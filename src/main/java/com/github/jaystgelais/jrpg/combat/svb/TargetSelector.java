package com.github.jaystgelais.jrpg.combat.svb;

import com.badlogic.gdx.graphics.Texture;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.combat.Combatant;
import com.github.jaystgelais.jrpg.combat.action.TargetChoiceProvider;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.ui.UserInputHandler;

import java.util.Collections;
import java.util.List;

public final class TargetSelector implements UserInputHandler {
    private final TargetChoiceProvider provider;
    private final SideViewBattleSystem battleSystem;
    private final List<? extends Combatant> targets;
    private final Texture cursor;
    private final DelayedInput okInput = new DelayedInput(Inputs.OK);
    private final DelayedInput downInput = new DelayedInput(Inputs.DOWN);
    private final DelayedInput leftInput = new DelayedInput(Inputs.LEFT);
    private final DelayedInput rightInput = new DelayedInput(Inputs.RIGHT);
    private final DelayedInput upInput = new DelayedInput(Inputs.UP);
    private int currentIndex = 0;

    public TargetSelector(final TargetChoiceProvider provider, final SideViewBattleSystem battleSystem,
                          final List<? extends Combatant> targets) {
        this.provider = provider;
        this.battleSystem = battleSystem;
        this.targets = targets;
        cursor = loadCursor();
        battleSystem
                .getSelectableActor(targets.get(0))
                .ifPresent(selectableActor -> selectableActor.showCursor(cursor));
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (okInput.isPressed(inputService)) {
            provider.setTargets(Collections.singletonList(targets.get(currentIndex)));
        } else if (rightInput.isPressed(inputService)) {
            updateCurrentIndex(1);
        } else if (downInput.isPressed(inputService)) {
            updateCurrentIndex(1);
        } else if (leftInput.isPressed(inputService)) {
            updateCurrentIndex(-1);
        } else if (upInput.isPressed(inputService)) {
            updateCurrentIndex(-1);
        }
    }

    @Override
    public boolean isComplete() {
        return provider.isComplete();
    }

    private void updateCurrentIndex(final int delta) {
        battleSystem.getSelectableActor(targets.get(currentIndex)).ifPresent(SelectableActor::hideCursor);
        currentIndex = (currentIndex + delta) % targets.size();
        battleSystem
                .getSelectableActor(targets.get(currentIndex))
                .ifPresent(selectableActor -> selectableActor.showCursor(cursor));
    }

    private Texture loadCursor() {
        final GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        if (!graphicsService.getAssetManager().isLoaded("assets/jrpg/panel/cursor.png", Texture.class)) {
            graphicsService.getAssetManager().load("assets/jrpg/panel/cursor.png", Texture.class);
            graphicsService.getAssetManager().finishLoading();
        }

        return graphicsService.getAssetManager().get("assets/jrpg/panel/cursor.png", Texture.class);
    }
}
