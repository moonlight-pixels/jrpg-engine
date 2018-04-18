package com.moonlightpixels.jrpg.internal.gdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

public final class DefaultGdxFacade implements GdxFacade {
    @Override
    public Application getApp() {
        return Gdx.app;
    }

    @Override
    public Graphics getGraphics() {
        return Gdx.graphics;
    }

    @Override
    public Audio getAudio() {
        return Gdx.audio;
    }

    @Override
    public Input getInput() {
        return Gdx.input;
    }

    @Override
    public Files getFiles() {
        return Gdx.files;
    }

    @Override
    public Net getNet() {
        return Gdx.net;
    }

    @Override
    public GL20 getGl() {
        return Gdx.gl;
    }

    @Override
    public GL20 getGl20() {
        return Gdx.gl20;
    }

    @Override
    public GL30 getGl30() {
        return Gdx.gl30;
    }
}
