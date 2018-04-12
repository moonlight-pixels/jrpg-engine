package com.moonlightpixels.jrpg.util;

public final class TimeUtil {
    public static final int MS_IN_SECOND = 1000;

    private TimeUtil() { }

    public static float convertMsToFloatSeconds(final long timeMs) {
        return (float) timeMs / MS_IN_SECOND;
    }

    public static long convertFloatSecondsToLongMs(final float seconds) {
        return (long) (seconds * MS_IN_SECOND);
    }

    public static float calculatePercentComplete(final long currentTimeMs, final long totalTimeMs) {
        return (float) currentTimeMs / (float) totalTimeMs;
    }
}
