package com.github.jaystgelais.jrpg.menu;

public final class SelectItem {
    private final SelectItemRenderer renderer;
    private final SelectListAction action;

    public SelectItem(final SelectItemRenderer renderer, final SelectListAction action) {
        this.renderer = renderer;
        this.action = action;
    }

    public SelectItem(final String label, final SelectListAction action) {
        this(new TextSelectItemRenderer(label), action);
    }

    public SelectItemRenderer getRenderer() {
        return renderer;
    }

    public SelectListAction getAction() {
        return action;
    }
}
