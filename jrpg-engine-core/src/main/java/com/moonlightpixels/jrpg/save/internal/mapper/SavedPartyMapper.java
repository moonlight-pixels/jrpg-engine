package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.map.Location;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import com.moonlightpixels.jrpg.player.Party;
import com.moonlightpixels.jrpg.player.PlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedParty;

import java.util.Map;
import java.util.stream.Collectors;

public final class SavedPartyMapper {

    public SavedParty map(final Party party) {
        SavedParty savedParty = new SavedParty();
        savedParty.setMinimumSize(party.getMinimumSize());
        savedParty.setMaximumSize(party.getMaximumSize());
        savedParty.setMembers(
            party.getMembers().stream()
                .map(PlayerCharacter::getKey)
                .collect(Collectors.toList())
        );
        savedParty.setMapKey(party.getLocation().getMap());
        savedParty.setX(party.getLocation().getTileCoordinate().getX());
        savedParty.setY(party.getLocation().getTileCoordinate().getY());
        savedParty.setMapLayer(party.getLocation().getTileCoordinate().getMapLayer());

        return savedParty;
    }

    public Party map(final SavedParty savedParty, final Map<PlayerCharacter.Key, PlayerCharacter> playerCharacterMap) {
        Party party = new Party(
            savedParty.getMinimumSize(),
            savedParty.getMaximumSize(),
            new Location(
                savedParty.getMapKey(),
                new TileCoordinate(
                    savedParty.getX(),
                    savedParty.getY(),
                    savedParty.getMapLayer()
                )
            )
        );
        savedParty.getMembers().forEach(key -> party.addMember(playerCharacterMap.get(key)));

        return party;
    }
}
