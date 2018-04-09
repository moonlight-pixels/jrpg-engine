package com.github.jaystgelais.jrpg.combat.svb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.combat.BattleSpriteSet;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.tween.IntegerTween;
import com.github.jaystgelais.jrpg.tween.Tween;
import com.github.jaystgelais.jrpg.util.TimeUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class SideViewBattleActor implements Updatable, Renderable, SelectableActor {
    private static final long DEFAULT_ANIMATION_TIME_MS = 600L;
    private static final long DEFAULT_WALK_TIME_MS = 1000L;

    private final BattleSpriteSet spriteSet;
    private final int idlePositionX;
    private final int actionPositionX;
    private final int positionY;
    private final StateMachine stateMachine;
    private Animation<TextureRegion> activeAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkingAnimation;
    private Animation<TextureRegion> actionAnimation;
    private Animation<TextureRegion> attackedAnimation;
    private ActionLifecycleListener actionLifecycleListener;
    private long actionDurationMs;
    private long timeInAnimationMs = 0L;
    private int positionX;
    private Texture cursor;

    public SideViewBattleActor(final BattleSpriteSet spriteSet, final int idlePositionX,
                               final int actionPositionX, final int positionY) {
        this.spriteSet = spriteSet;
        activeAnimation = spriteSet.getIdle(DEFAULT_ANIMATION_TIME_MS);
        idleAnimation = spriteSet.getIdle(DEFAULT_ANIMATION_TIME_MS);
        walkingAnimation = spriteSet.getIdle(DEFAULT_ANIMATION_TIME_MS);
        this.idlePositionX = idlePositionX;
        this.actionPositionX = actionPositionX;
        this.positionY = positionY;
        positionX = idlePositionX;
        stateMachine = initStateMachine();
    }

    public void startAction(final Animation<TextureRegion> actionAnimation,
                            final ActionLifecycleListener actionLifecycleListener, final long actionDurationMs) {
        this.actionAnimation = actionAnimation;
        this.actionLifecycleListener = actionLifecycleListener;
        this.actionDurationMs = actionDurationMs;
        stateMachine.change("walk-forward");
    }

    public void startAttackReaction(final boolean isHit, final ActionLifecycleListener actionLifecycleListener) {
        this.attackedAnimation = (isHit)
                ? spriteSet.getTakeHit(DEFAULT_ANIMATION_TIME_MS).orElse(idleAnimation)
                : spriteSet.getDodge(DEFAULT_ANIMATION_TIME_MS).orElse(idleAnimation);
        this.actionLifecycleListener = actionLifecycleListener;
        stateMachine.change("attacked");
    }

    public void applyStatus(final String status, final long durationMs) {
        idleAnimation = spriteSet.getStatusAnimation(status, durationMs).orElse(idleAnimation);
    }

    public void applyDeadState() {
        idleAnimation = spriteSet.getDead(DEFAULT_ANIMATION_TIME_MS);
    }

    public void applyCriticalState() {
        idleAnimation = spriteSet.getCritical(DEFAULT_ANIMATION_TIME_MS).orElse(idleAnimation);
    }

    public void resetIdleAnimation() {
        idleAnimation = spriteSet.getIdle(DEFAULT_ANIMATION_TIME_MS);
    }

    public void setCustomIdleAnimation(final String animationType) {
        idleAnimation = spriteSet.getCustomAnimation(animationType, DEFAULT_ANIMATION_TIME_MS).orElse(idleAnimation);
    }

    @Override
    public void update(final long elapsedTime) {
        timeInAnimationMs += elapsedTime;
        stateMachine.update(elapsedTime);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        final TextureRegion keyFrame = activeAnimation.getKeyFrame(TimeUtil.convertMsToFloatSeconds(timeInAnimationMs));
        graphicsService.drawSprite(
                keyFrame,
                positionX,
                positionY
        );
        getCursor().ifPresent(cursor -> {
            graphicsService.drawSprite(
                    cursor,
                    positionX - cursor.getWidth(),
                    positionY + (keyFrame.getRegionHeight() / 2) - (cursor.getHeight() / 2)
            );
        });
    }

    @Override
    public void showCursor(final Texture cursor) {
        this.cursor = cursor;
    }

    @Override
    public void hideCursor() {
        cursor = null;
    }

    private Optional<Texture> getCursor() {
        return Optional.ofNullable(cursor);
    }

    @Override
    public void dispose() {

    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();

        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "idle";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInAnimationMs = 0L;
                activeAnimation = idleAnimation;
            }
        });

        states.add(new StateAdapter() {
            private Tween<Integer> positionXTween;

            @Override
            public String getKey() {
                return "walk-forward";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInAnimationMs = 0L;
                activeAnimation = walkingAnimation;
                positionXTween = new IntegerTween(idlePositionX, actionPositionX, DEFAULT_WALK_TIME_MS);
            }

            @Override
            public void update(final long elapsedTime) {
                positionXTween.update(elapsedTime);

                if (positionXTween.isComplete()) {
                    stateMachine.change("action");
                } else {
                    positionX = positionXTween.getValue();
                }
            }
        });

        states.add(new StateAdapter() {

            @Override
            public String getKey() {
                return "action";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInAnimationMs = 0L;
                activeAnimation = actionAnimation;
                actionLifecycleListener.actionStarted();
            }

            @Override
            public void update(final long elapsedTime) {
                if (timeInAnimationMs > actionDurationMs) {
                    stateMachine.change("walk-back");
                }
            }

            @Override
            public void onExit() {
                actionLifecycleListener.actionCompleted();
            }
        });

        states.add(new StateAdapter() {
            private Tween<Integer> positionXTween;

            @Override
            public String getKey() {
                return "walk-back";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInAnimationMs = 0L;
                activeAnimation = walkingAnimation;
                positionXTween = new IntegerTween(actionPositionX, idlePositionX, DEFAULT_WALK_TIME_MS);
            }

            @Override
            public void update(final long elapsedTime) {
                positionXTween.update(elapsedTime);

                if (positionXTween.isComplete()) {
                    stateMachine.change("idle");
                } else {
                    positionX = positionXTween.getValue();
                }
            }
        });

        states.add(new StateAdapter() {

            @Override
            public String getKey() {
                return "attacked";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInAnimationMs = 0L;
                activeAnimation = attackedAnimation;
                actionLifecycleListener.actionStarted();
            }

            @Override
            public void update(final long elapsedTime) {
                final float animationDuration = attackedAnimation.getAnimationDuration();
                if (timeInAnimationMs > TimeUtil.convertFloatSecondsToLongMs(animationDuration)) {
                    stateMachine.change("idle");
                }
            }

            @Override
            public void onExit() {
                actionLifecycleListener.actionCompleted();
            }
        });

        return new StateMachine(states, "idle");
    }
}
