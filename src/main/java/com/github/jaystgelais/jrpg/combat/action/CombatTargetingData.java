package com.github.jaystgelais.jrpg.combat.action;

import java.util.Arrays;
import java.util.List;

public final class CombatTargetingData {
    public static final CombatTargetingData STANDARD_SINGLE_ATTACK = new CombatTargetingData(
            TargetGroup.OPPONENTS, false, TargetGroup.OPPONENTS
    );
    public static final CombatTargetingData STANDARD_SINGLE_BUFF = new CombatTargetingData(
            TargetGroup.ALLIES, false, TargetGroup.ALLIES
    );
    public static final CombatTargetingData STANDARD_MULTI_ATTACK = new CombatTargetingData(
            TargetGroup.OPPONENTS, true, TargetGroup.OPPONENTS
    );
    public static final CombatTargetingData STANDARD_MULTI_BUFF = new CombatTargetingData(
            TargetGroup.ALLIES, true, TargetGroup.ALLIES
    );
    public static final CombatTargetingData TWOWAY_SINGLE_ATTACK = new CombatTargetingData(
            TargetGroup.OPPONENTS, false, TargetGroup.OPPONENTS, TargetGroup.ALLIES
    );
    public static final CombatTargetingData TWOWAY_SINGLE_BUFF = new CombatTargetingData(
            TargetGroup.ALLIES, false, TargetGroup.OPPONENTS, TargetGroup.ALLIES
    );
    public static final CombatTargetingData TWOWAY_MULTI_ATTACK = new CombatTargetingData(
            TargetGroup.OPPONENTS, true, TargetGroup.OPPONENTS, TargetGroup.ALLIES
    );
    public static final CombatTargetingData TWOWAY_MULTI_BUFF = new CombatTargetingData(
            TargetGroup.ALLIES, true, TargetGroup.OPPONENTS, TargetGroup.ALLIES
    );

    private final TargetGroup defaultTargetGroup;
    private final List<TargetGroup> allowedTargetGroups;
    private final boolean isMultiTarget;

    public CombatTargetingData(final TargetGroup defaultTargetGroup, final boolean isMultiTarget,
                               final TargetGroup ...allowedTargetGroups) {
        this.defaultTargetGroup = defaultTargetGroup;
        this.isMultiTarget = isMultiTarget;
        this.allowedTargetGroups = Arrays.asList(allowedTargetGroups);
    }

    public TargetGroup getDefaultTargetGroup() {
        return defaultTargetGroup;
    }

    public List<TargetGroup> getAllowedTargetGroups() {
        return allowedTargetGroups;
    }

    public boolean isMultiTarget() {
        return isMultiTarget;
    }
}
