package com.moonlightpixels.jrpg.internal.gdx;

import com.badlogic.gdx.ai.FileSystem;
import com.badlogic.gdx.ai.Logger;
import com.badlogic.gdx.ai.Timepiece;

public interface GdxAIFacade {
    Timepiece getTimepiece();
    Logger getLogger();
    FileSystem getFileSystem();
}
