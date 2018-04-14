package com.moonlightpixels.jrpg.legacy.map.actor;

public final class SingleActionProvider implements ActionProvider {
    private Action action;

    public SingleActionProvider(final Action action) {
        this.action = action;
    }

    @Override
    public Action getNextAction() {
        final Action nextAction = action;
        action = null;
        return nextAction;
    }

    @Override
    public boolean hasNextAction() {
        return action != null;
    }
}
