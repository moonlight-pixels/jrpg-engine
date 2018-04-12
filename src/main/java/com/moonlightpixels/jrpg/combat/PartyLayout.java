package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.graphics.Coordinate2D;
import com.moonlightpixels.jrpg.party.Party;
import com.moonlightpixels.jrpg.party.PlayerCharacter;

import java.util.Map;

public interface PartyLayout {
    Map<PlayerCharacter, Coordinate2D> getPlayerPositions(Party party);
}
