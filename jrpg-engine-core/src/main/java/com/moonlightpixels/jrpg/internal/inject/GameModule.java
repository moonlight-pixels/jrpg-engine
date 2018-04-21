package com.moonlightpixels.jrpg.internal.inject;

import com.google.inject.AbstractModule;
import com.moonlightpixels.jrpg.combat.internal.CombatState;
import com.moonlightpixels.jrpg.combat.internal.DefaultCombatState;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.frontend.internal.DefaultFrontEndState;
import com.moonlightpixels.jrpg.frontend.internal.FrontEndState;
import com.moonlightpixels.jrpg.map.internal.DefaultMapState;
import com.moonlightpixels.jrpg.map.internal.MapState;

public final class GameModule extends AbstractModule {
    private final JRPGConfiguration configuration;

    public GameModule(final JRPGConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(FrontEndState.class).to(DefaultFrontEndState.class).asEagerSingleton();
        bind(MapState.class).to(DefaultMapState.class).asEagerSingleton();
        bind(CombatState.class).to(DefaultCombatState.class).asEagerSingleton();
    }
}
