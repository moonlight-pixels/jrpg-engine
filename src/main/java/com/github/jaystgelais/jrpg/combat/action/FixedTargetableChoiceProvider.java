package com.github.jaystgelais.jrpg.combat.action;

public class FixedTargetableChoiceProvider extends TargetableChoiceProvider<Targetable> {
    public FixedTargetableChoiceProvider(final AllowedTargets allowedTargets) {
        super(Targetable.class);
        setChoice(new Targetable() {
            @Override
            public AllowedTargets getAllowedTargets() {
                return allowedTargets;
            }
        });
    }
}
