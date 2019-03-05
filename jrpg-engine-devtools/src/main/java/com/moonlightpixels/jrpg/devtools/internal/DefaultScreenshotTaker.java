package com.moonlightpixels.jrpg.devtools.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.File;
import java.util.Date;

final class DefaultScreenshotTaker implements ScreenshotTaker {

    public void take() {
        take(
            ".jrpg-engine"
                + File.separator
                + "screen-shots"
                + File.separator
                + getTimeStamp()
                + ".png"
        );
    }

    private void take(final String filePath) {
        byte[] pixels = ScreenUtils.getFrameBufferPixels(
            0, 0,
            Gdx.graphics.getBackBufferWidth(),
            Gdx.graphics.getBackBufferHeight(), true
        );

        Pixmap pixmap = new Pixmap(
            Gdx.graphics.getBackBufferWidth(),
            Gdx.graphics.getBackBufferHeight(),
            Pixmap.Format.RGBA8888
        );
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        PixmapIO.writePNG(Gdx.files.external(filePath), pixmap);
        pixmap.dispose();
    }

    private static String getTimeStamp() {
        return new Date().toString();
    }
}
