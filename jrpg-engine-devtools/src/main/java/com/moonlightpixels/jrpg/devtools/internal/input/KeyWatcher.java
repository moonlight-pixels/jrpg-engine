package com.moonlightpixels.jrpg.devtools.internal.input;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;

public final class KeyWatcher {
    private final GdxFacade gdx;
    private final int key;
    private final StateMachine<KeyWatcher, KeyWatcherState> stateStateMachine = new DefaultStateMachine<>(
        this,
        KeyWatcherState.Initial
    );
    private boolean beenPressed = false;

    public KeyWatcher(final int key) {
        this(new DefaultGdxFacade(), key);
    }

    KeyWatcher(final GdxFacade gdx, final int key) {
        this.gdx = gdx;
        this.key = key;
    }

    public boolean hasBeenPressed() {
        stateStateMachine.update();

        return beenPressed;
    }

    private boolean isPressed() {
        return gdx.getInput().isKeyPressed(key);
    }

    private enum KeyWatcherState implements State<KeyWatcher> {
        Initial {
            @Override
            public void update(final KeyWatcher entity) {
                if (entity.isPressed()) {
                    entity.stateStateMachine.changeState(KeyPressed);
                }
            }
        },
        KeyPressed {
            @Override
            public void update(final KeyWatcher entity) {
                if (!entity.isPressed()) {
                    entity.stateStateMachine.changeState(UnProcessedPress);
                }
            }
        },
        UnProcessedPress {
            @Override
            public void enter(final KeyWatcher entity) {
                entity.beenPressed = true;
            }

            @Override
            public void update(final KeyWatcher entity) {
                entity.stateStateMachine.changeState(Initial);
            }

            @Override
            public void exit(final KeyWatcher entity) {
                entity.beenPressed = false;
            }
        };

        @Override
        public void enter(final KeyWatcher entity) {

        }

        @Override
        public void exit(final KeyWatcher entity) {

        }

        @Override
        public boolean onMessage(final KeyWatcher entity, final Telegram telegram) {
            return false;
        }
    }
}
