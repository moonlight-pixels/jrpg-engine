package com.moonlightpixels.jrpg.map.internal;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import com.moonlightpixels.jrpg.internal.GameStateHolder;
import com.moonlightpixels.jrpg.internal.JRPG;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;
import com.moonlightpixels.jrpg.internal.inject.GraphicsModule;
import com.moonlightpixels.jrpg.map.Direction;
import com.moonlightpixels.jrpg.map.JRPGMap;
import com.moonlightpixels.jrpg.map.JRPGMapFactory;
import com.moonlightpixels.jrpg.map.character.CharacterActor;
import com.moonlightpixels.jrpg.map.character.internal.CharacterAnimationSetRegistry;
import com.moonlightpixels.jrpg.map.character.internal.PlayerInputCharacterController;
import com.moonlightpixels.jrpg.ui.InputHandler;
import com.moonlightpixels.jrpg.ui.Menu;

import javax.inject.Inject;
import javax.inject.Named;

import static com.moonlightpixels.jrpg.internal.inject.GraphicsModule.MAP;

public final class DefaultMapState implements MapState {
    private final GraphicsContext graphicsContext;
    private final GdxFacade gdx;
    private final MapRegistry mapRegistry;
    private final CharacterAnimationSetRegistry characterAnimationSetRegistry;
    private final JRPGMapFactory mapFactory;
    private final GameStateHolder gameStateHolder;
    private final PlayerInputCharacterController playerInputCharacterController;
    private final Menu menu;
    private final StateMachine<DefaultMapState, MapModeState> stateMachine;
    private GameState gameState;
    private JRPGMap map;

    @Inject
    public DefaultMapState(@Named(GraphicsModule.MAP) final GraphicsContext graphicsContext,
                           final GdxFacade gdx,
                           final MapRegistry mapRegistry,
                           final CharacterAnimationSetRegistry characterAnimationSetRegistry,
                           final JRPGMapFactory mapFactory,
                           final GameStateHolder gameStateHolder,
                           final PlayerInputCharacterController playerInputCharacterController,
                           @Named(MAP) final Menu menu) {
        this.graphicsContext = graphicsContext;
        this.gdx = gdx;
        this.mapRegistry = mapRegistry;
        this.characterAnimationSetRegistry = characterAnimationSetRegistry;
        this.mapFactory = mapFactory;
        this.gameStateHolder = gameStateHolder;
        this.playerInputCharacterController = playerInputCharacterController;
        this.menu = menu;
        this.stateMachine = new DefaultStateMachine<>(this, MapModeState.Default);
    }

    @Override
    public void enter(final JRPG entity) {
        gameState = gameStateHolder.getGameState();
        map = mapRegistry.getMap(gameState.getLocation().getMap()).load(mapFactory);
        CharacterActor hero = new CharacterActor(
            gameState.getLocation().getTileCoordinate(),
            map,
            characterAnimationSetRegistry.getCharacterAnimationSet(gameState.getHeroAnimationSet()),
            playerInputCharacterController,
            Direction.DOWN
        );
        map.addActor(hero);
    }

    @Override
    public void update(final JRPG entity) {
        gameState.update(gdx.getGraphics().getDeltaTime());
        map.update(gdx.getGraphics().getDeltaTime());
        graphicsContext.activate();
        map.render();
    }

    @Override
    public void exit(final JRPG entity) {

    }

    @Override
    public boolean onMessage(final JRPG entity, final Telegram telegram) {
        return false;
    }

    @Override
    public boolean handleControlEvent(final ControlEvent event) {
        return stateMachine.getCurrentState().handleControlEvent(event);
    }

    @Override
    public boolean handleClickEvent(final ClickEvent event) {
        return stateMachine.getCurrentState().handleClickEvent(event);
    }

    @Override
    public void setInputScheme(final InputScheme inputScheme) {
        playerInputCharacterController.setInputScheme(inputScheme);
    }

    private enum MapModeState implements State<DefaultMapState>, InputHandler {
        Default {
            private DefaultMapState mapState;

            @Override
            public void enter(final DefaultMapState entity) {
                mapState = entity;
            }

            @Override
            public boolean handleControlEvent(final ControlEvent event) {
                if (event == ControlEvent.MENU_PRESSED) {
                    mapState.stateMachine.changeState(Menu);
                }
                return mapState.playerInputCharacterController.handleControlEvent(event);
            }

            @Override
            public boolean handleClickEvent(final ClickEvent event) {
                return mapState.playerInputCharacterController.handleClickEvent(event);
            }
        },
        Menu {
            @Override
            public void enter(final DefaultMapState entity) {
                entity.menu.open();
            }

            @Override
            public void update(final DefaultMapState entity) {
                if (!entity.menu.isOpen()) {
                    entity.stateMachine.changeState(Default);
                }
            }

            @Override
            public boolean handleControlEvent(final ControlEvent event) {
                return false;
            }

            @Override
            public boolean handleClickEvent(final ClickEvent event) {
                return false;
            }
        };

        @Override
        public void setInputScheme(final InputScheme inputScheme) {

        }

        @Override
        public void enter(final DefaultMapState entity) {

        }

        @Override
        public void update(final DefaultMapState entity) {

        }

        @Override
        public void exit(final DefaultMapState entity) {

        }

        @Override
        public boolean onMessage(final DefaultMapState entity, final Telegram telegram) {
            return false;
        }
    }
}
