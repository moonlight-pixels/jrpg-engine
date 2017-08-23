package com.github.jaystgelais.jrpg.menu;

public final class SelectItem {
    private final SelectItemRenderer renderer;
    private final SelectListAction action;
    private final SelectListAction onCursorAction;

    public SelectItem(final SelectItemRenderer renderer, final SelectListAction action,
                      final SelectListAction onCursorAction) {
        this.renderer = renderer;
        this.action = action;
        this.onCursorAction = onCursorAction;
    }

    public SelectItem(final SelectItemRenderer renderer, final SelectListAction action) {
        this(renderer, action, null);
    }

    public SelectItem(final String label, final SelectListAction action) {
        this(new TextSelectItemRenderer(label), action);
    }

    public SelectItemRenderer getRenderer() {
        return renderer;
    }

    public void performAction() {
        action.perform();
    }

    public void performOnCursorAction() {
        if (onCursorAction != null) {
            onCursorAction.perform();
        }
    }

}
