package com.moonlightpixels.jrpg.map.internal;

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

import javax.inject.Inject;
import javax.inject.Named;

public final class DefaultMapState implements MapState {
    private final GraphicsContext graphicsContext;
    private final GdxFacade gdx;
    private final MapRegistry mapRegistry;
    private final CharacterAnimationSetRegistry characterAnimationSetRegistry;
    private final JRPGMapFactory mapFactory;
    private final GameStateHolder gameStateHolder;
    private GameState gameState;
    private JRPGMap map;

    @Inject
    public DefaultMapState(@Named(GraphicsModule.MAP) final GraphicsContext graphicsContext,
                           final GdxFacade gdx,
                           final MapRegistry mapRegistry,
                           final CharacterAnimationSetRegistry characterAnimationSetRegistry,
                           final JRPGMapFactory mapFactory,
                           final GameStateHolder gameStateHolder) {
        this.graphicsContext = graphicsContext;
        this.gdx = gdx;
        this.mapRegistry = mapRegistry;
        this.characterAnimationSetRegistry = characterAnimationSetRegistry;
        this.mapFactory = mapFactory;
        this.gameStateHolder = gameStateHolder;
    }

    @Override
    public void enter(final JRPG entity) {
        gameState = gameStateHolder.getGameState();
        map = mapRegistry.getMap(gameState.getLocation().getMap()).load(mapFactory);
        CharacterActor hero = new CharacterActor(map,
            characterAnimationSetRegistry.getCharacterAnimationSet(gameState.getHeroAnimationSet()),
            gameState.getLocation().getTileCoordinate(),
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
        return false;
    }

    @Override
    public boolean handleClickEvent(final ClickEvent event) {
        return false;
    }

    @Override
    public void setInputScheme(final InputScheme inputScheme) {

    }
}
