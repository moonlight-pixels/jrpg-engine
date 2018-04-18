package com.moonlightpixels.jrpg.internal.gdx;

import com.badlogic.gdx.ai.FileSystem;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.Logger;
import com.badlogic.gdx.ai.Timepiece;

public final class DefaultGdxAIFacade implements GdxAIFacade {
    @Override
    public Timepiece getTimepiece() {
        return GdxAI.getTimepiece();
    }

    @Override
    public Logger getLogger() {
        return GdxAI.getLogger();
    }

    @Override
    public FileSystem getFileSystem() {
        return GdxAI.getFileSystem();
    }
}
