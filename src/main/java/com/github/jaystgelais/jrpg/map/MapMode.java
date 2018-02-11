package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.audio.MusicService;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.DelayedInputService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.Direction;
import com.github.jaystgelais.jrpg.map.actor.PlayerController;
import com.github.jaystgelais.jrpg.map.actor.WalkSpeeds;
import com.github.jaystgelais.jrpg.map.script.Scene;
import com.github.jaystgelais.jrpg.map.trigger.Trigger;
import com.github.jaystgelais.jrpg.map.trigger.TriggerAction;
import com.github.jaystgelais.jrpg.menu.Menu;
import com.github.jaystgelais.jrpg.menu.MenuDefinition;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.google.common.base.Preconditions;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MapMode extends GameMode {
    private static final long DEFAULT_INITIAL_PAUSE_MS = 50;
    private static final int DEBUG_TEXT_X = 10;
    private static final int DEBUB_TEXT_Y = 20;

    private final StateMachine stateMachine;
    private final AssetManager assetManager;
    private final MenuDefinition mainMenuDefinition;
    private final PlayerController controller = new PlayerController();
    private GameMap map;
    private Actor hero;

    @Inject
    public MapMode(final AssetManager assetManager, final MenuDefinition mainMenuDefinition) {
        this.assetManager = assetManager;
        this.mainMenuDefinition = mainMenuDefinition;
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        stateMachine = initStateMachine();
    }

    @Override
    public String getKey() {
        return "mapMode";
    }

    @Override
    public void onEnter(final Map<String, Object> params) {
        MapDefinition mapDefinition = (MapDefinition) params.get("map");
        TileCoordinate location = (TileCoordinate) params.get("location");
        Preconditions.checkNotNull(mapDefinition);
        Preconditions.checkNotNull(location);

        loadMap(mapDefinition);

        hero = new Actor(
                map,
                GameState.getParty().getLeader().getSpriteSetDefinition().getSpriteSet(assetManager),
                controller,
                location,
                WalkSpeeds.FAST
        );
        hero.setIsHero(true);
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
        map.update(elapsedTime);
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
        if (map != null) {
            map.dispose();
        }
        assetManager.dispose();
    }

    public Actor getHero() {
        return hero;
    }

    public void startScene(final Scene scene) {
        stateMachine.change("scene", Collections.singletonMap("scene", scene));
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
        states.add(createPauseState());
        states.add(createPlayerInControlState());
        states.add(createTriggerInControlState());
        states.add(createSceneState());
        states.add(createMenuState());

        return new StateMachine(states, "initialPause");
    }

    private State createMenuState() {
        return new StateAdapter() {
            private Menu menu;

            @Override
            public String getKey() {
                return "menu";
            }

            @Override
            public void update(final long elapsedTime) {
                if (menu.isActive()) {
                    menu.update(elapsedTime);
                } else {
                    stateMachine.change("playerInControl");
                }
            }

            @Override
            public void handleInput(final InputService inputService) {
                menu.handleInput(new DelayedInputService(inputService));
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndEntities(graphicsService);

                graphicsService.renderStart();
                menu.render(graphicsService);
                graphicsService.renderEnd();
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                menu = mainMenuDefinition.getMenu(Game.getInstance().getGraphicsService());
            }

            @Override
            public void onExit() {
                menu = null;
                controller.flushInput();
            }
        };
    }

    private State createSceneState() {
        return new StateAdapter() {
            private Scene scene;
            @Override
            public String getKey() {
                return "scene";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                scene = (Scene) params.get("scene");
                map.getEntities().stream()
                        .filter(Actor.class::isInstance)
                        .map(Actor.class::cast)
                        .forEach(Actor::startScene);
            }

            @Override
            public void onExit() {
                scene = null;
                map.getEntities().stream()
                        .filter(Actor.class::isInstance)
                        .map(Actor.class::cast)
                        .forEach(Actor::endScene);
                controller.flushInput();
            }

            @Override
            public void update(final long elapsedTime) {
                if (scene.isComplete()) {
                    stateMachine.change("playerInControl");
                } else {
                    scene.update(elapsedTime);

                    for (Entity entity : map.getEntities()) {
                        entity.update(elapsedTime);
                    }
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndEntities(graphicsService);
                scene.render(graphicsService);
            }

            @Override
            public void handleInput(final InputService inputService) {
                scene.handleInput(inputService);
            }

            @Override
            public void dispose() {
                scene.dispose();
            }
        };
    }

    private State createTriggerInControlState() {
        return new State() {
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
                renderMapAndEntities(graphicsService);
                triggerAction.render(graphicsService);
                graphicsService.renderUI();
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
                    if (triggerAction.flushInputOnComplete()) {
                        controller.flushInput();
                    }
                    stateMachine.change("playerInControl");
                } else {
                    triggerAction.update(elapsedTime);
                }
            }
        };
    }

    private State createPlayerInControlState() {
        return new StateAdapter() {
            @Override
            public String getKey() {
                return "playerInControl";
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (inputService.isPressed(Inputs.PAUSE)) {
                    getGame().exitGame();
                } else if (inputService.isPressed(Inputs.MENU)) {
                    stateMachine.change("menu");
                } else {
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
                for (Entity entity : map.getEntities()) {
                    entity.update(elapsedTime);
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderMapAndEntities(graphicsService);
            }
        };
    }

    private State createPauseState() {
        return new StateAdapter() {
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
                renderMapAndEntities(graphicsService);
            }
        };
    }

    private void renderMapAndEntities(final GraphicsService graphicsService) {
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
