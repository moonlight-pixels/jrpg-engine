package com.moonlightpixels.jrpg.map.character;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.moonlightpixels.jrpg.map.Direction;
import com.moonlightpixels.jrpg.map.JRPGMap;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import com.moonlightpixels.jrpg.map.character.internal.CharacterCommand;
import com.moonlightpixels.jrpg.map.character.internal.CharacterController;
import com.moonlightpixels.jrpg.map.internal.MapActor;
import com.moonlightpixels.jrpg.tween.Vector2Tween;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class CharacterActor extends MapActor<CharacterAnimation> {
    private static final float DEFAULT_SPEED = 0.3f;

    private final StateMachine<CharacterActor, CharacterState> stateMachine;
    private CharacterController controller;
    private Direction direction;
    private final int tilesCoveredPerWalkCycle;
    private float x;
    private float y;
    @Getter
    @Setter
    private float timeToTraverseTile = DEFAULT_SPEED;
    @Getter
    private TileCoordinate destination;
    private Vector2Tween walkingTween;

    /**
     * Creates a new CharacterActor.
     *  @param position Position on the Map
     * @param map Reference to the Map this actor is being placed on.
     * @param animationSet AnimationSet used by ths actor
     * @param controller Controller for character
     * @param initialDirection Direction this character is initially facing
     */
    public CharacterActor(final TileCoordinate position, final JRPGMap map,
                          final CharacterAnimationSet animationSet,
                          final CharacterController controller,
                          final Direction initialDirection) {
        super(map, animationSet, position);
        this.controller = controller;
        direction = initialDirection;
        x = calculateXForCenteredOnTile(position);
        y = map.getTileCoordinateXY(position).y;
        tilesCoveredPerWalkCycle = animationSet.getTilesCoveredPerWalkCycle();
        stateMachine = new StackStateMachine<>(this);
        stateMachine.changeState(CharacterState.Standing);
    }

    @Override
    protected final float calculateX() {
        return x;
    }

    @Override
    protected final float calculateY() {
        return y;
    }

    @Override
    protected final void actInternal(final float delta) {
        getWalkingTween().ifPresent(tween -> tween.update(delta));
        stateMachine.update();
    }

    private float getWalkAnimationDuration() {
        return tilesCoveredPerWalkCycle * timeToTraverseTile;
    }

    private Optional<Vector2Tween> getWalkingTween() {
        return Optional.ofNullable(walkingTween);
    }

    private boolean nextDestination() {
        if (!getMap().isOpen(getPosition().getAdjacent(direction))) {
            return false;
        }
        destination = getPosition().getAdjacent(direction);
        walkingTween = Vector2Tween.builder()
            .start(
                new Vector2(
                    calculateXForCenteredOnTile(getPosition()),
                    getMap().getTileCoordinateXY(getPosition()).y
                )
            )
            .end(
                new Vector2(
                    calculateXForCenteredOnTile(destination),
                    getMap().getTileCoordinateXY(destination).y
                )
            )
            .totalTweenTime(timeToTraverseTile)
            .build();
        return true;
    }

    private void arriveAtDestination() {
        setPosition(destination);
        x = calculateXForCenteredOnTile(getPosition());
        y = getMap().getTileCoordinateXY(getPosition()).y;
        destination = null;
        walkingTween = null;
    }

    private enum CharacterState implements State<CharacterActor> {
        Standing {
            @Override
            public void enter(final CharacterActor entity) {
                entity.setAnimation(
                    CharacterAnimation.getStandingAnimation(entity.direction),
                    1.0f
                );
            }

            @Override
            public void update(final CharacterActor entity) {
                CharacterCommand command = entity.controller.getNextCommand(entity);
                if (command != CharacterCommand.Stand) {
                    entity.direction = CharacterCommand.getDirectionForWalkCommand(command);
                    entity.stateMachine.changeState(Walking);
                }
            }
        },
        Walking {
            @Override
            public void enter(final CharacterActor entity) {
                if (attemptNextTile(entity)) {
                    entity.setAnimation(
                        CharacterAnimation.getWalkingAnimation(entity.direction),
                        entity.getWalkAnimationDuration()
                    );
                }
            }

            @Override
            public void update(final CharacterActor entity) {
                if (entity.walkingTween.isComplete()) {
                    entity.arriveAtDestination();

                    CharacterCommand command = entity.controller.getNextCommand(entity);
                    if (command == CharacterCommand.Stand) {
                        entity.stateMachine.changeState(Standing);
                    } else if (command == CharacterCommand.getWalkByDirection(entity.direction)) {
                        attemptNextTile(entity);
                    } else {
                        entity.direction = CharacterCommand.getDirectionForWalkCommand(command);
                        entity.stateMachine.changeState(Walking);
                    }
                } else {
                    entity.x = entity.walkingTween.getValue().x;
                    entity.y = entity.walkingTween.getValue().y;
                }
            }

            private boolean attemptNextTile(final CharacterActor entity) {
                if (!entity.nextDestination()) {
                    entity.stateMachine.changeState(CharacterState.Standing);
                    return false;
                }
                return true;
            }
        };

        @Override
        public void enter(final CharacterActor entity) {

        }

        @Override
        public void update(final CharacterActor entity) {

        }

        @Override
        public void exit(final CharacterActor entity) {

        }

        @Override
        public boolean onMessage(final CharacterActor entity, final Telegram telegram) {
            return false;
        }
    }
}
