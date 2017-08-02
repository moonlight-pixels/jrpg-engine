package com.github.jaystgelais.jrpg.map;

public abstract class Location {
    private WarpTarget exitDestination;
    private String backgroundMusicFilePath;

    public Location() {
        this(null, null);
    }

    public Location(WarpTarget exitDestination) {
        this(exitDestination, null);
    }

    public Location(String backgroundMusicFilePath) {
        this(null, backgroundMusicFilePath);
    }

    public Location(WarpTarget exitDestination, String backgroundMusicFilePath) {
        this.exitDestination = exitDestination;
        this.backgroundMusicFilePath = backgroundMusicFilePath;
    }

    public abstract String getDescription(TileCoordinate tileCoordinate);

    public WarpTarget getExitDestination() {
        return exitDestination;
    }

    public void setExitDestination(WarpTarget exitDestination) {
        this.exitDestination = exitDestination;
    }

    public String getBackgroundMusicFilePath() {
        return backgroundMusicFilePath;
    }

    public void setBackgroundMusicFilePath(String backgroundMusicFilePath) {
        this.backgroundMusicFilePath = backgroundMusicFilePath;
    }

    public boolean canExit() {
        return (exitDestination != null);
    }
}
