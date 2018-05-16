package com.moonlightpixels.jrpg.internal

import com.badlogic.gdx.Input
import com.moonlightpixels.jrpg.config.LaunchConfig
import com.moonlightpixels.jrpg.internal.inject.GameInitializer
import com.moonlightpixels.jrpg.internal.inject.GraphicsInitializer
import com.moonlightpixels.jrpg.internal.inject.InjectionContext
import com.moonlightpixels.jrpg.internal.inject.TestModule
import spock.lang.Specification

class DefaultJRPGEngineSpec extends Specification {
    private final TestModule testModule = new TestModule()
    private DefaultJRPGEngine jrpgEngine

    void setup() {
        InjectionContext.reset()
        testModule.attach(this)
        InjectionContext.addModule(testModule)
        jrpgEngine = new DefaultJRPGEngine(
            testModule.jrpgConfiguration,
            testModule.launcherFactory,
            testModule.gdx,
            testModule.gdxAI,
            Mock(GraphicsInitializer),
            Mock(GameInitializer)
        )
    }

    void cleanup() {
        testModule.detach()
    }

    void 'run() launches the game'() {
        when:
        jrpgEngine.run()
        jrpgEngine.create()

        then:
        1 * testModule.launcher.launch(*_)
    }

    void 'create() sets fullscreeen mode if configured to do so'() {
        setup:
        testModule.jrpgConfiguration.launchConfig = LaunchConfig.builder()
            .resolutionWidth(640)
            .resolutionHeight(480)
            .fullscreen(true)
            .build()

        when:
        jrpgEngine.run()
        jrpgEngine.create()

        then:
        1 * testModule.gdxGraphics.setFullscreenMode(_)
    }

    void 'render() exits the game if ESC is pressed'() {
        setup:
        jrpgEngine.create()

        when:
        jrpgEngine.render()

        then:
        1 * testModule.gdxInput.isKeyPressed(Input.Keys.ESCAPE) >> true
        1 * testModule.gdxApplication.exit()
    }

    void 'render() updates the GdxAI timepiece'() {
        setup:
        jrpgEngine.create()

        when:
        jrpgEngine.render()

        then:
        1 * testModule.gdxGraphics.getDeltaTime() >> 0.1f
        1 * testModule.gdxaiTimepiece.update(0.1f)
    }

    void 'dispose() disposes of assets loaded by AssetManager'() {
        setup:
        jrpgEngine.create()

        when:
        jrpgEngine.dispose()

        then:
        1 * testModule.assetManager.dispose()
    }
}
