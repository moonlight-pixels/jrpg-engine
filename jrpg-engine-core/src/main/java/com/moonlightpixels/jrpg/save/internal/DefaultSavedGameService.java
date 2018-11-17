package com.moonlightpixels.jrpg.save.internal;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.save.SavedGameService;
import com.moonlightpixels.jrpg.save.internal.format.FormatDecodingException;
import com.moonlightpixels.jrpg.save.internal.format.FormatEncodingException;
import com.moonlightpixels.jrpg.save.internal.format.SavedGameFormat;
import com.moonlightpixels.jrpg.save.internal.mapper.SavedGameStateMapper;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

public final class DefaultSavedGameService implements SavedGameService {
    private final Clock clock;
    private final GdxFacade gdx;
    private final SavedGameStateMapper mapper;
    private final SavedGameFormat<byte[]> format;

    public DefaultSavedGameService(final Clock clock,
                                   final GdxFacade gdx,
                                   final SavedGameStateMapper mapper,
                                   final SavedGameFormat<byte[]> format) {
        this.clock = clock;
        this.gdx = gdx;
        this.mapper = mapper;
        this.format = format;
    }

    @Override
    public boolean save(final GameState gameState) {
        String saveId = gameState.getDefaultSaveId().orElseGet(this::generateNewSaveId);
        gameState.setDefaultSaveId(saveId);
        try {
            byte[] bytes = format.toFormat(mapper.map(gameState));
            gdx.getFiles().local(getSavePath(saveId)).writeBytes(bytes, false);
            return true;
        } catch (FormatEncodingException | GdxRuntimeException e) {
            gdx.getApp().error("SaveGame", "Unable to save game", e);
            return false;
        }
    }

    @Override
    public Optional<GameState> load(final String saveId) {
        try {
            byte[] bytes = gdx.getFiles().local(getSavePath(saveId)).readBytes();
            return Optional.ofNullable(mapper.map(format.fromFormat(bytes)));
        } catch (SavedStateLoadExcpetion | FormatDecodingException | GdxRuntimeException e) {
            gdx.getApp().error("LoadGame", "Unable to load game", e);
            return Optional.empty();
        }
    }

    private String generateNewSaveId() {
        return Instant.now(clock).toString().replaceAll("[\\s,:]", "");
    }

    private String getSavePath(final String saveId) {
        return String.format("data/saves/%s.save", saveId);
    }
}
