package com.moonlightpixels.jrpg.internal;

import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.map.Location;
import com.moonlightpixels.jrpg.player.Cast;

public final class DefaultGameState implements GameState {
    private final Cast cast = new Cast();

    @Override
    public Location getLocation() {
        return cast.getActiveParty().getLocation();
    }

    @Override
    public void setLocation(final Location location) {
        cast.getActiveParty().setLocation(location);
    }

    @Override
    public Cast getCast() {
        return cast;
    }
}
