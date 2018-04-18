package com.moonlightpixels.jrpg.internal.gdx

import com.badlogic.gdx.Gdx
import spock.lang.Specification

class DefaultGdxFacadeSpec extends Specification {
    void 'DefaultGdxFacadeSpec delegates to Gdx'() {
        expect:
        GdxFacade gdx = new DefaultGdxFacade()
        gdx.app == Gdx.app
        gdx.graphics == Gdx.graphics
        gdx.audio == Gdx.audio
        gdx.input == Gdx.input
        gdx.files == Gdx.files
        gdx.net == Gdx.net
        gdx.gl == Gdx.gl
        gdx.gl20 == Gdx.gl20
        gdx.gl30 == Gdx.gl30
    }
}
