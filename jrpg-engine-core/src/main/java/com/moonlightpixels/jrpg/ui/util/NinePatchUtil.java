package com.moonlightpixels.jrpg.ui.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.g2d.NinePatch.BOTTOM_CENTER;
import static com.badlogic.gdx.graphics.g2d.NinePatch.BOTTOM_LEFT;
import static com.badlogic.gdx.graphics.g2d.NinePatch.BOTTOM_RIGHT;
import static com.badlogic.gdx.graphics.g2d.NinePatch.MIDDLE_CENTER;
import static com.badlogic.gdx.graphics.g2d.NinePatch.MIDDLE_LEFT;
import static com.badlogic.gdx.graphics.g2d.NinePatch.MIDDLE_RIGHT;
import static com.badlogic.gdx.graphics.g2d.NinePatch.TOP_CENTER;
import static com.badlogic.gdx.graphics.g2d.NinePatch.TOP_LEFT;
import static com.badlogic.gdx.graphics.g2d.NinePatch.TOP_RIGHT;

/**
 * Utility class for producing special cases of Ninepatches.
 */
public final class NinePatchUtil {
    private static final int NINEPATCH_PATCHCOUNT = 9;

    private NinePatchUtil() { }

    /**
     * Creates a Ninepatch with a gradient blending technique applied to the center patch.
     *
     * @param texture Texture representing teh complete Ninepatch graphic.
     * @param left Pixels from left edge.
     * @param right Pixels from right edge.
     * @param top Pixels from top edge.
     * @param bottom Pixels from bottom edge.
     *
     * @return Ninepatch with a gradient blending technique applied to the center patch
     */
    public static NinePatch createGradientNinePatch(final Texture texture, final int left, final int right,
                                                    final int top, final int bottom) {
        final TextureRegion[] patches = getPatches(texture, left, right, top, bottom);
        final TextureRegion centerPatch = patches[MIDDLE_CENTER];
        final float halfTexelHeight = 1 / (float) (texture.getHeight() * 2);
        final float halfTexelWidth = 1 / (float) (texture.getWidth() * 2);

        centerPatch.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        centerPatch.setU(centerPatch.getU() + halfTexelWidth);
        centerPatch.setU2(centerPatch.getU2() - halfTexelWidth);
        centerPatch.setV(centerPatch.getV() + halfTexelHeight);
        centerPatch.setV2(centerPatch.getV2() - halfTexelHeight);

        return new NinePatch(patches);
    }

    private static TextureRegion[] getPatches(final Texture texture, final int left, final int right,
                                              final int top, final int bottom) {
        final TextureRegion[] patches = new TextureRegion[NINEPATCH_PATCHCOUNT];
        final int height = texture.getHeight();
        final int width = texture.getWidth();
        final int centerHeight = height - (top + bottom);
        final int centerWidth = width - (left + right);

        patches[TOP_LEFT] = new TextureRegion(texture, 0, height - top, left, top);
        patches[TOP_CENTER] = new TextureRegion(texture, left, height - top, centerWidth, top);
        patches[TOP_RIGHT] = new TextureRegion(texture, width - right, height - top, right, top);
        patches[MIDDLE_LEFT] = new TextureRegion(texture, 0, bottom, left, centerHeight);
        patches[MIDDLE_CENTER] = new TextureRegion(texture, left, bottom, centerWidth, centerHeight);
        patches[MIDDLE_RIGHT] = new TextureRegion(texture, width - right, right, left, centerHeight);
        patches[BOTTOM_LEFT] = new TextureRegion(texture, 0, 0, left, bottom);
        patches[BOTTOM_CENTER] = new TextureRegion(texture, left, 0, centerWidth, bottom);
        patches[BOTTOM_RIGHT] = new TextureRegion(texture, width - right, 0, right, bottom);

        return patches;
    }
}
