package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.combat.Encounter;
import com.moonlightpixels.jrpg.graphics.GraphicsService;
import com.moonlightpixels.jrpg.input.InputService;
import com.moonlightpixels.jrpg.map.MapMode;

public final class EncounterTriggerAction implements TriggerAction {
    private final Encounter encounter;
    private boolean complete = false;

    public EncounterTriggerAction(final Encounter encounter) {
        this.encounter = encounter;
    }

    @Override
    public void startAction(final MapMode mapMode) {
        complete = true;
        mapMode.startEncounter(encounter);
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public void render(final GraphicsService graphicsService) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput(final InputService inputService) {

    }

    @Override
    public void update(final long elapsedTime) {

    }
}
