package com.moonlightpixels.jrpg.save.internal;

public class SavedStateLoadExcpetion extends Exception {
    private static final long serialVersionUID = -3592040251875936404L;

    public SavedStateLoadExcpetion(final String message, final Throwable cause) {
        super(message, cause);
    }
}
