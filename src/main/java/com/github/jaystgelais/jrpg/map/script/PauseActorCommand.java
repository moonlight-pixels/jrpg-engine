package com.github.jaystgelais.jrpg.map.script;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.actor.Action;
import com.github.jaystgelais.jrpg.map.actor.ActionProvider;

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
