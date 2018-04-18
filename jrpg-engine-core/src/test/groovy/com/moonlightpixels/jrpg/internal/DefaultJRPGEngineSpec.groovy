package com.moonlightpixels.jrpg.internal

import com.badlogic.gdx.Graphics
import com.google.inject.Guice
import com.google.inject.Injector
import com.moonlightpixels.jrpg.JRPGEngine
import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.config.LaunchConfig
import com.moonlightpixels.jrpg.config.internal.DefaultJRPGConfiguration
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.inject.TestModule
import com.moonlightpixels.jrpg.internal.launch.GameLauncher
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory
import spock.lang.Specification

class DefaultJRPGEngineSpec extends Specification {
    private JRPGConfiguration jrpgConfiguration
    private GameLauncher launcher
    private GameLauncherFactory launcherFactory
    private GdxFacade gdx
    private Graphics graphics
    private Injector injector

    void setup() {
        jrpgConfiguration = new DefaultJRPGConfiguration(
            launchConfig: LaunchConfig.builder()
                .resolutionWidth(640)
                .resolutionHeight(480)
                .fullscreen(false)
                .build()
        )
        launcher = Mock(GameLauncher)
        launcherFactory = Mock(GameLauncherFactory)
        gdx = Mock(GdxFacade)
        graphics = Mock(Graphics)
        injector = Guice.createInjector(
            new TestModule(
                gameLauncherFactory: this.launcherFactory,
                jrpgConfiguration: jrpgConfiguration,
                gdxFacade: gdx
            )
        )
    }

    void 'run() launches the game'() {
        setup:
        JRPGEngine jrpgEngine = new DefaultJRPGEngine(injector)

        when:
        jrpgEngine.run()
        jrpgEngine.create()

        then:
        1 * this.launcherFactory.getGameLauncher() >> this.launcher
        1 * this.launcher.launch(*_)
    }

    void 'create() sets fullscreeen mode if configured to do so'() {
        setup:
        jrpgConfiguration.launchConfig = LaunchConfig.builder()
            .resolutionWidth(640)
            .resolutionHeight(480)
            .fullscreen(true)
            .build()
        DefaultJRPGEngine jrpgEngine = new DefaultJRPGEngine(injector)

        when:
        jrpgEngine.run()
        jrpgEngine.create()

        then:
        1 * launcherFactory.getGameLauncher() >> launcher
        1 * launcher.launch(*_)
        _ * gdx.getGraphics() >> graphics
        1 * graphics.setFullscreenMode(_)
    }
}
