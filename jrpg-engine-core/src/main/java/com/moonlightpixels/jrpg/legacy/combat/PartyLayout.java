package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.graphics.Coordinate2D;
import com.moonlightpixels.jrpg.legacy.party.PlayerCharacter;
import com.moonlightpixels.jrpg.legacy.party.Party;

import java.util.Map;

public interface PartyLayout {
    Map<PlayerCharacter, Coordinate2D> getPlayerPositions(Party party);
}
