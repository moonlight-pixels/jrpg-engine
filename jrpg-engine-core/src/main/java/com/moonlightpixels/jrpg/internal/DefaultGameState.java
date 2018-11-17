package com.moonlightpixels.jrpg.internal;

import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.map.Location;
import com.moonlightpixels.jrpg.player.Cast;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class DefaultGameState implements GameState {
    private final Cast cast = new Cast();
    private String defaultSaveId;

    @Override
    public Optional<String> getDefaultSaveId() {
        return Optional.ofNullable(defaultSaveId);
    }

    @Override
    public void setDefaultSaveId(final String saveId) {
        this.defaultSaveId = saveId;
    }

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

    @Override
    public boolean isValid() {
        return cast.isValid();
    }
}
