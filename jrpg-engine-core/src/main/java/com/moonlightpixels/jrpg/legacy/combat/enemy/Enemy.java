package com.moonlightpixels.jrpg.legacy.combat.enemy;

import com.moonlightpixels.jrpg.legacy.animation.TextureProvider;
import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.Combatant;
import com.moonlightpixels.jrpg.legacy.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.legacy.combat.action.CombatActionType;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.enemy.ai.EnemyAI;
import com.moonlightpixels.jrpg.legacy.combat.stats.MaxHP;
import com.moonlightpixels.jrpg.legacy.combat.stats.MaxMP;
import com.moonlightpixels.jrpg.legacy.combat.stats.MissingStatException;
import com.moonlightpixels.jrpg.legacy.combat.stats.Stat;

import java.util.HashMap;
import java.util.Map;

public class Enemy implements Combatant {
    private final String name;
    private final TextureProvider image;
    private final Map<Class<? extends Stat>, Stat> stats = new HashMap<>();
    private final int level;
    private final EnemyAI enemyAI;
    private int currentHp;
    private int currentMp;

    public Enemy(final String name, final TextureProvider image, final int level,
                 final EnemyAI enemyAI, final Stat... stats) {
        this.name = name;
        this.image = image;
        this.level = level;
        this.enemyAI = enemyAI;
        for (Stat stat : stats) {
            this.stats.put(stat.getClass(), stat);
        }
        currentHp = getStatValue(MaxHP.class);
        currentMp = getStatValue(MaxMP.class);
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final void applyHpChange(final int hpChange) {
        setCurrentHp(Math.max(getCurrentHp() + hpChange, 0));
    }

    @Override
    public final int getLevel() {
        return level;
    }

    @Override
    public final ActionTypeProvider getActionTypeProvider(final Battle battle) {
        final ActionTypeProvider provider = new ActionTypeProvider();
        enemyAI.handle(this, provider, battle);

        return provider;
    }

    @Override
    public final TargetableChoiceProvider getTargetableChoiceProvider(final CombatActionType actionType,
                                                                      final Battle battle) {
        TargetableChoiceProvider provider = actionType.getTargetableChoiceProvider();
        if (!provider.isComplete()) {
            enemyAI.handle(this, provider, battle);
        }

        return provider;
    }

    @Override
    public final TargetChoiceProvider getTargetChoiceProvider(final AllowedTargets allowedTargets,
                                                              final Battle battle) {
        TargetChoiceProvider provider = new TargetChoiceProvider();
        enemyAI.handle(this, provider, battle);

        return provider;
    }

    @Override
    public final boolean isAlive() {
        return currentHp > 0;
    }

    @Override
    public final <T extends Stat> Stat getStat(final Class<T> statClass) throws MissingStatException {
        if (!stats.containsKey(statClass)) {
            throw new MissingStatException(statClass);
        }

        return stats.get(statClass);
    }

    public final TextureProvider getImage() {
        return image;
    }

    public final int getCurrentHp() {
        return currentHp;
    }

    public final int getCurrentMp() {
        return currentMp;
    }

    public final void setCurrentHp(final int currentHp) {
        this.currentHp = Math.min(currentHp, getStatValue(MaxHP.class));
    }

    public final void setCurrentMp(final int currentMp) {
        this.currentMp = Math.min(currentMp, getStatValue(MaxMP.class));
    }
}
