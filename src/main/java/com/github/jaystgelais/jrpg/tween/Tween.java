package com.github.jaystgelais.jrpg.tween;

import com.github.jaystgelais.jrpg.state.Updatable;

public interface Tween<T> extends Updatable {
    T getValue();
    boolean isComplete();
}
