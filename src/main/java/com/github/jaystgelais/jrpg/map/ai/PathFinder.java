package com.github.jaystgelais.jrpg.map.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.github.jaystgelais.jrpg.map.TileCoordinate;

public final class PathFinder {
    private final CachingIndexedGraph<TileCoordinate> graph;
    private final com.badlogic.gdx.ai.pfa.PathFinder<TileCoordinate> pathFinder;
    private final Heuristic<TileCoordinate> heuristic;

    public PathFinder(final CachingIndexedGraph<TileCoordinate> graph) {
        this.graph = graph;
        pathFinder = new IndexedAStarPathFinder<TileCoordinate>(graph);
        heuristic = new Heuristic<TileCoordinate>() {

            @Override
            public float estimate(final TileCoordinate node, final TileCoordinate endNode) {
                return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
            }
        };
    }

    public TileCoordinate getNextTarget(final TileCoordinate location, final TileCoordinate destination) {
        final GraphPath<Connection<TileCoordinate>> path = new DefaultGraphPath<>();
        pathFinder.searchConnectionPath(
                graph.getCachedNode(location),
                graph.getCachedNode(destination),
                heuristic,
                path
        );

        return path.getCount() == 0 ? null : path.get(0).getToNode();
    }
}
