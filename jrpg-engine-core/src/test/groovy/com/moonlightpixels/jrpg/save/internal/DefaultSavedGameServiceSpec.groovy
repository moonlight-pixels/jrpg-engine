package com.moonlightpixels.jrpg.save.internal

import com.badlogic.gdx.Application
import com.badlogic.gdx.Files
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.GdxRuntimeException
import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.inject.InjectionContext
import com.moonlightpixels.jrpg.internal.inject.TestModule
import com.moonlightpixels.jrpg.save.SavedGameService
import com.moonlightpixels.jrpg.save.internal.format.FormatEncodingException
import com.moonlightpixels.jrpg.save.internal.format.SavedGameFormat
import spock.lang.Specification

import java.time.Clock
import java.time.Instant

class DefaultSavedGameServiceSpec extends Specification {
    SavedGameService savedGameService
    Clock clock
    GdxFacade gdx
    Files files
    Application app
    GameStateDataProvider gameStateDataProvider
    SavedGameFormat<byte[]> format
    GameState gameState
    FileHandle fileHandle

    void setup() {
        InjectionContext.reset()
        InjectionContext.addModule(new TestModule())
        clock = Mock()
        fileHandle = Mock()
        files = Mock(Files) {
            local('data/saves/mySaveId.save') >> fileHandle
        }
        app = Mock()
        gdx = Mock(GdxFacade) {
            getFiles() >> files
            getApp() >> app
        }
        gameStateDataProvider = new GameStateDataProvider()
        format = Mock()
        savedGameService = new DefaultSavedGameService(clock, gdx, gameStateDataProvider.mapper, format)
        gameState = gameStateDataProvider.createGameState()
    }

    void 'save() writes format output to the correct file'() {
        setup:
        byte[] bytes = 'sampledata'.bytes
        format.toFormat(_) >> bytes

        when:
        boolean success = savedGameService.save(gameState)

        then:
        success
        1 * fileHandle.writeBytes(bytes, false)
    }

    void 'save() returns false and logs an error it any throwable is thrown'() {
        setup:
        format.toFormat(_) >> { throw new FormatEncodingException('', null) }

        when:
        boolean success = savedGameService.save(gameState)

        then:
        !success
        1 * app.error(*_)
    }

    void 'save() getnerates a new saveId basedon the current time if one doesnt exist'() {
        setup:
        clock.instant() >> Instant.EPOCH
        gameState.setDefaultSaveId(null)
        files.local(_) >> Mock(FileHandle)

        when:
        savedGameService.save(gameState)

        then:
        gameState.defaultSaveId.get() == '1970-01-01T000000Z'
    }

    void 'load() reads savd data form the correct format'() {
        setup:
        byte[] bytes = 'sampledata'.bytes
        fileHandle.readBytes() >> bytes
        format.fromFormat(bytes) >> gameStateDataProvider.createSavedGameState()

        when:
        boolean success = savedGameService.load(gameState.defaultSaveId.get()).isPresent()

        then:
        success
    }

    void 'load() returns an empty Optional and logs an error if it cant load file'() {
        setup:
        fileHandle.readBytes() >> { throw Mock(GdxRuntimeException) }

        when:
        boolean success = savedGameService.load(gameState.defaultSaveId.get()).isPresent()

        then:
        !success
        1 * app.error(*_)
    }
}

