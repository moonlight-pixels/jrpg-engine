package com.moonlightpixels.jrpg

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.graphics.GL20
import spock.lang.Specification

class GdxSpecification extends Specification {
    private Application application

    void setup() {
        application = new HeadlessApplication(new ApplicationAdapter() { })

        Gdx.gl20 = Mock(GL20)
        Gdx.gl = Gdx.gl20
    }

    void cleanup() {
        application.exit()
        application = null
    }
}
