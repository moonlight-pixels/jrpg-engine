package com.moonlightpixels.jrpg.internal.inject

import com.badlogic.gdx.Application
import com.badlogic.gdx.Audio
import com.badlogic.gdx.Files
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.Net
import com.badlogic.gdx.ai.FileSystem
import com.badlogic.gdx.ai.Logger
import com.badlogic.gdx.ai.Timepiece
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.moonlightpixels.jrpg.combat.CombatState
import com.moonlightpixels.jrpg.combat.stats.BaseStat
import com.moonlightpixels.jrpg.combat.stats.RequiredStats
import com.moonlightpixels.jrpg.combat.stats.StatSystem
import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.config.LaunchConfig
import com.moonlightpixels.jrpg.config.internal.DefaultJRPGConfiguration
import com.moonlightpixels.jrpg.frontend.internal.FrontEndState
import com.moonlightpixels.jrpg.internal.DefaultJRPG
import com.moonlightpixels.jrpg.internal.JRPG
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.launch.GameLauncher
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory
import com.moonlightpixels.jrpg.map.internal.MapState
import org.spockframework.mock.MockUtil
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import javax.inject.Named
import javax.inject.Singleton

class TestModule extends AbstractModule {
    JRPGConfiguration jrpgConfiguration
    GameLauncher launcher
    GameLauncherFactory launcherFactory
    GdxFacade gdx
    Application gdxApplication
    Graphics gdxGraphics
    Audio gdxAudio
    Input gdxInput
    Files gdxFiles
    Net gdxNet
    GL20 gdxGl20
    GL30 gdxGl30
    GdxAIFacade gdxAI
    Timepiece gdxaiTimepiece
    Logger gdxaiLogger
    FileSystem gdxaiFileSystem
    AssetManager assetManager
    SpriteBatch spriteBatch
    Camera camera
    Viewport viewport
    JRPG jrpg
    FrontEndState frontEndState
    MapState mapState
    CombatState combatState

    TestModule() {
        DetachedMockFactory mockFactory = new DetachedMockFactory()
        jrpgConfiguration = new DefaultJRPGConfiguration(
            launchConfig: LaunchConfig.builder()
                .resolutionWidth(640)
                .resolutionHeight(480)
                .fullscreen(false)
                .build()
        )
        launcher = mockFactory.Mock(GameLauncher)
        launcherFactory = new GameLauncherFactory() {
            @Override
            GameLauncher getGameLauncher() {
                return launcher
            }
        }
        gdxApplication = mockFactory.Mock(Application)
        gdxGraphics = mockFactory.Mock(Graphics)
        gdxAudio = mockFactory.Mock(Audio)
        gdxInput = mockFactory.Mock(Input)
        gdxFiles = mockFactory.Mock(Files)
        gdxNet = mockFactory.Mock(Net)
        gdxGl20 = mockFactory.Mock(GL20)
        gdxGl30 = mockFactory.Mock(GL30)
        gdx = new GdxFacade() {
            @Override
            Application getApp() {
                return gdxApplication
            }

            @Override
            Graphics getGraphics() {
                return gdxGraphics
            }

            @Override
            Audio getAudio() {
                return gdxAudio
            }

            @Override
            Input getInput() {
                return gdxInput
            }

            @Override
            Files getFiles() {
                return gdxFiles
            }

            @Override
            Net getNet() {
                return gdxNet
            }

            @Override
            GL20 getGl() {
                return gdxGl20
            }

            @Override
            GL20 getGl20() {
                return gdxGl20
            }

            @Override
            GL30 getGl30() {
                return gdxGl30
            }
        }
        gdxaiTimepiece = mockFactory.Mock(Timepiece)
        gdxaiLogger = mockFactory.Mock(Logger)
        gdxaiFileSystem = mockFactory.Mock(FileSystem)
        gdxAI = new GdxAIFacade() {
            @Override
            Timepiece getTimepiece() {
                return gdxaiTimepiece
            }

            @Override
            Logger getLogger() {
                return gdxaiLogger
            }

            @Override
            FileSystem getFileSystem() {
                return gdxaiFileSystem
            }
        }
        assetManager = mockFactory.Mock(AssetManager)
        spriteBatch = mockFactory.Mock(SpriteBatch)
        camera = mockFactory.Mock(Camera)
        viewport = mockFactory.Mock(Viewport)
        jrpg = mockFactory.Mock(JRPG)
        frontEndState = mockFactory.Mock(FrontEndState)
        mapState = mockFactory.Mock(MapState)
        combatState = mockFactory.Mock(CombatState)
    }

    void attach(Specification spec) {
        MockUtil mockUtil = new MockUtil()
        mockUtil.attachMock(launcher, spec)
        mockUtil.attachMock(gdxApplication, spec)
        mockUtil.attachMock(gdxGraphics, spec)
        mockUtil.attachMock(gdxAudio, spec)
        mockUtil.attachMock(gdxInput, spec)
        mockUtil.attachMock(gdxFiles, spec)
        mockUtil.attachMock(gdxNet, spec)
        mockUtil.attachMock(gdxGl20, spec)
        mockUtil.attachMock(gdxGl30, spec)
        mockUtil.attachMock(gdxaiTimepiece, spec)
        mockUtil.attachMock(gdxaiLogger, spec)
        mockUtil.attachMock(gdxaiFileSystem, spec)
        mockUtil.attachMock(assetManager, spec)
        mockUtil.attachMock(spriteBatch, spec)
        mockUtil.attachMock(camera, spec)
        mockUtil.attachMock(viewport, spec)
        mockUtil.attachMock(jrpg, spec)
        mockUtil.attachMock(frontEndState, spec)
        mockUtil.attachMock(mapState, spec)
        mockUtil.attachMock(combatState, spec)
    }

    void detach() {
        MockUtil mockUtil = new MockUtil()
        mockUtil.detachMock(launcher)
        mockUtil.detachMock(gdxApplication)
        mockUtil.detachMock(gdxGraphics)
        mockUtil.detachMock(gdxAudio)
        mockUtil.detachMock(gdxInput)
        mockUtil.detachMock(gdxFiles)
        mockUtil.detachMock(gdxNet)
        mockUtil.detachMock(gdxGl20)
        mockUtil.detachMock(gdxGl30)
        mockUtil.detachMock(gdxaiTimepiece)
        mockUtil.detachMock(gdxaiLogger)
        mockUtil.detachMock(gdxaiFileSystem)
        mockUtil.detachMock(assetManager)
        mockUtil.detachMock(spriteBatch)
        mockUtil.detachMock(camera)
        mockUtil.detachMock(viewport)
        mockUtil.detachMock(jrpg)
        mockUtil.detachMock(frontEndState)
        mockUtil.detachMock(mapState)
        mockUtil.detachMock(combatState)
    }

    @Override
    protected void configure() {
        bind(JRPGConfiguration).toInstance(jrpgConfiguration)
        bind(GameLauncherFactory).toInstance(launcherFactory)
        bind(GdxFacade).toInstance(gdx)
        bind(GdxAIFacade).toInstance(gdxAI)
        bind(AssetManager).toInstance(assetManager)
        bind(SpriteBatch).toInstance(spriteBatch)
        bind(Camera).toInstance(camera)
        bind(Viewport).toInstance(viewport)
        bind(JRPG).toInstance(jrpg)
        bind(FrontEndState).toInstance(frontEndState)
        bind(MapState).toInstance(mapState)
        bind(CombatState).toInstance(combatState)
    }

    @Provides
    @Named('initial')
    State<DefaultJRPG> provideInitialState() {
        return frontEndState
    }

    @Provides
    @Singleton
    StatSystem provideStatSystem() {
        StatSystem statSystem = new StatSystem()
        statSystem.addStat(BaseStat.builder()
            .key(RequiredStats.MaxHP)
            .name('Max HP')
            .shortName('HPM')
            .build()
        )
        statSystem.addStat(BaseStat.builder()
            .key(RequiredStats.Level)
            .name('Level')
            .shortName('LVL')
            .build()
        )
        statSystem.addStat(BaseStat.builder()
            .key(RequiredStats.CombatTurnInterval)
            .name('Combat Turn Interval')
            .shortName('CTI')
            .build()
        )

        return statSystem
    }
}
