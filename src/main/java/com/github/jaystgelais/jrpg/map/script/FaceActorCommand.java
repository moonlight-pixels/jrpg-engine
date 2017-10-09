package com.github.jaystgelais.jrpg.map.script;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.actor.ActionProvider;
import com.github.jaystgelais.jrpg.map.actor.Direction;
import com.github.jaystgelais.jrpg.map.actor.FaceAction;
import com.github.jaystgelais.jrpg.map.actor.SingleActionProvider;

public final class FaceActorCommand extends AbstractActorCommand {
    private final Direction direction;
    private SingleActionProvider actionProvider;

    public FaceActorCommand(final GameMap map, final String actorName, final Direction direction) {
        super(map, actorName);
        this.direction = direction;
        actionProvider = new SingleActionProvider(new FaceAction(direction));
    }

    @Override
    protected ActionProvider getActionProvider() {
        return actionProvider;
    }

    @Override
    public boolean isComplete() {
        return !actionProvider.hasNextAction();
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
