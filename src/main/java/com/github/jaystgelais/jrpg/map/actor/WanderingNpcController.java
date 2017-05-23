package com.github.jaystgelais.jrpg.map.actor;

import com.github.jaystgelais.jrpg.util.OddmentTable;

public final class WanderingNpcController implements Controller {
    public static final int DEFAULT_NPC_PAUSE_TIME_MS = 750;
    private Action lastAction;
    private final OddmentTable<Action> decisionTable;

    @SuppressWarnings("checkstyle:magicnumber")
    public WanderingNpcController() {
        decisionTable = new OddmentTable<>();
        decisionTable.addRow(11, new RepeatLastAction());
        decisionTable.addRow(1, new MoveAction(Direction.UP));
        decisionTable.addRow(1, new MoveAction(Direction.DOWN));
        decisionTable.addRow(1, new MoveAction(Direction.LEFT));
        decisionTable.addRow(1, new MoveAction(Direction.RIGHT));
        decisionTable.addRow(3, new WaitAction());
    }

    @Override
    public void update(final long elapsedTime) {
        if (lastAction instanceof WaitAction) {
            ((WaitAction) lastAction).waitMs -= elapsedTime;
        }
    }

    @Override
    public Action nextAction() {
        if ((lastAction instanceof WaitAction) && ((WaitAction) lastAction).waitMs > 0) {
            return null;
        }
        Action nextAction = decisionTable.getValue();
        if (!(nextAction instanceof RepeatLastAction)) {
            lastAction = nextAction;
        }
        return nextAction;
    }

    private class RepeatLastAction implements Action {

        @Override
        public void perform(final Actor actor) {
            if (lastAction != null) {
                lastAction.perform(actor);
            }
        }
    }

    private static class WaitAction implements Action {
        private long waitMs = DEFAULT_NPC_PAUSE_TIME_MS;

        @Override
        public void perform(final Actor actor) {

        }
    }
}
