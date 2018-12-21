package com.moonlightpixels.jrpg.ui.standard.action

import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.config.internal.ConfigurationHandler
import com.moonlightpixels.jrpg.internal.GameStateHolder
import com.moonlightpixels.jrpg.internal.JRPG
import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.player.Party
import com.moonlightpixels.jrpg.player.PlayerCharacter
import spock.lang.Specification

class NewGameActionSpec extends Specification {
    void 'perform() configured a new Game State and starts game'() {
        setup:
        JRPG jrpg = Mock()
        GameStateHolder gameStateHolder = new GameStateHolder()
        ConfigurationHandler configurationHandler = Mock()
        NewGameAction action = new NewGameAction(jrpg, gameStateHolder, configurationHandler)

        when:
        action.perform()

        then:
        1 * configurationHandler.configureNewGame(_) >> { args ->
            GameState gameState = args[0] as GameState
            Party party = new Party(1, 1, new Location(Mock(MapDefinition.Key), new TileCoordinate(1, 1)))
            party.addMember(Mock(PlayerCharacter))
            gameState.cast.configureParties(party)
        }
        1 * jrpg.toMap()
    }
}
