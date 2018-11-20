package com.moonlightpixels.jrpg.story.event.internal;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.moonlightpixels.jrpg.story.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EventHandler<E> implements Telegraph {
    private final Task task;

    protected abstract int getMessageCode();
    protected abstract boolean doesComplete(E event);

    @Override
    @SuppressWarnings("unchecked")
    public final boolean handleMessage(final Telegram msg) {
        task.setComplete(doesComplete((E) msg.extraInfo));
        return true;
    }

    final void register(final MessageDispatcher messageDispatcher) {
        messageDispatcher.addListener(this, getMessageCode());
    }
}
