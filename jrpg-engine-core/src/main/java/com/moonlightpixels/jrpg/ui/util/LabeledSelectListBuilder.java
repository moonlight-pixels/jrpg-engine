package com.moonlightpixels.jrpg.ui.util;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.moonlightpixels.jrpg.ui.SelectList;
import com.moonlightpixels.jrpg.ui.UiStyle;

import java.util.LinkedList;
import java.util.List;

/**
 * Builder utiltity for making SelectLists with text lable items.
 */
public final class LabeledSelectListBuilder {
    /**
     * Style name used by this builder. Style will be applied to SelectList and labels.
     */
    public static final String STYLE = "LabeledSelectList";

    private final List<SelectList.Item> items = new LinkedList<>();
    private final UiStyle uiStyle;
    private int columns = 1;

    /**
     * Costructs a new LabeledSelectListBuilder.
     *
     * @param uiStyle Provided UI Styles.
     */
    public LabeledSelectListBuilder(final UiStyle uiStyle) {
        this.uiStyle = uiStyle;
    }

    /**
     * Adds an item to the SelectList that will be built.
     *
     * @param labelText Text of this item's label
     * @param action action tied to this item
     * @return reference to this builder
     */
    public LabeledSelectListBuilder addItem(final String labelText, final SelectList.Action action) {
        final Label label = uiStyle.createLabel(labelText, STYLE);
        items.add(new SelectList.Item(label, action));

        return this;
    }

    /**
     * Set the number of the SelectList that will be built.
     *
     * @param columns number of columns
     * @return reference to this builder
     */
    public LabeledSelectListBuilder setColumns(final int columns) {
        this.columns = columns;

        return this;
    }

    /**
     * Builds SelectList.
     *
     * @return newly constructed SelectList
     */
    public SelectList build() {
        return new SelectList(items, uiStyle.get(STYLE, SelectList.SelectListStyle.class), columns);
    }
}
