package com.moonlightpixels.jrpg.save.internal.format;

import com.moonlightpixels.jrpg.save.internal.SavedGameState;

public interface SavedGameFormat<T> {
    T toFormat(SavedGameState savedGameState) throws FormatEncodingException;
    SavedGameState fromFormat(T data) throws FormatDecodingException;
}
