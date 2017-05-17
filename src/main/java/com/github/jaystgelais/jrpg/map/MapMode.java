package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.ActorSpriteSet;
import com.github.jaystgelais.jrpg.map.actor.SpriteSetData;
import com.github.jaystgelais.jrpg.map.trigger.Trigger;
import com.github.jaystgelais.jrpg.map.trigger.TriggerContext;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MapMode extends GameMode {
    private static final int DEFAULT_TIME_TO_TRAVERSE_TILE_MS = 300;
    public static final int DEBUG_TEXT_X = 10;
    public static final int DEBUB_TEXT_Y = 20;

    private final StateMachine stateMachine;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final String initialMapPath;
    private final TileCoordinate initialLocation;
    private final SpriteSetData heroSpriteSetData;
    private final List<Trigger> triggers;
    private GameMap map;
    private Actor hero;

    public MapMode(final OrthographicCamera camera, final String mapPath,
                   final TileCoordinate initialLocation, final SpriteSetData heroSpriteSetData) {
        this(camera, mapPath, initialLocation, heroSpriteSetData, new AssetManager());
    }

    MapMode(final OrthographicCamera camera, final String mapPath,
            final TileCoordinate initialLocation, final SpriteSetData heroSpriteSetData,
            final AssetManager assetManager) {
        this.camera = camera;
        this.initialLocation = initialLocation;
        this.assetManager = assetManager;
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        initialMapPath = mapPath;
        this.heroSpriteSetData = heroSpriteSetData;
        triggers = new LinkedList<>();
        stateMachine = initStateMachine();
    }

    public void addTrigger(final Trigger trigger) {
        triggers.add(trigger);
    }

    @Override
    public String getKey() {
        return "mapMode";
    }

    @Override
    public void onEnter(final Map<String, Object> params) {
        if (params.containsKey("map")) {
            loadMap((String) params.get("map"));
        } else {
            loadMap(initialMapPath);
        }
        ActorSpriteSet heroSpriteSet = new ActorSpriteSet(
                heroSpriteSetData, DEFAULT_TIME_TO_TRAVERSE_TILE_MS, assetManager
        );
        hero = new Actor(map, heroSpriteSet, initialLocation);
        map.setFocalPoint(hero);
        map.focusCamera();
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void update(final long elapsedTime) {
        for (Trigger trigger : triggers) {
            if (trigger.isTriggered(hero)) {
                stateMachine.change("triggerInControl", Collections.singletonMap("trigger", trigger));
                return;
            }
        }
        stateMachine.update(elapsedTime);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        map.focusCamera();
        stateMachine.render(graphicsService);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    private void loadMap(final String mapPath) {
        if (!assetManager.isLoaded(mapPath, TiledMap.class)) {
            assetManager.load(mapPath, TiledMap.class);
            assetManager.finishLoading();
        }
        TiledMap tiledMap = assetManager.get(mapPath);
        TiledMapRenderer mapRenderer = getGame().getGraphicsService().getTileMapRenderer(tiledMap);
        this.map = new GameMap(camera, tiledMap, mapRenderer);
    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "playerInControl";
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (inputService.isPressed(Inputs.CANCEL)) {
                    getGame().exitGame();
                }
                hero.handleInput(inputService);
            }

            @Override
            public void update(final long elapsedTime) {
                hero.update(elapsedTime);
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndActors(graphicsService);
            }
        });
        states.add(new State() {
            private StateMachine triggerState;
            private TriggerContext context;

            @Override
            public String getKey() {
                return "triggerInControl";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                Trigger trigger = (Trigger) params.get("trigger");
                context = new TriggerContext();
                triggerState = trigger.performAction(context);
            }

            @Override
            public void onExit() {
                triggerState.dispose();
                triggerState = null;
                context = null;
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndActors(graphicsService);
                triggerState.render(graphicsService);
            }

            @Override
            public void dispose() {
                triggerState.dispose();
            }

            @Override
            public void handleInput(final InputService inputService) {
                triggerState.handleInput(inputService);
            }

            @Override
            public void update(final long elapsedTime) {
                if (context.isComplete()) {
                    stateMachine.change("playerInControl");
                } else {
                    triggerState.update(elapsedTime);
                }
            }
        });

        return new StateMachine(states, "playerInControl");
    }

    private void renderMapAndActors(final GraphicsService graphicsService) {
        map.renderBackground(graphicsService);

        graphicsService.renderStart();
        hero.render(graphicsService);
        graphicsService.renderEnd();

        map.renderForeground(graphicsService);

        if (getGame().isDebug()) {
            graphicsService.renderStart();
            graphicsService.getFontSet().getTextFont().draw(
                    graphicsService.getSpriteBatch(),
                    "FPS: " + Gdx.graphics.getFramesPerSecond()
                            + "   Position [X:" + hero.getLocation().getX()
                            + "  Y:" + hero.getLocation().getY() + "]",
                    camera.position.x - ((float) graphicsService.getResolutionWidth() / 2) + DEBUG_TEXT_X,
                    camera.position.y - ((float) graphicsService.getResolutionHeight() / 2) + DEBUB_TEXT_Y
            );
            graphicsService.renderEnd();
        }
    }
}
