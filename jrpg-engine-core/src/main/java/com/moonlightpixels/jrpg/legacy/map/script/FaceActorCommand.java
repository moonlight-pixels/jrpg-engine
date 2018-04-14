package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.actor.ActionProvider;
import com.moonlightpixels.jrpg.legacy.map.actor.Direction;
import com.moonlightpixels.jrpg.legacy.map.actor.SingleActionProvider;
import com.moonlightpixels.jrpg.legacy.map.actor.FaceAction;

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
