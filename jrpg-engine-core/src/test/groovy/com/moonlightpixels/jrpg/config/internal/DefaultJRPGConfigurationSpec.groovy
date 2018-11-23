package com.moonlightpixels.jrpg.config.internal

import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.config.ContentRegistry
import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.config.LaunchConfig
import com.moonlightpixels.jrpg.frontend.FrontEndConfig
import com.moonlightpixels.jrpg.ui.UiStyle
import com.moonlightpixels.jrpg.ui.standard.MenuConfiguration
import spock.lang.Specification

import java.util.function.Consumer

class DefaultJRPGConfigurationSpec extends Specification {
    void 'validate passes if LaunchConfig is set'() {
        expect:
        JRPGConfiguration configuration = new DefaultJRPGConfiguration()
        configuration.setLaunchConfig(LaunchConfig.builder()
            .resolutionWidth(640)
            .resolutionHeight(480)
            .fullscreen(true)
            .build())
        configuration.validate()
    }

    void 'validate throws IllegalStateException if LaunchConfig is NOT set'() {
        when:
        new DefaultJRPGConfiguration().validate()

        then:
        thrown IllegalStateException
    }

    void 'configure(UiStyle) can handle not having a configuration consumer'() {
        when:
        new DefaultJRPGConfiguration().configure(null as UiStyle)

        then:
        noExceptionThrown()
    }

    void 'configure(UiStyle) calls configuration consumer when set'() {
        setup:
        UiStyle uiStyle = Mock()
        Consumer<UiStyle> consumer = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.uiStyle(consumer)
        jrpgConfiguration.configure(uiStyle)

        then:
        1 * consumer.accept(uiStyle)
    }

    void 'configure(UiStyle) calls configuration consumers in order when more than one is set'() {
        setup:
        UiStyle uiStyle = Mock()
        Consumer<UiStyle> consumer1 = Mock()
        Consumer<UiStyle> consumer2 = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.uiStyle(consumer1)
        jrpgConfiguration.uiStyle(consumer2)
        jrpgConfiguration.configure(uiStyle)

        then:
        1 * consumer1.accept(uiStyle)

        then:
        1 * consumer2.accept(uiStyle)
    }

    void 'configure(FrontEndConfig) calls configuration consumer when set'() {
        setup:
        FrontEndConfig frontEnd = Mock()
        Consumer<FrontEndConfig> consumer = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.frontEnd(consumer)
        jrpgConfiguration.configure(frontEnd)

        then:
        1 * consumer.accept(frontEnd)
    }

    void 'configure(FrontEndConfig) calls configuration consumers in order when more than one is set'() {
        setup:
        FrontEndConfig frontEnd = Mock()
        Consumer<FrontEndConfig> consumer1 = Mock()
        Consumer<FrontEndConfig> consumer2 = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.frontEnd(consumer1)
        jrpgConfiguration.frontEnd(consumer2)
        jrpgConfiguration.configure(frontEnd)

        then:
        1 * consumer1.accept(frontEnd)

        then:
        1 * consumer2.accept(frontEnd)
    }

    void 'configure(MenuConfiguration) calls configuration consumer when set'() {
        setup:
        MenuConfiguration menu = Mock()
        Consumer<MenuConfiguration> consumer = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.menu(consumer)
        jrpgConfiguration.configure(menu)

        then:
        1 * consumer.accept(menu)
    }

    void 'configure(MenuConfiguration) calls configuration consumers in order when more than one is set'() {
        setup:
        MenuConfiguration menu = Mock()
        Consumer<MenuConfiguration> consumer1 = Mock()
        Consumer<MenuConfiguration> consumer2 = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.menu(consumer1)
        jrpgConfiguration.menu(consumer2)
        jrpgConfiguration.configure(menu)

        then:
        1 * consumer1.accept(menu)

        then:
        1 * consumer2.accept(menu)
    }

    void 'configureNewGame(GameState) calls configuration consumer when set'() {
        setup:
        GameState gameState = Mock()
        Consumer<GameState> consumer = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.newGame(consumer)
        jrpgConfiguration.configureNewGame(gameState)

        then:
        1 * consumer.accept(gameState)
    }

    void 'configureNewGame(GameState) calls configuration consumers in order when more than one is set'() {
        setup:
        GameState gameState = Mock()
        Consumer<GameState> consumer1 = Mock()
        Consumer<GameState> consumer2 = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.newGame(consumer1)
        jrpgConfiguration.newGame(consumer2)
        jrpgConfiguration.configureNewGame(gameState)

        then:
        1 * consumer1.accept(gameState)

        then:
        1 * consumer2.accept(gameState)
    }

    void 'configureNewGame(ContentRegistry) calls configuration consumer when set'() {
        setup:
        ContentRegistry contentRegistry = Mock()
        Consumer<ContentRegistry> consumer = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.content(consumer)
        jrpgConfiguration.configureContent(contentRegistry)

        then:
        1 * consumer.accept(contentRegistry)
    }

    void 'configureNewGame(ContentRegistry) calls configuration consumers in order when more than one is set'() {
        setup:
        ContentRegistry contentRegistry = Mock()
        Consumer<ContentRegistry> consumer1 = Mock()
        Consumer<ContentRegistry> consumer2 = Mock()

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.content(consumer1)
        jrpgConfiguration.content(consumer2)
        jrpgConfiguration.configureContent(contentRegistry)

        then:
        1 * consumer1.accept(contentRegistry)

        then:
        1 * consumer2.accept(contentRegistry)
    }
}
