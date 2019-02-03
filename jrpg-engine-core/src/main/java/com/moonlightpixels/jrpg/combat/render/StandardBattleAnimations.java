package com.moonlightpixels.jrpg.combat.render;

public enum StandardBattleAnimations implements CharacterBattleAnimationSet.CharacterBattleAnimation {
    /**
     * Animation for default state.
     */
    Idle(true),
    /**
     * Animation used when player walks forward to perform an action.
     */
    Walk(true),
    /**
     * Plays when player is dead/KO'd.
     */
    Dead(true),
    /**
     * Plays when player takes damage.
     */
    Hit(true),
    /**
     * A victory dance.
     */
    Victory(true);

    private final boolean required;

    StandardBattleAnimations(final boolean required) {
        this.required = required;
    }

    @Override
    public boolean isRequired() {
        return required;
    }
}
