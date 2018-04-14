package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.actor.ActionProvider;
import com.moonlightpixels.jrpg.legacy.map.actor.Action;

public final class PauseActorCommand extends AbstractActorCommand {
    private final long pauseTimeMs;
    private long pauseTimeRemainingMs;

    PauseActorCommand(final GameMap map, final String actorName, final long pauseTimeMs) {
        super(map, actorName);
        this.pauseTimeMs = pauseTimeMs;
    }

    @Override
    protected ActionProvider getActionProvider() {
        return new ActionProvider() {
            @Override
            public Action getNextAction() {
                return null;
            }

            @Override
            public boolean hasNextAction() {
                return !isComplete();
            }
        };
    }

    @Override
    protected void startCommand() {
        pauseTimeRemainingMs = pauseTimeMs;
    }

    @Override
    public boolean isComplete() {
        return pauseTimeRemainingMs <= 0L;
    }

    @Override
    public void update(final long elapsedTime) {
        pauseTimeRemainingMs -= elapsedTime;
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
    public String toString() {
        return String.format("PauseActorCommand{pauseTimeMs=%d (ms)}", pauseTimeMs);
    }
}
