package com.moonlightpixels.jrpg.map.character.internal;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import com.moonlightpixels.jrpg.map.character.CharacterActor;
import com.moonlightpixels.jrpg.ui.InputHandler;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PlayerInputCharacterController implements CharacterController, InputHandler {
    private static final Map<ControlEvent, ControllerState> GLOBAL_TRANSITIONS = Stream.of(
        new AbstractMap.SimpleImmutableEntry<>(ControlEvent.UP_PRESSED, ControllerState.Up),
        new AbstractMap.SimpleImmutableEntry<>(ControlEvent.LEFT_PRESSED, ControllerState.Left),
        new AbstractMap.SimpleImmutableEntry<>(ControlEvent.RIGHT_PRESSED, ControllerState.Right),
        new AbstractMap.SimpleImmutableEntry<>(ControlEvent.DOWN_PRESSED, ControllerState.Down)
    )
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private final StateMachine<PlayerInputCharacterController, ControllerState> stateMachine;

    public PlayerInputCharacterController() {
        stateMachine = new DefaultStateMachine<>(this, ControllerState.Open);
    }

    @Override
    public CharacterCommand getNextCommand(final CharacterActor characterActor) {
        return stateMachine.getCurrentState().getNextCommand(characterActor);
    }

    @Override
    public boolean handleControlEvent(final ControlEvent event) {
        if (GLOBAL_TRANSITIONS.containsKey(event)) {
            stateMachine.changeState(GLOBAL_TRANSITIONS.get(event));
            return true;
        }

        return stateMachine.getCurrentState().handleControlEvent(this, event);
    }

    @Override
    public boolean handleClickEvent(final ClickEvent event) {
        return false;
    }

    @Override
    public void setInputScheme(final InputScheme inputScheme) { }

    private enum ControllerState implements State<PlayerInputCharacterController>, CharacterController {
        Open {
            @Override
            public CharacterCommand getNextCommand(final CharacterActor characterActor) {
                return CharacterCommand.Stand;
            }
        },
        Up {
            @Override
            public CharacterCommand getNextCommand(final CharacterActor characterActor) {
                return CharacterCommand.WalkUp;
            }

            @Override
            public boolean handleControlEvent(final PlayerInputCharacterController entity, final ControlEvent event) {
                if (event == ControlEvent.UP_RELEASED) {
                    entity.stateMachine.changeState(Open);
                }
                return true;
            }
        },
        Left {
            @Override
            public CharacterCommand getNextCommand(final CharacterActor characterActor) {
                return CharacterCommand.WalkLeft;
            }

            @Override
            public boolean handleControlEvent(final PlayerInputCharacterController entity, final ControlEvent event) {
                if (event == ControlEvent.LEFT_RELEASED) {
                    entity.stateMachine.changeState(Open);
                }
                return true;
            }
        },
        Right {
            @Override
            public CharacterCommand getNextCommand(final CharacterActor characterActor) {
                return CharacterCommand.WalkRight;
            }

            @Override
            public boolean handleControlEvent(final PlayerInputCharacterController entity, final ControlEvent event) {
                if (event == ControlEvent.RIGHT_RELEASED) {
                    entity.stateMachine.changeState(Open);
                }
                return true;
            }
        },
        Down {
            @Override
            public CharacterCommand getNextCommand(final CharacterActor characterActor) {
                return CharacterCommand.WalkDown;
            }

            @Override
            public boolean handleControlEvent(final PlayerInputCharacterController entity, final ControlEvent event) {
                if (event == ControlEvent.DOWN_RELEASED) {
                    entity.stateMachine.changeState(Open);
                }
                return true;
            }
        };

        @Override
        public void enter(final PlayerInputCharacterController entity) {

        }

        @Override
        public void update(final PlayerInputCharacterController entity) {

        }

        @Override
        public void exit(final PlayerInputCharacterController entity) {

        }

        @Override
        public boolean onMessage(final PlayerInputCharacterController entity, final Telegram telegram) {
            return false;
        }

        public boolean handleControlEvent(final PlayerInputCharacterController entity, final ControlEvent event) {
            return false;
        }
    }
}
