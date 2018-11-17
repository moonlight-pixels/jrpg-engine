package com.moonlightpixels.jrpg.save.internal.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonlightpixels.jrpg.save.internal.SavedGameState;

import javax.inject.Inject;
import java.io.IOException;

public final class JSONSavedGameFormat implements SavedGameFormat<String> {
    private final ObjectMapper objectMapper;

    @Inject
    public JSONSavedGameFormat(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String toFormat(final SavedGameState savedGameState) throws FormatEncodingException {
        try {
            return objectMapper.writeValueAsString(savedGameState);
        } catch (JsonProcessingException e) {
            throw new FormatEncodingException(
                "Unable to produce JSON from SavedGameState",
                e
            );
        }
    }

    @Override
    public SavedGameState fromFormat(final String data) throws FormatDecodingException {
        try {
            return objectMapper.readValue(data, SavedGameState.class);
        } catch (IOException e) {
            throw new FormatDecodingException(
                String.format("Unable to decode JSON: %s", data),
                e
            );
        }
    }
}
