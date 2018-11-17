package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.save.internal.SavedParty;
import com.moonlightpixels.jrpg.map.Location;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import com.moonlightpixels.jrpg.map.internal.MapRegistry;
import com.moonlightpixels.jrpg.player.Party;
import com.moonlightpixels.jrpg.player.PlayerCharacter;

import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;

public final class SavedPartyMapper {
    private final MapRegistry mapRegistry;

    @Inject
    public SavedPartyMapper(final MapRegistry mapRegistry) {
        this.mapRegistry = mapRegistry;
    }

    public SavedParty map(final Party party) {
        SavedParty savedParty = new SavedParty();
        savedParty.setMinimumSize(party.getMinimumSize());
        savedParty.setMaximumSize(party.getMaximumSize());
        savedParty.setMembers(
            party.getMembers().stream()
                .map(PlayerCharacter::getKey)
                .map(PlayerCharacter.Key::toString)
                .collect(Collectors.toList())
        );
        savedParty.setMapId(party.getLocation().getMap().getId());
        savedParty.setX(party.getLocation().getTileCoordinate().getX());
        savedParty.setY(party.getLocation().getTileCoordinate().getY());
        savedParty.setMapLayer(party.getLocation().getTileCoordinate().getMapLayer());

        return savedParty;
    }

    public Party map(final SavedParty savedParty, final Map<String, PlayerCharacter> playerCharacterMap) {
        Party party = new Party(
            savedParty.getMinimumSize(),
            savedParty.getMaximumSize(),
            new Location(
                mapRegistry.getMap(savedParty.getMapId()),
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
