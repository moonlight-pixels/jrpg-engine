package com.github.jaystgelais.jrpg.map.script;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.actor.Action;
import com.github.jaystgelais.jrpg.map.actor.ActionProvider;
import com.github.jaystgelais.jrpg.map.ai.MoveToGoal;

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