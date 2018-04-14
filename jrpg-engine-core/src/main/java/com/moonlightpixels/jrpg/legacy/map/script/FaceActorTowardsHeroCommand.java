package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.actor.ActionProvider;
import com.moonlightpixels.jrpg.legacy.map.actor.Actor;
import com.moonlightpixels.jrpg.legacy.map.actor.Direction;
import com.moonlightpixels.jrpg.legacy.map.actor.SingleActionProvider;
import com.moonlightpixels.jrpg.legacy.map.TileCoordinate;
import com.moonlightpixels.jrpg.legacy.map.actor.FaceAction;

public final class FaceActorTowardsHeroCommand extends AbstractActorCommand {
    private final Actor hero;
    private boolean complete = false;

    FaceActorTowardsHeroCommand(final GameMap map, final String actorName) {
        super(map, actorName);
        hero = map.getNamedActor("hero");
    }

    @Override
    protected ActionProvider getActionProvider() {
        complete = true;
        return new SingleActionProvider(new FaceAction(getDirectionToHero()));
    }

    private Direction getDirectionToHero() {
        final TileCoordinate heroLocation = hero.getLocation();
        final TileCoordinate actorLocation = getActor().getLocation();
        final int deltaX = actorLocation.getX() - heroLocation.getX();
        final int deltaY = actorLocation.getY() - heroLocation.getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return (deltaX < 0) ? Direction.RIGHT : Direction.LEFT;
        } else {
            return (deltaY < 0) ? Direction.UP : Direction.DOWN;
        }
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
