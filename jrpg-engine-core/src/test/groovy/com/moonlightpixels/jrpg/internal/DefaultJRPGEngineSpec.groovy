package com.moonlightpixels.jrpg.internal

import com.badlogic.gdx.Application
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.ai.Timepiece
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.moonlightpixels.jrpg.JRPGEngine
import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.config.LaunchConfig
import com.moonlightpixels.jrpg.config.internal.DefaultJRPGConfiguration
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.inject.InjectionContext
import com.moonlightpixels.jrpg.internal.inject.TestModule
import com.moonlightpixels.jrpg.internal.launch.GameLauncher
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory
import spock.lang.Specification

class DefaultJRPGEngineSpec extends Specification {
    private JRPGConfiguration jrpgConfiguration
    private GameLauncher launcher
    private GameLauncherFactory launcherFactory
    private GdxFacade gdx
    private Application application
    private Graphics graphics
    private Input input
    private GdxAIFacade gdxAI
    private Timepiece timepiece
    private AssetManager assetManager
    private TestModule testModule

    void setup() {
        InjectionContext.reset()
        jrpgConfiguration = new DefaultJRPGConfiguration(
            launchConfig: LaunchConfig.builder()
                .resolutionWidth(640)
                .resolutionHeight(480)
                .fullscreen(false)
                .build()
        )
        launcher = Mock(GameLauncher)
        launcherFactory = Mock(GameLauncherFactory) {
            _ * getGameLauncher() >> launcher
        }
        application = Mock(Application)
        graphics = Mock(Graphics)
        input = Mock(Input)
        gdx = Mock(GdxFacade) {
            _ * getApp() >> application
            _ * getGraphics() >> graphics
            _ * getInput() >> input
        }
        timepiece = Mock(Timepiece)
        gdxAI = Mock(GdxAIFacade) {
            _ * getTimepiece() >> timepiece
        }
        assetManager = Mock(AssetManager)
        testModule = new TestModule(
            jrpgConfiguration: jrpgConfiguration,
            launcherFactory: launcherFactory,
            gdx: gdx,
            gdxAI: gdxAI,
            assetManager: assetManager,
            spriteBatch: Mock(SpriteBatch),
            camera: Mock(Camera),
            viewport: Mock(Viewport)
        )
    }

    void 'run() launches the game'() {
        setup:
        JRPGEngine jrpgEngine = new DefaultJRPGEngine(jrpgConfiguration, launcherFactory, gdx, gdxAI, testModule)

        when:
        jrpgEngine.run()
        jrpgEngine.create()

        then:
        1 * launcher.launch(*_)
    }

    void 'create() sets fullscreeen mode if configured to do so'() {
        setup:
        jrpgConfiguration.launchConfig = LaunchConfig.builder()
            .resolutionWidth(640)
            .resolutionHeight(480)
            .fullscreen(true)
            .build()
        DefaultJRPGEngine jrpgEngine = new DefaultJRPGEngine(jrpgConfiguration, launcherFactory, gdx, gdxAI, testModule)

        when:
        jrpgEngine.run()
        jrpgEngine.create()

        then:
        1 * graphics.setFullscreenMode(_)
    }

    void 'render() exits the game if ESC is pressed'() {
        setup:
        DefaultJRPGEngine jrpgEngine = new DefaultJRPGEngine(jrpgConfiguration, launcherFactory, gdx, gdxAI, testModule)
        jrpgEngine.create()

        when:
        jrpgEngine.render()

        then:
        1 * input.isKeyPressed(Input.Keys.ESCAPE) >> true
        1 * application.exit()
    }

    void 'render() updates the GdxAI timepiece'() {
        setup:
        DefaultJRPGEngine jrpgEngine = new DefaultJRPGEngine(jrpgConfiguration, launcherFactory, gdx, gdxAI, testModule)
        jrpgEngine.create()

        when:
        jrpgEngine.render()

        then:
        1 * graphics.getDeltaTime() >> 0.1f
        1 * timepiece.update(0.1f)
    }

    void 'dispose() disposes of assets loaded by AssetManager'() {
        setup:
        DefaultJRPGEngine jrpgEngine = new DefaultJRPGEngine(jrpgConfiguration, launcherFactory, gdx, gdxAI, testModule)
        jrpgEngine.create()

        when:
        jrpgEngine.dispose()

        then:
        1 * assetManager.dispose()
    }
}
