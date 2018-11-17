package com.moonlightpixels.jrpg.save.internal.mapper

import com.moonlightpixels.jrpg.player.PlayerCharacter
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion
import spock.lang.Specification

class PlayerCharacterKeyEnumLoaderSpec extends Specification {

    void 'Can load enum value from ID'() {
        setup:
        PlayerCharacterKeyLoader loader = new PlayerCharacterKeyEnumLoader(Players)

        when:
        PlayerCharacter.Key key = loader.load(Players.GALUF.toString())

        then:
        key == Players.GALUF
    }

    void 'Throws SavedStateLoadExcpetion if key doesnt exist in enum'() {
        setup:
        PlayerCharacterKeyLoader loader = new PlayerCharacterKeyEnumLoader(Players)

        when:
        loader.load('KRILE')

        then:
        thrown SavedStateLoadExcpetion
    }

    static enum Players implements PlayerCharacter.Key {
        BARTZ,
        LENNA,
        FARIS,
        GALUF,
        CARA
    }
}
