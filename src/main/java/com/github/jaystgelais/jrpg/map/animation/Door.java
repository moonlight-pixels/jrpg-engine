package com.github.jaystgelais.jrpg.map.animation;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.Interactable;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;

import java.util.Arrays;
import java.util.List;

public class Door implements Updatable, Interactable {
    public enum OpenActions { PUSH, INSPECT };

    private static final String STATE_OPEN = "open";
    private static final String STATE_CLOSED = "closed";
    private static final String STATE_OPENNING = "openning";
    private static final String STATE_CLOSING = "closing";

    private final String id;
    private final OpenActions openActions;
    private final DoorLockCheck lockCheck;
    private final List<DoorTile> doorTiles;
    private final StateMachine stateMachine;

    public Door(final String id, final OpenActions openActions, final DoorLockCheck lockCheck,
                final DoorTile... doorTiles) {
        this.id = id;
        this.openActions = openActions;
        this.lockCheck = lockCheck;
        this.doorTiles = Arrays.asList(doorTiles);
        stateMachine = null;
    }

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public void interactWith() {

    }

    public static final class DoorTile {
        private final GameMap map;
        private final String layerName;
        private final TileCoordinate location;
        private final List<TiledMapTile> closedToOpenFrames;

        private DoorTile(final GameMap map, final String layerName, final TileCoordinate location,
                         final TiledMapTile... frames) {
            this.map = map;
            this.layerName = layerName;
            this.location = location;
            closedToOpenFrames = Arrays.asList(frames);
        }

        public void updateTile(final float percentOpen) {
            map.updateTile(location, layerName, getCurrentTile(percentOpen));
        }

        private TiledMapTile getCurrentTile(final float percentOpen) {
            return closedToOpenFrames.get(getKeyFramIndex(percentOpen));
        }

        private int getKeyFramIndex(final float percentOpen) {
            return Math.min(closedToOpenFrames.size() - 1, Math.round(closedToOpenFrames.size() * percentOpen));
        }
    }
}
