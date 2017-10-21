package com.github.jaystgelais.jrpg.map.animation;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.Interactable;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.util.TiledUtil;
import com.github.jaystgelais.jrpg.util.TimeUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Door implements Updatable, Interactable {

    public enum OpenActions { PUSH, INSPECT };

    private static final String STATE_OPEN = "open";
    private static final String STATE_CLOSED = "closed";
    private static final String STATE_OPENING = "opening";
    private static final String STATE_CLOSING = "closing";

    private final String id;
    private final OpenActions openAction;
    private final DoorLockCheck lockCheck;
    private final long animationTimeMs;
    private final List<DoorTile> doorTiles;
    private final StateMachine stateMachine;

    public Door(final String id, final OpenActions openAction, final DoorLockCheck lockCheck,
                final long animationTimeMs, final DoorTile... doorTiles) {
        this.id = id;
        this.openAction = openAction;
        this.lockCheck = lockCheck;
        this.animationTimeMs = animationTimeMs;
        this.doorTiles = Arrays.asList(doorTiles);
        stateMachine = createStateMachine();
    }

    public String getId() {
        return id;
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public void interactWith() {
        if (openAction == OpenActions.INSPECT) {
            open();
        }
    }

    public void open() {
        if (isClosed() && !isLocked()) {
            forceOpen();
        }
    }

    public void forceOpen() {
        stateMachine.change(STATE_OPENING);
    }

    public boolean isLocked() {
        return lockCheck.isLocked();
    }

    public boolean isClosed() {
        return stateMachine.getCurrentState().getKey().equals(STATE_CLOSED);
    }

    public void close() {
        if (isOpen()) {
            stateMachine.change(STATE_CLOSING);
        }
    }

    public boolean isOpen() {
        return stateMachine.getCurrentState().getKey().equals(STATE_OPEN);
    }

    public boolean isInteractableWithAt(final TileCoordinate location) {
        for (DoorTile doorTile : doorTiles) {
            if (doorTile.location.equals(location) && doorTile.isBackground()) {
                return true;
            }
        }

        return false;
    }

    public boolean isCollision(final TileCoordinate tileCoordinate) {
        for (DoorTile doorTile : doorTiles) {
            if (isClosed()
                    && doorTile.location.equals(tileCoordinate)
                    && TiledUtil.isBackground(
                            doorTile.map.getTiledMap(), tileCoordinate.getMapLayer(), doorTile.layerName)) {
                return true;
            }
        }

        return false;
    }

    private String getStateFlag() {
        return String.format("door:%s:opened", id);
    }

    private StateMachine createStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new OpenState());
        states.add(new ClosedState());
        states.add(new OpeningState());
        states.add(new ClosingState());

        return new StateMachine(
                states,
                (GameState.checkFlag(getStateFlag(), false)) ? STATE_OPEN : STATE_CLOSED
        );
    }

    public static final class DoorTile {
        private final GameMap map;
        private final String layerName;
        private final TileCoordinate location;
        private final List<TiledMapTile> closedToOpenFrames;

        public DoorTile(final GameMap map, final String layerName, final TileCoordinate location,
                         final TiledMapTile... frames) {
            this.map = map;
            this.layerName = layerName;
            this.location = location;
            closedToOpenFrames = Arrays.asList(frames);
        }

        public void updateTile(final float percentOpen) {
            TiledUtil.updateTile(map.getTiledMap(), location, layerName, getCurrentTile(percentOpen));
        }

        private TiledMapTile getCurrentTile(final float percentOpen) {
            return closedToOpenFrames.get(getKeyFrameIndex(percentOpen));
        }

        private int getKeyFrameIndex(final float percentOpen) {
            return Math.min(closedToOpenFrames.size() - 1, Math.round(closedToOpenFrames.size() * percentOpen));
        }

        public boolean isBackground() {
            return map.isBackgroundLayer(location.getMapLayer(), layerName);
        }
    }

    private final class OpenState extends StateAdapter {

        @Override
        public String getKey() {
            return STATE_OPEN;
        }

        @Override
        public void onEnter(final Map<String, Object> params) {
            doorTiles.forEach(tile -> {
                tile.updateTile(1.0f);
            });
        }
    }

    private final class ClosedState extends StateAdapter {

        @Override
        public String getKey() {
            return STATE_CLOSED;
        }

        @Override
        public void onEnter(final Map<String, Object> params) {
            doorTiles.forEach(tile -> {
                tile.updateTile(0.0f);
            });
        }
    }

    private final class OpeningState extends StateAdapter {
        private long timeInstateMs;

        @Override
        public String getKey() {
            return STATE_OPENING;
        }

        @Override
        public void onEnter(final Map<String, Object> params) {
            timeInstateMs = 0L;
        }

        @Override
        public void update(final long elapsedTime) {
            timeInstateMs += elapsedTime;
            doorTiles.forEach(tile -> {
                tile.updateTile(TimeUtil.calculatePercentComplete(timeInstateMs, animationTimeMs));
            });
            if (timeInstateMs > animationTimeMs) {
                stateMachine.change(STATE_OPEN);
            }
        }
    }

    private final class ClosingState extends StateAdapter {
        private long timeRemainingMs;

        @Override
        public String getKey() {
            return STATE_CLOSING;
        }

        @Override
        public void onEnter(final Map<String, Object> params) {
            timeRemainingMs = animationTimeMs;
        }

        @Override
        public void update(final long elapsedTime) {
            timeRemainingMs -= elapsedTime;
            doorTiles.forEach(tile -> {
                tile.updateTile(TimeUtil.calculatePercentComplete(timeRemainingMs, animationTimeMs));
            });
            if (timeRemainingMs > 0L) {
                stateMachine.change(STATE_CLOSED);
            }
        }
    }
}
