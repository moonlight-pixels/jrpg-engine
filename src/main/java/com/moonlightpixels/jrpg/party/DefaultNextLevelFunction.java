package com.moonlightpixels.jrpg.party;

public final class DefaultNextLevelFunction implements NextLevelFunction {
    private static final int DEFAULT_BASE_XP = 100;
    private static final float DEFAULT_EXPONENT = 1.5f;

    private final int baseXP;
    private final float exponent;

    public DefaultNextLevelFunction(final int baseXP, final float exponent) {
        this.baseXP = baseXP;
        this.exponent = exponent;
    }

    public DefaultNextLevelFunction() {
        this(DEFAULT_BASE_XP, DEFAULT_EXPONENT);
    }

    @Override
    public int getNextLevel(final int currentLevel) {
        return (int) Math.round(baseXP * Math.pow(currentLevel, exponent));
    }
}
