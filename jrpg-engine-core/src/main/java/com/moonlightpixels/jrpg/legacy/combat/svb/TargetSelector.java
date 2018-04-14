package com.moonlightpixels.jrpg.legacy.combat.svb;

import com.badlogic.gdx.graphics.Texture;
import com.moonlightpixels.jrpg.legacy.Game;
import com.moonlightpixels.jrpg.legacy.combat.Combatant;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.input.DelayedInput;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.input.Inputs;
import com.moonlightpixels.jrpg.legacy.ui.UserInputHandler;

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
