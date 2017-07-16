package com.github.jaystgelais.jrpg.map.trigger;

public interface TileTrigger {
    TriggerAction onEnter();
    TriggerAction onExit();
    TriggerAction onInspect();
}
