package com.github.jaystgelais.jrpg.map.actor;

public interface ActionProvider {
    Action getNextAction();
    boolean hasNextAction();
}
