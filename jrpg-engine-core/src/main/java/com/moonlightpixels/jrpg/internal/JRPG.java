package com.moonlightpixels.jrpg.internal;

import com.moonlightpixels.jrpg.map.Location;

public interface JRPG {
    void update();
    void toLocation(Location location);
    void toBattle();
    void toMainMenu();
    void exitBattle();
}
