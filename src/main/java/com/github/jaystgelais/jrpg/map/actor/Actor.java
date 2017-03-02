package com.github.jaystgelais.jrpg.map.actor;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.util.TimeUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Actor implements Renderable, InputHandler, Updatable {
    private final GameMap map;
    private final ActorSpriteSet spriteSet;
    private final StateMachine stateMachine;
    private Direction facing;
    private TileCoordinate location;
    private TileCoordinate destination;
    private float positionX;
    private float positionY;

    public Actor(final GameMap map, final ActorSpriteSet spriteSet, final TileCoordinate location) {
        this.map = map;
        this.spriteSet = spriteSet;
        this.facing = Direction.DOWN;
        this.location = location;
        this.destination = location;
        stateMachine = initStateMachine();
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public Direction getFacing() {
        return facing;
    }

    public TileCoordinate getLocation() {
        return location;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    @Override
    public void dispose() {

    }

    private StateMachine initStateMachine() {
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
            public void handleInput(final InputService inputService) {
                TileCoordinate targetCoordinate = null;
                if (inputService.isPressed(Inputs.UP)) {
                    facing = Direction.UP;
                    targetCoordinate = location.getAbove();
                } else if (inputService.isPressed(Inputs.DOWN)) {
                    facing = Direction.DOWN;
                    targetCoordinate = location.getBelow();
                } else if (inputService.isPressed(Inputs.LEFT)) {
                    facing = Direction.LEFT;
                    targetCoordinate = location.getLeft();
                } else if (inputService.isPressed(Inputs.RIGHT)) {
                    facing = Direction.RIGHT;
                    targetCoordinate = location.getRight();
                }

                if (targetCoordinate != null && isOpen(targetCoordinate)) {
                    destination = targetCoordinate;
                    stateMachine.change("walking");
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                graphicsService.drawSprite(
                        spriteSet.getStandingImage(facing),
                        positionX, positionY
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
                location = destination;
                destination = null;
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
                graphicsService.drawSprite(
                        spriteSet.getWalkingAnimation(facing).getKeyFrame(
                                TimeUtil.convertMsToFloatSeconds(timeInState)
                        ),
                        positionX, positionY
                );
            }
        });

        return new StateMachine(states, "standing");
    }

    private boolean isOpen(final TileCoordinate targetCoordinate) {
        // TODO check collsion data from map
        return true;
    }

    private float weightedAverage(final float start, final float finish, final float percentComplete) {
        return start + ((finish - start) * percentComplete);
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }
}
