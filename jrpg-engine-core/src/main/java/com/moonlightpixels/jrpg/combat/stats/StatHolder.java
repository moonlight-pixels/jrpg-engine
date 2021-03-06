package com.moonlightpixels.jrpg.combat.stats;

public interface StatHolder extends StatModifierHolder {
    /**
     * Get holders base value for a given stat.
     *
     * @param key Key identiying requested stat
     * @return stat's base value
     */
    int getBaseValue(Stat.Key key);

    /**
     * Returns the type of the holder.
     *
     * @return Type
     */
    Type getHolderType();

    enum Type { Player, Enemy }
}
