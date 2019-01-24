package com.moonlightpixels.jrpg.combat.render.internal;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.moonlightpixels.jrpg.combat.internal.BattleInfo;
import com.moonlightpixels.jrpg.combat.CombatConfig;
import com.moonlightpixels.jrpg.combat.internal.CombatMessageTypes;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;

import javax.inject.Inject;
import javax.inject.Named;

import static com.moonlightpixels.jrpg.internal.inject.GraphicsModule.COMBAT;

public final class BattleRenderer implements Telegraph {
    private final CombatConfig config;
    private final MessageDispatcher messageDispatcher;
    private final GraphicsContext graphicsContext;
    private final Stage stage;
    private final StateMachine<BattleRenderer, BattleRendererState> stateMachine;
    private BattleInfo battleInfo;

    @Inject
    public BattleRenderer(final CombatConfig config,
                          @Named("combat") final MessageDispatcher messageDispatcher,
                          @Named(COMBAT) final GraphicsContext graphicsContext) {
        this.config = config;
        this.messageDispatcher = messageDispatcher;
        this.graphicsContext = graphicsContext;
        stage = graphicsContext.createStage();
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
    }

    public void reset() {
        battleInfo = null;
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
                return false;
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
    }
}
