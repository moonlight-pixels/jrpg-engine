package com.moonlightpixels.jrpg.save.internal.format

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.moonlightpixels.jrpg.internal.inject.InjectionContext
import com.moonlightpixels.jrpg.internal.inject.TestModule
import com.moonlightpixels.jrpg.save.internal.GameStateDataProvider
import com.moonlightpixels.jrpg.save.internal.SavedGameState
import spock.lang.Specification

class JSONSavedGameFormatSpec extends Specification {
    void setup() {
        InjectionContext.reset()
        InjectionContext.addModule(new TestModule())
    }

    void 'format can save/load data producing equal values'() {
        setup:
        ObjectMapper objectMapper = new ObjectMapper()
        SavedGameFormat<String> format = new JSONSavedGameFormat(objectMapper)
        SavedGameState savedGameState = new GameStateDataProvider().createSavedGameState()

        expect:
        savedGameState == format.fromFormat(format.toFormat(savedGameState))
    }

    void 'toFormat() throws FormatEncodingException is input cannot be serialized'() {
        setup:
        ObjectMapper objectMapper = Mock(ObjectMapper) {
            writeValueAsString(_) >> { throw Mock(JsonProcessingException) }
        }
        SavedGameFormat<String> format = new JSONSavedGameFormat(objectMapper)
        SavedGameState savedGameState = new GameStateDataProvider().createSavedGameState()

        when:
        format.toFormat(savedGameState)

        then:
        thrown FormatEncodingException
    }

    void 'fromFormat() throws FormatDecodingException is input cannot be deserialized'() {
        setup:
        ObjectMapper objectMapper = Mock(ObjectMapper) {
            readValue(*_) >> { throw Mock(IOException) }
        }
        SavedGameFormat<String> format = new JSONSavedGameFormat(objectMapper)

        when:
        format.fromFormat('some data')

        then:
        thrown FormatDecodingException
    }
}
