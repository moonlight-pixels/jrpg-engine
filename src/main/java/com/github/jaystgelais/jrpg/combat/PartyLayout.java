package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.graphics.Coordinate2D;
import com.github.jaystgelais.jrpg.party.Party;
import com.github.jaystgelais.jrpg.party.PlayerCharacter;

import java.util.Map;

public interface PartyLayout {
    Map<PlayerCharacter, Coordinate2D> getPlayerPositions(Party party);
}
