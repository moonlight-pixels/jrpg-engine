package com.moonlightpixels.jrpg.internal.gdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

public interface GdxFacade {
    Application getApp();
    Graphics getGraphics();
    Audio getAudio();
    Input getInput();
    Files getFiles();
    Net getNet();

    GL20 getGl();
    GL20 getGl20();
    GL30 getGl30();
}
