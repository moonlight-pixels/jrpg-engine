package com.moonlightpixels.jrpg.internal;

import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.map.Location;
import lombok.Data;

@Data
public class DefaultGameState implements GameState {
    private Location location;
}
