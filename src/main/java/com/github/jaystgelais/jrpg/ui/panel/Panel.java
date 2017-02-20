package com.github.jaystgelais.jrpg.ui.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jgelais on 2/15/17.
 */
public class Panel implements Renderable, PanelContainer {
    public static final int BORDER_THICKNESS = 3;
    public static final int CORNER_HEIGHT = 3;
    public static final int CORNER_WIDTH = 3;
    public static final int CONTENT_OFFSET_X = 8;
    public static final int CONTENT_OFFSET_Y = 8;

    private final PanelPalette palette;
    private final int width;
    private final int height;
    private float positionX = 0;
    private float positionY = 0;
    private final Pixmap pixmap;
    private List<PanelContent> children;

    public Panel(final PanelPalette palette, final int width, final int height) {
        this.palette = palette;
        this.width = width;
        this.height = height;
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        children = new LinkedList<>();
    }

    public final void add(final PanelContent content) {
        children.add(content);
    }


    @Override
    public final void render(final GraphicsService graphicsService) {
        drawTileFromArray(getBottomLeftCorner(), CORNER_WIDTH, CORNER_HEIGHT, 0,         0);
        drawTileFromArray(getBottomRightCorner(), CORNER_WIDTH, CORNER_HEIGHT, width - (CORNER_WIDTH + 1), 0);
        drawTileFromArray(getTopLeftCorner(), CORNER_WIDTH, CORNER_HEIGHT, 0,         height - (CORNER_HEIGHT + 1));
        drawTileFromArray(
                getTopRightCorner(),
                CORNER_WIDTH, CORNER_HEIGHT,
                width - (CORNER_WIDTH + 1), height - (CORNER_HEIGHT + 1)
        );

        Color[] topBorder = getTopBorder();
        Color[] bottomBorder = getBottomBorder();
        for (int x = BORDER_THICKNESS; x < width - (BORDER_THICKNESS + 1); x++) {
            drawTileFromArray(topBorder,    1, BORDER_THICKNESS, x, 0);
            drawTileFromArray(bottomBorder, 1, BORDER_THICKNESS, x, height - (BORDER_THICKNESS + 1));
        }

        Color[] leftBorder = getLeftBorder();
        Color[] rightBorder = getRightBorder();
        for (int y = BORDER_THICKNESS; y < height - (BORDER_THICKNESS + 1); y++) {
            drawTileFromArray(leftBorder, BORDER_THICKNESS, 1, 0,         y);
            drawTileFromArray(rightBorder, BORDER_THICKNESS, 1, width - (BORDER_THICKNESS + 1), y);
        }

        pixmap.setColor(palette.getBgPrimary());
        pixmap.fillRectangle(
                BORDER_THICKNESS, BORDER_THICKNESS,
                width - (BORDER_THICKNESS * 2), height - (BORDER_THICKNESS * 2)
        );

        graphicsService.drawSprite(pixmap, positionX, positionY);
        children.forEach(
                panelContent -> panelContent.render(graphicsService)
        );
    }

    private void drawTileFromArray(final Color[] tile,
                                   final int width, final int height,
                                   final int posX, final int posY) {
        int colorIndex = 0;
        for (int y = posY; y < posY + height; y++) {
            for (int x = posX; x < posX + width; x++) {
                Color color = tile[colorIndex++];
                if (color != null) {
                    pixmap.drawPixel(x, y, color.toIntBits());
                }
            }
        }
    }

    private Color[] getTopLeftCorner() {
        return new Color[]{
                palette.getBorderEdgeMedium(), palette.getBorderPrimary(),    palette.getBorderEdgeDark(),
                null,                          palette.getBorderEdgeMedium(), palette.getBorderPrimary(),
                null,                          null,                          palette.getBorderEdgeDark()
        };
    }

    private Color[] getTopBorder() {
        return new Color[]{
                palette.getBorderEdgeLight(),
                palette.getBorderPrimary(),
                palette.getBorderEdgeDark()
        };
    }

    private Color[] getTopRightCorner() {
        return new Color[]{
                palette.getBorderPrimary(),    palette.getBorderEdgeMedium(), palette.getBorderEdgeDark(),
                palette.getBorderEdgeLight(),  palette.getBorderEdgeDark(),   null,
                palette.getBorderEdgeDark(),   null,                          null
        };
    }

    private Color[] getLeftBorder() {
        return new Color[]{
                palette.getBorderEdgeMedium(), palette.getBorderPrimary(), palette.getBorderEdgeDark()
        };
    }

    private Color[] getRightBorder() {
        return new Color[]{
                palette.getBorderEdgeMedium(), palette.getBorderPrimary(), palette.getBorderEdgeDark()
        };
    }

    private Color[] getBottomLeftCorner() {
        return new Color[]{
                null,                          null,                          palette.getBorderEdgeLight(),
                null,                          palette.getBorderEdgeMedium(), palette.getBorderPrimary(),
                palette.getBorderEdgeMedium(), palette.getBorderPrimary(),    palette.getBorderEdgeMedium()
        };
    }

    private Color[] getBottomBorder() {
        return new Color[]{
                palette.getBorderEdgeLight(),
                palette.getBorderPrimary(),
                palette.getBorderEdgeDark()
        };
    }

    private Color[] getBottomRightCorner() {
        return new Color[]{
                palette.getBorderEdgeDark(),   null,                          null,
                palette.getBorderEdgeMedium(), palette.getBorderEdgeDark(),   null,
                palette.getBorderPrimary(),    palette.getBorderEdgeMedium(), palette.getBorderEdgeDark()
        };
    }

    public final PanelPalette getPalette() {
        return palette;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final float getPositionX() {
        return positionX;
    }

    public final void setPositionX(final float positionX) {
        this.positionX = positionX;
    }

    public final float getPositionY() {
        return positionY;
    }

    public final void setPositionY(final float positionY) {
        this.positionY = positionY;
    }

    @Override
    public final void dispose() {
        pixmap.dispose();
    }

    @Override
    public final float getContainerPositionX() {
        return positionX + CONTENT_OFFSET_X;
    }

    @Override
    public final float getContainerPositionY() {
        return positionY + CONTENT_OFFSET_Y;
    }

    @Override
    public final float getContainerWidth() {
        return width - (2 * CONTENT_OFFSET_X);
    }

    @Override
    public final float getContainerHeight() {
        return height - (2 * CONTENT_OFFSET_Y);
    }
}
