package com.github.jaystgelais.jrpg.map.trigger;

public final class TriggerContext {
    private boolean complete = false;

    public boolean isComplete() {
        return complete;
    }

    public void done() {
        complete = true;
    }
}
