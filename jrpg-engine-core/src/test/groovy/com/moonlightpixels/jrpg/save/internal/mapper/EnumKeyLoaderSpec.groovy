package com.moonlightpixels.jrpg.save.internal.mapper

import com.moonlightpixels.jrpg.player.PlayerCharacter
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion
import spock.lang.Specification

class EnumKeyLoaderSpec extends Specification {
    KeyLoader<PlayerCharacter.Key> loader

    void setup() {
        loader = new EnumKeyLoader<PlayerCharacter.Key>(Players, PlayerCharacter.Key)
    }

    void 'Can load enum value from ID'() {
        when:
        PlayerCharacter.Key key = loader.load(Players.GALUF.toString())

        then:
        key == Players.GALUF
    }

    void 'Throws SavedStateLoadExcpetion if key doesnt exist in enum'() {
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
