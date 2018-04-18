package com.moonlightpixels.jrpg.internal.gdx

import com.badlogic.gdx.ai.GdxAI
import spock.lang.Specification

class DefaultGdxAIFacadeSpec extends Specification {
    void 'DefaultGdxAIFacadeSpec delegates to GdxAI'() {
        expect:
        GdxAIFacade gdxAI = new DefaultGdxAIFacade()
        gdxAI.fileSystem == GdxAI.fileSystem
        gdxAI.logger == GdxAI.logger
        gdxAI.timepiece == GdxAI.timepiece
    }
}
