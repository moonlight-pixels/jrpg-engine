package com.moonlightpixels.jrpg.combat.render.internal;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.moonlightpixels.jrpg.combat.CombatActionOutcome;
import com.moonlightpixels.jrpg.combat.CombatConfig;
import com.moonlightpixels.jrpg.combat.PlayerCombatant;
import com.moonlightpixels.jrpg.combat.internal.BattleInfo;
import com.moonlightpixels.jrpg.combat.internal.CombatMessageTypes;
import com.moonlightpixels.jrpg.combat.render.BattleAnimation;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

import static com.moonlightpixels.jrpg.internal.inject.GraphicsModule.COMBAT;

public final class BattleRenderer implements Telegraph {
    private final CombatConfig config;
    private final MessageDispatcher messageDispatcher;
    private final GraphicsContext graphicsContext;
    private final GdxFacade gdx;
    private final Stage stage;
    private final StateMachine<BattleRenderer, BattleRendererState> stateMachine;
    private final Map<PlayerCombatant, CombatActor> actorMap = new HashMap<>();
    private BattleInfo battleInfo;
    private BattleAnimation currentAnimation;

    @Inject
    public BattleRenderer(final CombatConfig config,
                          @Named("combat") final MessageDispatcher messageDispatcher,
                          @Named(COMBAT) final GraphicsContext graphicsContext,
                          final GdxFacade gdx) {
        this.config = config;
        this.messageDispatcher = messageDispatcher;
        this.graphicsContext = graphicsContext;
        stage = graphicsContext.createStage();
        this.gdx = gdx;
        stateMachine = new DefaultStateMachine<>(this, BattleRendererState.Initial);
        messageDispatcher.addListeners(
            this,
            CombatMessageTypes.START_BATTLE,
            CombatMessageTypes.START_ANIMATION
        );
    }

    @Override
    public boolean handleMessage(final Telegram msg) {
        return stateMachine.handleMessage(msg);
    }

    public void update(final float elapsedTime) {
        stateMachine.update();
        if (battleInfo != null) {
            stage.act(elapsedTime);
            battleInfo.getBackground().ifPresent(battleBackground -> {
                battleBackground.update(elapsedTime);
                battleBackground.render(graphicsContext.getSpriteBatch());
            });
            stage.draw();
        }
    }

    void init(final BattleInfo battleInfo) {
        this.battleInfo = battleInfo;
        changeStateAndUpdate(BattleRendererState.StandBy);
    }

    void animateAction(final CombatActionOutcome actionOutcome) {
        currentAnimation = actionOutcome.getAnimation();
        changeStateAndUpdate(BattleRendererState.Animation);
    }

    public void reset() {
        battleInfo = null;
        actorMap.clear();
        stage.clear();
        changeStateAndUpdate(BattleRendererState.Initial);
    }

    private void changeStateAndUpdate(final BattleRendererState state) {
        stateMachine.changeState(state);
        stateMachine.update();
    }

    private enum BattleRendererState implements State<BattleRenderer> {
        Initial {
            @Override
            public boolean onMessage(final BattleRenderer entity, final Telegram telegram) {
                if (telegram.message == CombatMessageTypes.START_BATTLE) {
                    entity.init((BattleInfo) telegram.extraInfo);
                    return true;
                }
                return false;
            }
        },
        StandBy {
            @Override
            public boolean onMessage(final BattleRenderer entity, final Telegram telegram) {
                if (telegram.message == CombatMessageTypes.START_ANIMATION) {
                    entity.animateAction((CombatActionOutcome) telegram.extraInfo);
                    return true;
                }
                return false;
            }
        },
        Animation {
            @Override
            public void enter(final BattleRenderer entity) {
                entity.currentAnimation.start();
            }

            @Override
            public void update(final BattleRenderer entity) {
                entity.currentAnimation.update(entity.gdx.getGraphics().getDeltaTime());
                if (entity.currentAnimation.isComplete()) {
                    entity.currentAnimation = null;
                    entity.changeStateAndUpdate(BattleRendererState.StandBy);
                }
            }

            @Override
            public void exit(final BattleRenderer entity) {
                entity.messageDispatcher.dispatchMessage(CombatMessageTypes.ANIMATION_COMPLETE);
            }
        };

        @Override
        public void enter(final BattleRenderer entity) {

        }

        @Override
        public void update(final BattleRenderer entity) {

        }

        @Override
        public void exit(final BattleRenderer entity) {

        }

        @Override
        public boolean onMessage(final BattleRenderer entity, final Telegram telegram) {
            return false;
        }
    }
}
