package com.github.jaystgelais.jrpg.map;

public abstract class Location {
    private WarpTarget exitDestination;
    private String backgroundMusicFilePath;

    public Location() {
        this(null, null);
    }

    public Location(final WarpTarget exitDestination) {
        this(exitDestination, null);
    }

    public Location(final String backgroundMusicFilePath) {
        this(null, backgroundMusicFilePath);
    }

    public Location(final WarpTarget exitDestination, final String backgroundMusicFilePath) {
        this.exitDestination = exitDestination;
        this.backgroundMusicFilePath = backgroundMusicFilePath;
    }

    public abstract String getDescription(TileCoordinate tileCoordinate);

    public final WarpTarget getExitDestination() {
        return exitDestination;
    }

    public final void setExitDestination(final WarpTarget exitDestination) {
        this.exitDestination = exitDestination;
    }

    public final String getBackgroundMusicFilePath() {
        return backgroundMusicFilePath;
    }

    public final void setBackgroundMusicFilePath(final String backgroundMusicFilePath) {
        this.backgroundMusicFilePath = backgroundMusicFilePath;
    }

    public final boolean canExit() {
        return (exitDestination != null);
    }
}
