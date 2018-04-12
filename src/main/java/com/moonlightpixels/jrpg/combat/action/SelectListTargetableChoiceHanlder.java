package com.moonlightpixels.jrpg.combat.action;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonlightpixels.jrpg.combat.Battle;
import com.moonlightpixels.jrpg.ui.ScreenRegion;

import java.util.List;

public abstract class SelectListTargetableChoiceHanlder<T extends Targetable> extends TargetableChoiceHanlder<T> {
    private final List<T> items;
    private final int columns;
    private final ScreenRegion region;

    protected SelectListTargetableChoiceHanlder(final TargetableChoiceProvider<T> provider,
                                                final List<T> items, final int columns,
                                                final ScreenRegion region) {
        super(provider);
        this.items = items;
        this.columns = columns;
        this.region = region;
    }

    @Override
    public final void start(final Battle battle) {

    }

    protected abstract Actor renderListItem(T item);
}
