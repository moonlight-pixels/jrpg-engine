package com.github.jaystgelais.jrpg.menu;

public final class SelectItem {
    private final String label;
    private final SelectListAction action;

    public SelectItem(final String label, final SelectListAction action) {
        this.label = label;
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public SelectListAction getAction() {
        return action;
    }
}
