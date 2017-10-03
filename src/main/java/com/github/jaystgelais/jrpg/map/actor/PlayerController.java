package com.github.jaystgelais.jrpg.map.actor;

import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;

public final class PlayerController implements Controller, InputHandler {
    private Action nextAction;

    private final DelayedInput okInput = new DelayedInput(Inputs.OK);

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public void handleInput(final InputService inputService) {
        if (inputService.isPressed(Inputs.UP)) {
            nextAction = new MoveAction(Direction.UP);
        } else if (inputService.isPressed(Inputs.DOWN)) {
            nextAction = new MoveAction(Direction.DOWN);
        } else if (inputService.isPressed(Inputs.LEFT)) {
            nextAction = new MoveAction(Direction.LEFT);
        } else if (inputService.isPressed(Inputs.RIGHT)) {
            nextAction = new MoveAction(Direction.RIGHT);
        } else if (okInput.isPressed(inputService)) {
            nextAction = new InspectAction();
        } else {
            nextAction = null;
        }
    }

    @Override
    public Action nextAction() {
        Action action = nextAction;
        nextAction = null;

        return action;
    }

    public void flushInput() {
        nextAction = null;
    }
}
