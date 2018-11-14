package com.moonlightpixels.jrpg.internal

import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.player.Party
import com.moonlightpixels.jrpg.player.PlayerCharacter
import spock.lang.Specification

class DefaultGameStateSpec extends Specification {
    void 'getLocation() returns location of the active party'() {
        setup:
        GameState gameState = new DefaultGameState()
        Location location = new Location(Mock(MapDefinition), new TileCoordinate(1, 1))

        when:
        Party party = new Party(1, 1, location)
        party.addMember(new PlayerCharacter(Mock(PlayerCharacter.Key)))
        gameState.cast.configureParties(party)

        then:
        gameState.location == location
    }

    void 'setLocation() sets the location of the active party'() {
        setup:
        GameState gameState = new DefaultGameState()
        Location location = new Location(Mock(MapDefinition), new TileCoordinate(1, 1))
        Location newLocation = new Location(Mock(MapDefinition), new TileCoordinate(2, 1))
        Party party = new Party(1, 1, location)
        party.addMember(new PlayerCharacter(Mock(PlayerCharacter.Key)))
        gameState.cast.configureParties(party)

        when:
        gameState.setLocation(newLocation)

        then:
        gameState.cast.activeParty.location == newLocation
    }
}
