package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.audio.MusicService;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.Direction;
import com.github.jaystgelais.jrpg.map.actor.PlayerController;
import com.github.jaystgelais.jrpg.map.actor.SpriteSetDefinition;
import com.github.jaystgelais.jrpg.map.trigger.Trigger;
import com.github.jaystgelais.jrpg.map.trigger.TriggerAction;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MapMode extends GameMode {
    private static final int DEFAULT_TIME_TO_TRAVERSE_TILE_MS = 300;
    private static final long DEFAULT_INITIAL_PAUSE_MS = 500;
    private static final int DEBUG_TEXT_X = 10;
    private static final int DEBUB_TEXT_Y = 20;

    private final StateMachine stateMachine;
    private final AssetManager assetManager;
    private final MapDefinition initialMap;
    private final TileCoordinate initialLocation;
    private final SpriteSetDefinition heroSpriteSet;
    private final PlayerController controller = new PlayerController();
    private GameMap map;
    private Actor hero;

    public MapMode(final MapDefinition initialMap, final TileCoordinate initialLocation) {
        this(initialMap, initialLocation, new AssetManager());
    }

    MapMode(final MapDefinition initialMap, final TileCoordinate initialLocation,
            final AssetManager assetManager) {
        this.initialMap = initialMap;
        this.initialLocation = initialLocation;
        this.assetManager = assetManager;
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        this.heroSpriteSet = GameState.getParty().getLeader().getSpriteSetDefinition();
        stateMachine = initStateMachine();
    }

    @Override
    public String getKey() {
        return "mapMode";
    }

    @Override
    public void onEnter(final Map<String, Object> params) {
        MapDefinition mapDefinition = (MapDefinition) params.getOrDefault("map", initialMap);
        TileCoordinate location = (TileCoordinate) params.getOrDefault("location", initialLocation);

        loadMap(mapDefinition);

        hero = new Actor(map, heroSpriteSet.getSpriteSet(assetManager), controller, location);
        if (params.containsKey("facing")) {
            hero.setFacing((Direction) params.get("facing"));
        }

        map.setFocalPoint(hero);
        map.focusCamera();

        if (map.getParentLocation().getBackgroundMusicFilePath() != null) {
            MusicService.playMusic(Gdx.files.internal(map.getParentLocation().getBackgroundMusicFilePath()));
        }
        stateMachine.change("initialPause");
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void update(final long elapsedTime) {
        GameState.incrementTimePLayed(elapsedTime);
        stateMachine.update(elapsedTime);
        GameState.setLocationOnMap(hero.getLocation());
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

    private void loadMap(final MapDefinition mapDefinition) {
        this.map = mapDefinition.getMap(getGame().getGraphicsService(), assetManager);
        GameState.setLocation(map.getParentLocation());
    }

    private MapMode getThis() {
        return this;
    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            private long timeRemainingMs;

            @Override
            public String getKey() {
                return "initialPause";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeRemainingMs = DEFAULT_INITIAL_PAUSE_MS;
            }

            @Override
            public void update(final long elapsedTime) {
                timeRemainingMs -= elapsedTime;
                if (timeRemainingMs <= 0) {
                    controller.flushInput();
                    stateMachine.change("playerInControl");
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndActors(graphicsService);
            }
        });
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "playerInControl";
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (inputService.isPressed(Inputs.PAUSE)) {
                    getGame().exitGame();
                } else if (getGame().hasGameMode("menu") && inputService.isPressed(Inputs.MENU)) {
                    getGame().activateGameMode("menu");
                } else if (hero.isWaiting()) {
                    controller.handleInput(inputService);
                }
            }

            @Override
            public void update(final long elapsedTime) {
                TriggerAction nextTriggeredAction = map.getNextTriggeredAction();
                if (nextTriggeredAction != null) {
                    stateMachine.change("triggerInControl", Collections.singletonMap("action", nextTriggeredAction));
                    return;
                }
                for (Trigger trigger : map.getTriggers()) {
                    if (trigger.isTriggered(hero)) {
                        stateMachine.change(
                                "triggerInControl",
                                Collections.singletonMap("action", trigger.getAction())
                        );
                        return;
                    }
                }
                for (Actor actor : map.getActors()) {
                    actor.update(elapsedTime);
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndActors(graphicsService);
            }
        });
        states.add(new State() {
            private TriggerAction triggerAction;

            @Override
            public String getKey() {
                return "triggerInControl";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                triggerAction = (TriggerAction) params.get("action");
                triggerAction.startAction(getThis());
            }

            @Override
            public void onExit() {
                triggerAction.dispose();
                triggerAction = null;
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndActors(graphicsService);
                triggerAction.render(graphicsService);
            }

            @Override
            public void dispose() {
                triggerAction.dispose();
            }

            @Override
            public void handleInput(final InputService inputService) {
                triggerAction.handleInput(inputService);
            }

            @Override
            public void update(final long elapsedTime) {
                if (triggerAction.isComplete()) {
                    controller.flushInput();
                    stateMachine.change("playerInControl");
                } else {
                    triggerAction.update(elapsedTime);
                }
            }
        });

        return new StateMachine(states, "initialPause");
    }

    private void renderMapAndActors(final GraphicsService graphicsService) {
        map.render(graphicsService);

        if (getGame().isDebug()) {
            OrthographicCamera camera = graphicsService.getCamera();
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
