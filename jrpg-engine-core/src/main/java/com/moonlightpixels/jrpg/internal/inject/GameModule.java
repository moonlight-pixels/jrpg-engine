package com.moonlightpixels.jrpg.internal.inject;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.combat.CombatConfig;
import com.moonlightpixels.jrpg.combat.CombatState;
import com.moonlightpixels.jrpg.combat.internal.DefaultCombatConfig;
import com.moonlightpixels.jrpg.combat.internal.DefaultCombatState;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.config.internal.ConfigurationHandler;
import com.moonlightpixels.jrpg.frontend.FrontEndConfig;
import com.moonlightpixels.jrpg.frontend.internal.DefaultFrontEndConfig;
import com.moonlightpixels.jrpg.frontend.internal.DefaultFrontEndState;
import com.moonlightpixels.jrpg.frontend.internal.FrontEndState;
import com.moonlightpixels.jrpg.input.InputSystem;
import com.moonlightpixels.jrpg.input.internal.DefaultInputSystem;
import com.moonlightpixels.jrpg.internal.DefaultJRPG;
import com.moonlightpixels.jrpg.internal.GameMode;
import com.moonlightpixels.jrpg.internal.GameStateHolder;
import com.moonlightpixels.jrpg.internal.JRPG;
import com.moonlightpixels.jrpg.map.JRPGMap;
import com.moonlightpixels.jrpg.map.JRPGMapFactory;
import com.moonlightpixels.jrpg.map.character.internal.PlayerInputCharacterController;
import com.moonlightpixels.jrpg.map.internal.DefaultJRPGMap;
import com.moonlightpixels.jrpg.map.internal.DefaultMapState;
import com.moonlightpixels.jrpg.map.internal.MapState;
import com.moonlightpixels.jrpg.ui.Menu;
import com.moonlightpixels.jrpg.ui.UserInterface;
import com.moonlightpixels.jrpg.ui.internal.DefaultUserInterface;
import com.moonlightpixels.jrpg.ui.standard.FrontEndMenu;
import com.moonlightpixels.jrpg.ui.standard.action.ExitGameAction;
import com.moonlightpixels.jrpg.ui.standard.action.NewGameAction;
import com.moonlightpixels.jrpg.ui.standard.internal.MenuConfigurationHandler;

import javax.inject.Named;
import javax.inject.Singleton;

import static com.moonlightpixels.jrpg.internal.inject.GraphicsModule.FRONTEND;

public final class GameModule extends AbstractModule {
    public static final String INITIAL_STATE = "initial";
    private final JRPGConfiguration configuration;

    GameModule(final JRPGConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(JRPG.class).to(DefaultJRPG.class).asEagerSingleton();
        bind(FrontEndState.class).to(DefaultFrontEndState.class).asEagerSingleton();
        bind(MapState.class).to(DefaultMapState.class).asEagerSingleton();
        bind(CombatState.class).to(DefaultCombatState.class).asEagerSingleton();
        bind(UserInterface.class).to(DefaultUserInterface.class).asEagerSingleton();
        bind(GameStateHolder.class).asEagerSingleton();
        bind(InputSystem.class).to(DefaultInputSystem.class).asEagerSingleton();
        bind(PlayerInputCharacterController.class).asEagerSingleton();

        // standard front end actions
        bind(ExitGameAction.class);
        bind(NewGameAction.class);

        install(
            new FactoryModuleBuilder()
                .implement(JRPGMap.class, DefaultJRPGMap.class)
                .build(JRPGMapFactory.class)
        );
    }

    @Provides
    @Singleton
    FrontEndConfig provideFrontEndConfig(final ConfigurationHandler configurationHandler) {
        FrontEndConfig frontEndConfig = new DefaultFrontEndConfig();
        configurationHandler.configure(frontEndConfig);

        return frontEndConfig;
    }

    @Provides
    @Named(INITIAL_STATE)
    GameMode provideInitialState(final FrontEndState frontEndState) {
        return frontEndState;
    }

    @Provides
    @Named(FRONTEND)
    Menu provideFrontEndMenu(final UserInterface userInterface,
                             final MenuConfigurationHandler configurationHandler,
                             final NewGameAction newGameAction,
                             final ExitGameAction exitGameAction) {
        final FrontEndMenu frontEndMenu = FrontEndMenu.builder()
            .newGameAction(newGameAction)
            .exitGameAction(exitGameAction)
            .build();
        configurationHandler.configureFrontEndMenu(frontEndMenu);
        return frontEndMenu.getMenu(userInterface);
    }

    @Provides
    GameState provideGameState(final GameStateHolder gameStateHolder) {
        return gameStateHolder.getGameState();
    }

    @Provides
    @Singleton
    StatSystem provideStatSystem(final ConfigurationHandler configurationHandler) {
        final StatSystem statSystem = new StatSystem();
        configurationHandler.configure(statSystem);
        return statSystem;
    }

    @Provides
    @Singleton
    CombatConfig provideCombatConfig() {
        return new DefaultCombatConfig();
    }

    @Provides
    @Singleton
    @Named("combat")
    MessageDispatcher provideCombatMessageDispatcher() {
        return new MessageDispatcher();
    }
}
