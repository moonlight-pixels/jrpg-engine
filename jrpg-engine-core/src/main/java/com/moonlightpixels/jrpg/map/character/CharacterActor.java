package com.moonlightpixels.jrpg.map.character;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.moonlightpixels.jrpg.map.Direction;
import com.moonlightpixels.jrpg.map.JRPGMap;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import com.moonlightpixels.jrpg.map.internal.MapActor;
import com.moonlightpixels.jrpg.tween.Vector2Tween;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class CharacterActor extends MapActor<CharacterAnimation> {
    private static final float DEFAULT_SPEED = 0.3f;

    private final StateMachine<CharacterActor, CharacterState> stateMachine;
    private Direction direction;
    private final int tilesCoveredPerWalkCycle;
    private float x;
    private float y;
    @Getter
    @Setter
    private float timeToTraverseTile = DEFAULT_SPEED;
    private Vector2Tween walkingTween;

    protected CharacterActor(final JRPGMap map, final CharacterAnimationSet animationSet,
                             final TileCoordinate position, final Direction initialDirection) {
        super(map, animationSet, position);
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

    protected final void walk() {
        stateMachine.changeState(CharacterState.Walking);
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

    private enum CharacterState implements State<CharacterActor> {
        Standing {
            @Override
            public void enter(final CharacterActor entity) {
                entity.setAnimation(
                    CharacterAnimation.getStandingAnimation(entity.direction),
                    1.0f
                );
            }
        },
        Walking {
            @Override
            public void enter(final CharacterActor entity) {
                entity.setAnimation(
                    CharacterAnimation.getWalkingAnimation(entity.direction),
                    entity.getWalkAnimationDuration()
                );
                entity.walkingTween = new Vector2Tween(
                    new Vector2(
                        entity.calculateXForCenteredOnTile(entity.getPosition()),
                        entity.getMap().getTileCoordinateXY(entity.getPosition()).y
                    ),
                    new Vector2(
                        entity.calculateXForCenteredOnTile(entity.getPosition().getAdjacent(entity.direction)),
                        entity.getMap().getTileCoordinateXY(entity.getPosition().getAdjacent(entity.direction)).y
                    ),
                    entity.getWalkAnimationDuration()
                );
            }

            @Override
            public void update(final CharacterActor entity) {
                if (entity.walkingTween.isComplete()) {
                    entity.stateMachine.changeState(Standing);
                } else {
                    entity.x = entity.walkingTween.getValue().x;
                    entity.y = entity.walkingTween.getValue().y;
                }
            }

            @Override
            public void exit(final CharacterActor entity) {
                entity.setPosition(entity.getPosition().getAdjacent(entity.direction));
                entity.x = entity.calculateXForCenteredOnTile(entity.getPosition());
                entity.y = entity.getMap().getTileCoordinateXY(entity.getPosition()).y;
                entity.walkingTween = null;
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
