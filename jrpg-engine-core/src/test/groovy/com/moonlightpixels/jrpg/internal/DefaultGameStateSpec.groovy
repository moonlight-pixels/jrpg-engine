package com.moonlightpixels.jrpg.internal

import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.player.Party
import com.moonlightpixels.jrpg.player.PlayerCharacter
import spock.lang.Specification

import java.time.Duration

class DefaultGameStateSpec extends Specification {
    private PlayerCharacter playerCharacter

    void setup() {
        playerCharacter = PlayerCharacter.builder()
            .key(Mock(PlayerCharacter.Key))
            .build()
    }

    void 'getLocation() returns location of the active party'() {
        setup:
        GameState gameState = new DefaultGameState()
        Location location = new Location(Mock(MapDefinition.Key), new TileCoordinate(1, 1))

        when:
        Party party = new Party(1, 1, location)
        party.addMember(playerCharacter)
        gameState.cast.configureParties(party)

        then:
        gameState.location == location
    }

    void 'setLocation() sets the location of the active party'() {
        setup:
        GameState gameState = new DefaultGameState()
        Location location = new Location(Mock(MapDefinition.Key), new TileCoordinate(1, 1))
        Location newLocation = new Location(Mock(MapDefinition.Key), new TileCoordinate(2, 1))
        Party party = new Party(1, 1, location)
        party.addMember(playerCharacter)
        gameState.cast.configureParties(party)

        when:
        gameState.setLocation(newLocation)

        then:
        gameState.cast.activeParty.location == newLocation
    }

    void 'isValid() returns true if all validaiton requirements have been met'() {
        setup:
        GameState gameState = new DefaultGameState()
        Location location = new Location(Mock(MapDefinition.Key), new TileCoordinate(1, 1))
        Party party = new Party(1, 1, location)
        party.addMember(playerCharacter)
        gameState.cast.configureParties(party)

        expect:
        gameState.isValid()
    }

    void 'isValid() returns false if active party hasnt been configured'() {
        expect:
        !new DefaultGameState().isValid()
    }

    void 'update() increments the time played'() {
        setup:
        GameState gameState = new DefaultGameState()

        when:
        gameState.update(0.15f)

        then:
        gameState.timePlayed == Duration.ofMillis(150)
    }
}
