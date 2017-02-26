package com.github.jaystgelais.jrpg

import com.github.jaystgelais.jrpg.graphics.GraphicsService
import com.github.jaystgelais.jrpg.input.InputService
import com.github.jaystgelais.jrpg.state.State
import spock.lang.Specification

import java.time.Clock

class GameSpec extends Specification {
    void 'render() calls update and render correctly on the active game mode'() {
        setup:
        GameMode gameMode = Mock(GameMode) {
            _ * getKey() >> 'key1'
        }
        GraphicsService graphicsService = Mock(GraphicsService)
        InputService inputService = Mock(InputService)

        when:
        Game game = new Game([gameMode] as Set<State>, gameMode.key, graphicsService, inputService)
        game.render()

        then:
        1 * gameMode.update(_)
        1 * gameMode.render(graphicsService)
    }

    void 'Game correctly measures time elapsed after pause'() {
        setup:
        Clock mockClock = Mock(Clock) {
            5 * millis() >>> [1, 2, 3, 10, 11]
        }
        GameMode gameMode = Mock(GameMode) {
            _ * getKey() >> 'key1'
        }
        GraphicsService graphicsService = Mock(GraphicsService)
        InputService inputService = Mock(InputService)

        when:
        Game game = new Game([gameMode] as Set<GameMode>, gameMode.key, graphicsService, inputService, mockClock)
        game.render()
        game.pause()
        game.resume()
        game.render()

        then:
        1 * gameMode.update(1)
        1 * gameMode.update(2)
    }

    void 'Game correctly measures time elapsed'() {
        setup:
        Clock mockClock = Mock(Clock) {
            2 * millis() >>> [1, 2]
        }
        GameMode gameMode = Mock(GameMode) {
            _ * getKey() >> 'key1'
        }
        GraphicsService graphicsService = Mock(GraphicsService)
        InputService inputService = Mock(InputService)

        when:
        Game game = new Game([gameMode] as Set<GameMode>, gameMode.key, graphicsService, inputService, mockClock)
        game.render()

        then:
        1 * gameMode.update(1)
        1 * gameMode.handleInput(inputService)
        1 * gameMode.render(graphicsService)
    }

    void 'calls to dispose propegate to all states'() {
        setup:
        GameMode gameMode1 = Mock(GameMode) {
            _ * getKey() >> 'key1'
        }
        GameMode gameMode2 = Mock(GameMode) {
            _ * getKey() >> 'key2'
        }
        GraphicsService graphicsService = Mock(GraphicsService)
        InputService inputService = Mock(InputService)
        Game game = new Game([gameMode1, gameMode2] as Set<GameMode>, gameMode1.key, graphicsService, inputService)

        when:
        game.dispose()

        then:
        1 * gameMode1.dispose()
        1 * gameMode2.dispose()
    }

    void 'Game is available to its GameModes'() {
        setup:
        GameMode gameMode = new GameMode() {
            String key = 'key'

            @Override
            void update(long elapsedTime) {
                game.graphicsService.resolutionHeight
            }
        }
        GraphicsService graphicsService = Mock(GraphicsService)
        InputService inputService = Mock(InputService)
        Game game = new Game([gameMode] as Set<GameMode>, gameMode.key, graphicsService, inputService)

        when:
        game.render()

        then:
        1 * graphicsService.getResolutionHeight()
    }

    void 'GraphicsService is initialized on start of Game'() {
        setup:
        GameMode gameMode = Mock(GameMode) {
            _ * getKey() >> 'key1'
        }
        GraphicsService graphicsService = Mock(GraphicsService)
        InputService inputService = Mock(InputService)
        Game game = new Game([gameMode] as Set<GameMode>, gameMode.key, graphicsService, inputService)

        when:
        game.create()

        then:
        1 * graphicsService.init()
    }

    void 'calls to resize() propegate to GraphicsService'() {
        setup:
        GameMode gameMode = Mock(GameMode) {
            _ * getKey() >> 'key1'
        }
        GraphicsService graphicsService = Mock(GraphicsService)
        InputService inputService = Mock(InputService)
        Game game = new Game([gameMode] as Set<GameMode>, gameMode.key, graphicsService, inputService)

        when:
        game.resize(100, 200)

        then:
        1 * graphicsService.resize(100, 200)
    }
}
