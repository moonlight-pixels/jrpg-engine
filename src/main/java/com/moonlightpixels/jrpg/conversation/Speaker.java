package com.moonlightpixels.jrpg.conversation;

public final class Speaker {
    private final String name;

    public Speaker(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
