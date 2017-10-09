package com.github.jaystgelais.jrpg.map.actor;

public final class SceneController implements Controller {
    private ActionProvider actionProvider;

    public ActionProvider getActionProvider() {
        return actionProvider;
    }

    public void setActionProvider(final ActionProvider actionProvider) {
        this.actionProvider = actionProvider;
    }

    @Override
    public Action nextAction() {
        if (actionProvider != null && actionProvider.hasNextAction()) {
            return actionProvider.getNextAction();
        }
        return null;
    }

    @Override
    public void setActor(final Actor actor) {

    }

    @Override
    public void update(final long elapsedTime) {

    }
}
