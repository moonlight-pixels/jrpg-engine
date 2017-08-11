package com.github.jaystgelais.jrpg.inventory;

public class Item {
    private final String id;
    private final String description;
    private final boolean useFromMenu;
    private final boolean useInBattle;
    private final boolean requiresTarget;
    private final boolean usageConsumesItem;
    private final ItemAction action;

    private Item(String id, String description, boolean useFromMenu, boolean useInBattle, boolean requiresTarget, boolean usageConsumesItem, ItemAction action) {
        this.id = id;
        this.description = description;
        this.useFromMenu = useFromMenu;
        this.useInBattle = useInBattle;
        this.requiresTarget = requiresTarget;
        this.usageConsumesItem = usageConsumesItem;
        this.action = action;
    }

    public String getId() {
        return id;
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

    public boolean requiresTarget() {
        return requiresTarget;
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

    public static class Builder {
        private final String id;
        private final String description;
        private boolean useFromMenu = true;
        private boolean useInBattle = true;
        private boolean requiresTarget = false;
        private boolean usageConsumesItem = true;
        private ItemAction action;

        private Builder(final String id, final String description) {
            this.id = id;
            this.description = description;
        }

        public Builder setCanUseFromMenu(boolean useFromMenu) {
            this.useFromMenu = useFromMenu;
            return this;
        }

        public Builder seCantUseInBattle(boolean useInBattle) {
            this.useInBattle = useInBattle;
            return this;
        }

        public Builder setRequiresTarget(boolean requiresTarget) {
            this.requiresTarget = requiresTarget;
            return this;
        }

        public Builder setDoesUsageConsumesItem(boolean usageConsumesItem) {
            this.usageConsumesItem = usageConsumesItem;
            return this;
        }

        public Builder setAction(ItemAction action) {
            this.action = action;
            return this;
        }

        public Item createItem() {
            return new Item(id, description, useFromMenu, useInBattle, requiresTarget, usageConsumesItem, action);
        }
    }
}
