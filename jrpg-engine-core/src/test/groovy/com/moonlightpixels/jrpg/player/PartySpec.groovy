package com.moonlightpixels.jrpg.player

import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import spock.lang.Specification

class PartySpec extends Specification {
    private Location location
    private PlayerCharacter player1
    private PlayerCharacter player2

    void setup() {
        location = new Location(Mock(MapDefinition.Key), new TileCoordinate(1, 1))
        player1 = PlayerCharacter.builder().key(Mock(PlayerCharacter.Key)).build()
        player2 = PlayerCharacter.builder().key(Mock(PlayerCharacter.Key)).build()
    }

    void 'attempting to create party with minimum size < 1 throws IllegalArgumentException'() {
        when:
        new Party(0, 0, location)

        then:
        thrown IllegalArgumentException
    }

    void 'attempting to create party with maximum size < minimum size throws IllegalArgumentException'() {
        when:
        new Party(1, 0, location)

        then:
        thrown IllegalArgumentException
    }

    void 'attempting to create party with a null location throws NullPointerException'() {
        when:
        new Party(1, 1, null)

        then:
        thrown NullPointerException
    }

    void 'isValid() ensure that party meets minimum size'() {
        when:
        Party party = new Party(1, 2, location)

        then:
        !party.isValid()

        when:
        party.addMember(player1)

        then:
        party.isValid()
    }

    void 'addMember() will not add the same member twice'() {
        setup:
        Party party = new Party(1, 2, location)

        when:
        party.addMember(player1)
        party.addMember(player1)

        then:
        party.members.size() == 1
    }

    void 'addMember() throws IllegalStateException if it would exceen maximum size'() {
        setup:
        Party party = new Party(1, 1, location)

        when:
        party.addMember(player1)
        party.addMember(player2)

        then:
        thrown IllegalStateException
    }

    void 'removeMember() throws NullPointerException if memeber is null'() {
        setup:
        Party party = new Party(1, 1, location)

        when:
        party.removeMember(null)

        then:
        thrown NullPointerException
    }

    void 'removeMember() removed member from party'() {
        setup:
        Party party = new Party(1, 2, location)
        party.addMember(player1)
        party.addMember(player2)

        when:
        party.removeMember(player1)

        then:
        !party.members.contains(player1)
    }

    void 'swap() swaps character positions'() {
        setup:
        Party party = new Party(1, 2, location)

        when:
        party.addMember(player1)
        party.addMember(player2)

        then:
        party.members[0] == player1
        party.members[1] == player2

        when:
        party.swap(player1, player2)

        then:
        party.members[0] == player2
        party.members[1] == player1
    }
}
