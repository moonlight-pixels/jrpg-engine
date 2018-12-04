package com.moonlightpixels.jrpg.player

import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import spock.lang.Specification

class CastSpec extends Specification {
    private Location location
    private PlayerCharacter player1
    private PlayerCharacter player2

    void setup() {
        location = new Location(Mock(MapDefinition.Key), new TileCoordinate(1, 1))
        player1 = PlayerCharacter.builder()
            .key(Mock(PlayerCharacter.Key))
            .equipmentSlots([])
            .build()
        player2 = PlayerCharacter.builder()
            .key(Mock(PlayerCharacter.Key))
            .equipmentSlots([])
            .build()
    }

    void 'isValid() checks for an active party'() {
        when:
        Cast cast = new Cast()

        then:
        !cast.isValid()

        when:
        Party party = new Party(1, 1, location)
        party.addMember(player1)
        cast.configureParties(party)

        then:
        cast.isValid()
    }

    void 'configureParties() rejects invalid parties'() {
        setup:
        Cast cast = new Cast()
        Party party1 = new Party(1, 1, location)
        Party party2 = new Party(1, 1, location)

        when:
        cast.configureParties(party1)

        then:
        thrown IllegalArgumentException

        when:
        party1.addMember(player1)
        cast.configureParties(party1)

        then:
        noExceptionThrown()

        when:
        cast.configureParties(party1, party2)

        then:
        thrown IllegalArgumentException

        when:
        party2.addMember(player2)
        cast.configureParties(party1, party2)

        then:
        noExceptionThrown()
    }

    void 'nextParty() activates next party, rolling over if needed'() {
        setup:
        Cast cast = new Cast()
        Party party1 = new Party(1, 1, location)
        party1.addMember(player1)
        Party party2 = new Party(1, 1, location)
        party2.addMember(player2)
        cast.configureParties(party1, party2)

        when:
        cast.nextParty()

        then:
        cast.activeParty == party2

        when:
        cast.nextParty()

        then:
        cast.activeParty == party1
    }

    void 'previousParty() activates previous party, rolling over if needed'() {
        setup:
        Cast cast = new Cast()
        Party party1 = new Party(1, 1, location)
        party1.addMember(player1)
        Party party2 = new Party(1, 1, location)
        party2.addMember(player2)
        cast.configureParties(party1, party2)

        when:
        cast.nextParty()

        then:
        cast.activeParty == party2

        when:
        cast.previousParty()

        then:
        cast.activeParty == party1

        when:
        cast.previousParty()

        then:
        cast.activeParty == party2
    }

    void 'removeFromRoster() leave character in the cast'() {
        setup:
        Cast cast = new Cast()
        cast.addToRoster(player1)

        when:
        cast.removeFromRoster(player1.key)

        then:
        cast.getPlayerCharacter(player1.key).isPresent()
    }

    void 'getUnassignedRoster() returns characters not assigned to a party'() {
        setup:
        Cast cast = new Cast()

        when:
        cast.addToRoster(player1)
        cast.addToRoster(player2)

        then:
        cast.roster == cast.unassignedRoster

        when:
        Party party = new Party(1, 1, location)
        party.addMember(player1)
        cast.configureParties(party)

        then:
        cast.roster.contains(player1)
        !cast.unassignedRoster.contains(player1)
    }
}
