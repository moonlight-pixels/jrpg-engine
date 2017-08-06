package com.github.jaystgelais.jrpg.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.Container;

import java.util.List;

public class SelectList extends AbstractContent {

    public static final int COLUMN_MARGIN = 32;
    public static final int ROW_MARGIN = 10;

    private final List<SelectItem> items;
    private final int columns;
    private final DelayedInput okInput = new DelayedInput(Inputs.OK);
    private final DelayedInput downInput = new DelayedInput(Inputs.DOWN);
    private final DelayedInput leftInput = new DelayedInput(Inputs.LEFT);
    private final DelayedInput rightInput = new DelayedInput(Inputs.RIGHT);
    private final DelayedInput upInput = new DelayedInput(Inputs.UP);
    private int currentSelectionIndex = 0;
    private Texture cursorSprite;

    public SelectList(final Container parent, final List<SelectItem> items, final int columns) {
        super(
                parent.getContentPositionX(), parent.getContentPositionY(),
                parent.getContentWidth(), parent.getContentHeight()
        );
        this.items = items;
        this.columns = columns;
    }

    public SelectList(final Container parent, final List<SelectItem> items) {
        this(parent, items, 1);
    }

    @Override
    public void update(long elapsedTime) {

    }

    @Override
    public void handleInput(InputService inputService) {
        if (okInput.isPressed(inputService)) {
            items.get(currentSelectionIndex).getAction().perform();
        } else if (rightInput.isPressed(inputService)) {
            currentSelectionIndex = Math.min(currentSelectionIndex + 1, items.size() - 1);
        } else if (downInput.isPressed(inputService)) {
            currentSelectionIndex = Math.min(currentSelectionIndex + columns, items.size() - 1);
        } else if (leftInput.isPressed(inputService)) {
            currentSelectionIndex = Math.max(currentSelectionIndex - 1, 0);
        } else if (upInput.isPressed(inputService)) {
            currentSelectionIndex = Math.max(currentSelectionIndex - columns, 0);
        }
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        int totalRows = ((items.size() - 1) / columns)  + 1;
        int columnWidth = (getWidth() - (COLUMN_MARGIN * (columns + 1))) / columns;
        int rowHeight = (int) graphicsService.getFontSet().getTextFont().getLineHeight() + ROW_MARGIN;
        int visibleRows = getHeight() / rowHeight;
        int currentRow = currentSelectionIndex / columns;
        int firstRow = Math.max(
                0,
                Math.min(
                        totalRows - visibleRows,
                        currentRow - (visibleRows / 2)
                )
        );

//        System.out.printf("  Total Rows: %d\n", totalRows);
//        System.out.printf("  Visible Rows: %d\n", visibleRows);
//        System.out.printf("  Current Rows: %d\n", currentRow);
//        System.out.printf("  First Row: %d\n", firstRow);

        for (int rowIndex = firstRow; rowIndex < firstRow + Math.min(visibleRows, totalRows); rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                final int itemIndex = (rowIndex * columns) + colIndex;
                final int labelX = getScreenPositionX() + graphicsService.getCameraOffsetX() + COLUMN_MARGIN + (colIndex * (columnWidth + COLUMN_MARGIN));
                final int labelY = getScreenPositionY() + graphicsService.getCameraOffsetY() + getHeight() - (rowIndex * rowHeight);
                graphicsService.getFontSet().getTextFont().draw(
                        graphicsService.getSpriteBatch(),
                        items.get(itemIndex).getLabel(),
                        labelX,
                        labelY,
                        columnWidth,
                        Align.left,
                        false
                );
                if (itemIndex == currentSelectionIndex) {
                    Texture cursor = getCursorSprite(graphicsService);
                    graphicsService.drawSprite(cursor, labelX - 32, labelY - 12);
                }
            }
        }
    }

    @Override
    protected boolean canChangeMargins() {
        return false;
    }

    @Override
    public void dispose() {

    }

    private Texture getCursorSprite(final GraphicsService graphicsService) {
        if (cursorSprite == null) {
            if (!graphicsService.getAssetManager().isLoaded("assets/jrpg/panel/cursor.png", Texture.class)) {
                graphicsService.getAssetManager().load("assets/jrpg/panel/cursor.png", Texture.class);
                graphicsService.getAssetManager().finishLoading();
            }
            cursorSprite = graphicsService.getAssetManager().get("assets/jrpg/panel/cursor.png", Texture.class);
        }

        return cursorSprite;
    }
}
