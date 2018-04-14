package com.moonlightpixels.jrpg.legacy.map.actor;

import com.moonlightpixels.jrpg.legacy.input.DelayedInput;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.input.Inputs;

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

    @Override
    public void setActor(final Actor actor) {

    }

    public void flushInput() {
        nextAction = null;
        okInput.delay();
    }
}
