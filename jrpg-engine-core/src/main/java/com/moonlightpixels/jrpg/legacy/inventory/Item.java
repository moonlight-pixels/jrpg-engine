package com.moonlightpixels.jrpg.legacy.inventory;

import com.moonlightpixels.jrpg.legacy.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.legacy.combat.action.Targetable;

public final class Item implements InventoryContent, Targetable {
    private final String id;
    private final String name;
    private final String description;
    private final boolean useFromMenu;
    private final boolean useInBattle;
    private final AllowedTargets allowedTargets;
    private final boolean usageConsumesItem;
    private final ItemAction action;

    @SuppressWarnings("checkstyle:parameternumber")
    private Item(final String id, final String name, final String description, final boolean useFromMenu,
                 final boolean useInBattle, final AllowedTargets allowedTargets, final boolean usageConsumesItem,
                 final ItemAction action) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.useFromMenu = useFromMenu;
        this.useInBattle = useInBattle;
        this.allowedTargets = allowedTargets;
        this.usageConsumesItem = usageConsumesItem;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean canUseFromMenu() {
        return useFromMenu;
    }

    public boolean canUseInBattle() {
        return useInBattle;
    }

    @Override
    public AllowedTargets getAllowedTargets() {
        return allowedTargets;
    }

    public boolean doesUsageConsumesItem() {
        return usageConsumesItem;
    }

    public ItemAction getAction() {
        return action;
    }

    public static Builder build(final String id, final String description) {
        return new Builder(id, description);
    }

    @Override
    public void addToInventory(final Inventory inventory, final int quantity) {
        inventory.adjustQuantity(this, quantity);
    }

    public static final class Builder {
        private final String id;
        private final String name;
        private String description;
        private boolean useFromMenu = true;
        private boolean useInBattle = true;
        private AllowedTargets allowedTargets = AllowedTargets.Untargeted;
        private boolean usageConsumesItem = true;
        private ItemAction action;

        private Builder(final String id, final String name) {
            this.id = id;
            this.name = name;
        }

        public Builder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public Builder setCanUseFromMenu(final boolean useFromMenu) {
            this.useFromMenu = useFromMenu;
            return this;
        }

        public Builder setCantUseInBattle(final boolean useInBattle) {
            this.useInBattle = useInBattle;
            return this;
        }

        public Builder setAllowedTargets(final AllowedTargets allowedTargets) {
            this.allowedTargets = allowedTargets;
            return this;
        }

        public Builder setDoesUsageConsumesItem(final boolean usageConsumesItem) {
            this.usageConsumesItem = usageConsumesItem;
            return this;
        }

        public Builder setAction(final ItemAction action) {
            this.action = action;
            return this;
        }

        public Item createItem() {
            return new Item(id, name, description, useFromMenu, useInBattle, allowedTargets, usageConsumesItem, action);
        }
    }
}
