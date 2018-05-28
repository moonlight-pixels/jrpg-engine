package com.moonlightpixels.jrpg.map.internal;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.internal.JRPG;
import com.moonlightpixels.jrpg.internal.inject.GraphicsModule;
import com.moonlightpixels.jrpg.map.JRPGMap;
import com.moonlightpixels.jrpg.map.JRPGMapFactory;

import javax.inject.Inject;
import javax.inject.Named;

public final class DefaultMapState implements MapState {
    private final OrthographicCamera camera;
    private final JRPGMapFactory mapFactory;
    private final GameState gameState;
    private JRPGMap map;

    @Inject
    public DefaultMapState(@Named(GraphicsModule.MAP_CAMERA) final OrthographicCamera camera,
                           final JRPGMapFactory mapFactory, final GameState gameState) {
        this.camera = camera;
        this.mapFactory = mapFactory;
        this.gameState = gameState;
    }

    @Override
    public void enter(final JRPG entity) {
        map = gameState.getLocation().getMap().load(mapFactory);
    }

    @Override
    public void update(final JRPG entity) {

    }

    @Override
    public void exit(final JRPG entity) {

    }

    @Override
    public boolean onMessage(final JRPG entity, final Telegram telegram) {
        return false;
    }
}
