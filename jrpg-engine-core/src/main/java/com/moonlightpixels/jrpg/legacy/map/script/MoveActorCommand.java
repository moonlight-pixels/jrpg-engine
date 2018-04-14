package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.map.actor.ActionProvider;
import com.moonlightpixels.jrpg.legacy.map.ai.MoveToGoal;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.TileCoordinate;
import com.moonlightpixels.jrpg.legacy.map.actor.Action;

public final class MoveActorCommand extends AbstractActorCommand {
    private final MoveToGoal goal;

    public MoveActorCommand(final GameMap map, final String actorName, final TileCoordinate destination) {
        super(map, actorName);
        goal = new MoveToGoal(destination);
    }

    @Override
    protected ActionProvider getActionProvider() {
        return new ActionProvider() {
            @Override
            public Action getNextAction() {
                final Action nextAction = goal.nextAction(getActor());
                return nextAction;
            }

            @Override
            public boolean hasNextAction() {
                return !isComplete();
            }
        };
    }

    @Override
    public boolean isComplete() {
        return goal.isComplete(getActor());
    }

    @Override
    public void update(final long elapsedTime) {
        goal.update(elapsedTime);
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
        return String.format("MoveActorCommand{destination=%s}", goal.getDestination());
    }
}
