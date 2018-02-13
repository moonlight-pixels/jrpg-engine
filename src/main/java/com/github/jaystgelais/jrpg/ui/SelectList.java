package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.google.common.base.Preconditions;

import java.util.LinkedList;
import java.util.List;

public final class SelectList extends WidgetGroup implements InputHandler {
    public static final float DEFAULT_SPACING = 0.05f;
    private final List<SelectItem<? extends Widget>> items;
    private final int columns;
    private DelayedInput okInput = new DelayedInput(Inputs.OK);
    private final DelayedInput downInput = new DelayedInput(Inputs.DOWN);
    private final DelayedInput leftInput = new DelayedInput(Inputs.LEFT);
    private final DelayedInput rightInput = new DelayedInput(Inputs.RIGHT);
    private final DelayedInput upInput = new DelayedInput(Inputs.UP);
    private int currentSelectionIndex = 0;
    private Table layout;

    public SelectList(final List<SelectItem<? extends Widget>> items, final int columns) {
        Preconditions.checkArgument(!items.isEmpty());
        Preconditions.checkArgument(columns > 0);
        this.items = new LinkedList<>(items);
        this.columns = columns;
    }

    public SelectList(final List<SelectItem<? extends Widget>> items) {
        this(items, 1);
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (okInput.isPressed(inputService)) {
            items.get(currentSelectionIndex).getAction().perform();
        } else if (rightInput.isPressed(inputService)) {
            currentSelectionIndex = Math.min(currentSelectionIndex + 1, items.size() - 1);
            onSelect(items.get(currentSelectionIndex));
        } else if (downInput.isPressed(inputService)) {
            currentSelectionIndex = Math.min(currentSelectionIndex + columns, items.size() - 1);
            onSelect(items.get(currentSelectionIndex));
        } else if (leftInput.isPressed(inputService)) {
            currentSelectionIndex = Math.max(currentSelectionIndex - 1, 0);
            onSelect(items.get(currentSelectionIndex));
        } else if (upInput.isPressed(inputService)) {
            currentSelectionIndex = Math.max(currentSelectionIndex - columns, 0);
            onSelect(items.get(currentSelectionIndex));
        }
    }

    @Override
    public void layout() {
        final Texture cursorTexture = getCursorSprite();
        final int totalRows = ((items.size() - 1) / columns)  + 1;

        layout = new Table();
        layout.setFillParent(true);
        layout.defaults().space(cursorTexture.getWidth() / 2f);
        addActor(layout);

        for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                final int itemIndex = (rowIndex * columns) + colIndex;
                final SelectItem<? extends Widget> item = items.get(itemIndex);
                final boolean isSelected = (currentSelectionIndex == itemIndex);

                final Image cursor = new Image(cursorTexture);
                cursor.setVisible(isSelected);
                layout.add(cursor).padLeft(cursorTexture.getWidth());

                layout.add(item.getDisplay()).fillX().expand();
            }
            layout.row();
        }
    }

    @Override
    public float getPrefWidth() {
        return (layout != null) ? layout.getPrefWidth() : 0;
    }

    @Override
    public float getPrefHeight() {
        return (layout != null) ? layout.getPrefHeight() : 0;
    }

    private void onSelect(final SelectItem<? extends Widget> selectItem) {
        selectItem.getOnCursorAction().ifPresent(SelectItem.Action::perform);
        clear();
        layout();
    }

    private Texture getCursorSprite() {
        final GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        if (!graphicsService.getAssetManager().isLoaded("assets/jrpg/panel/cursor.png", Texture.class)) {
            graphicsService.getAssetManager().load("assets/jrpg/panel/cursor.png", Texture.class);
            graphicsService.getAssetManager().finishLoading();
        }

        return graphicsService.getAssetManager().get("assets/jrpg/panel/cursor.png", Texture.class);
    }
}
