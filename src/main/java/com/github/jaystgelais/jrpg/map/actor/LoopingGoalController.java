package com.github.jaystgelais.jrpg.map.actor;

import com.github.jaystgelais.jrpg.map.ai.Goal;

public final class LoopingGoalController implements Controller {
    private final Goal[] goals;
    private int currentGoalIndex = 0;
    private Actor actor;

    public LoopingGoalController(final Goal... goals) {
        this.goals = goals;
    }

    @Override
    public Action nextAction() {
        final Action nextAction = getCurrentGoal().nextAction(actor);
        return nextAction;
    }

    @Override
    public void update(final long elapsedTime) {
        getCurrentGoal().update(elapsedTime);
        if (getCurrentGoal().isComplete(actor)) {
            currentGoalIndex = (currentGoalIndex + 1) % goals.length;
        }
    }

    @Override
    public void setActor(final Actor actor) {
        this.actor = actor;
    }

    private Goal getCurrentGoal() {
        return goals[currentGoalIndex];
    }
}
