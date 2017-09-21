package com.github.jaystgelais.jrpg.map.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.Entity;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.util.TimeUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Actor implements Entity, InputHandler {
    private final GameMap map;
    private final ActorSpriteSet spriteSet;
    private final StateMachine stateMachine;
    private final Controller controller;
    private final int height;
    private final int width;
    private Direction facing;
    private TileCoordinate location;
    private TileCoordinate destination;
    private int positionX;
    private int positionY;
    private boolean isHero;

    public Actor(final GameMap map, final ActorSpriteSet spriteSet,
                 final Controller controller, final TileCoordinate location) {
        this.map = map;
        this.spriteSet = spriteSet;
        this.controller = controller;
        this.facing = Direction.DOWN;
        this.location = location;
        this.destination = location;
        positionX = map.getAbsoluteX(location);
        positionY = map.getAbsoluteY(location);
        height = spriteSet.getSpriteHeight();
        width = spriteSet.getSpriteWidth();
        stateMachine = initStateMachine();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public boolean isHero() {
        return isHero;
    }

    public void setIsHero(final boolean isHero) {
        this.isHero = isHero;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(final Direction direction) {
        this.facing = direction;
    }

    void walk(final Direction direction) {
        TileCoordinate target = getAdjacentTileCoordinate(direction);
        if (isOpen(target)) {
            destination = target;
            stateMachine.change("walking");
        }
    }

    void inspect() {
        Entity targetEntiry = map.getEntity(getAdjacentTileCoordinate(facing));
        if (targetEntiry != null) {
            targetEntiry.interactWith();
        } else {
            map.fireOnInspectTrigger(getAdjacentTileCoordinate(facing));
        }
    }

    private TileCoordinate getAdjacentTileCoordinate(final Direction direction) {
        TileCoordinate target = null;
        switch (direction) {
            case UP:
                target = location.getAbove();
                break;
            case DOWN:
                target = location.getBelow();
                break;
            case LEFT:
                target = location.getLeft();
                break;
            case RIGHT:
                target = location.getRight();
                break;
            default:
        }

        return target;
    }

    public TileCoordinate getLocation() {
        return location;
    }

    public void setLocation(final TileCoordinate location) {
        this.location = location;
    }

    public boolean isOccupying(final TileCoordinate coordinate) {
        return coordinate.equals(location) || coordinate.equals(destination);
    }

    @Override
    public void interactWith() {

    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    @Override
    public void dispose() {

    }

    private StateMachine initStateMachine() {
        final Actor actor = this;

        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "standing";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                positionX = map.getAbsoluteX(location);
                positionY = map.getAbsoluteY(location);
            }

            @Override
            public void update(final long elapsedTime) {
                controller.update(elapsedTime);
                Action action = controller.nextAction();
                if (action != null) {
                    action.perform(actor);
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                final TextureRegion standingImage = spriteSet.getStandingImage(facing);
                graphicsService.drawSprite(
                        standingImage,
                        positionX - (standingImage.getRegionWidth() / 2.0f), positionY
                );
            }
        });
        states.add(new StateAdapter() {
            private long timeInState = 0L;

            @Override
            public String getKey() {
                return "walking";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInState = 0L;
            }

            @Override
            public void onExit() {
                map.fireOnExitTrigger(location);
                location = destination;
                destination = null;
                map.fireOnEnterTrigger(location);
            }

            @Override
            public void update(final long elapsedTime) {
                timeInState += elapsedTime;
                if (timeInState > spriteSet.getTimeToTraverseTileMs()) {
                    stateMachine.change("standing");
                } else {
                    float percentComplete = TimeUtil.calculatePercentComplete(
                            timeInState, spriteSet.getTimeToTraverseTileMs()
                    );
                    positionX = weightedAverage(
                            map.getAbsoluteX(location), map.getAbsoluteX(destination), percentComplete
                    );
                    positionY = weightedAverage(
                            map.getAbsoluteY(location), map.getAbsoluteY(destination), percentComplete
                    );
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                final TextureRegion walkingFrame = spriteSet.getWalkingAnimation(facing).getKeyFrame(
                        TimeUtil.convertMsToFloatSeconds(timeInState)
                );
                graphicsService.drawSprite(
                        walkingFrame,
                        positionX - (walkingFrame.getRegionWidth() / 2.0f), positionY
                );
            }
        });

        return new StateMachine(states, "standing");
    }

    private boolean isOpen(final TileCoordinate targetCoordinate) {
        // check that move is inbounds for map
        if (targetCoordinate.getX() < 0
                || targetCoordinate.getY() < 0
                || targetCoordinate.getX() >= map.getMapWidthInTiles()
                || targetCoordinate.getY() >= map.getMapHeightInTiles()) {
            return false;
        }

        return !map.isCollision(this, targetCoordinate);
    }

    private int weightedAverage(final int start, final int finish, final float percentComplete) {
        return Math.round(start + ((finish - start) * percentComplete));
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    public boolean isWaiting() {
        return stateMachine.getCurrentState().getKey().equals("standing");
    }
}
