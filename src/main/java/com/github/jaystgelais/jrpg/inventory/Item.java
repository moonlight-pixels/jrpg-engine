package com.github.jaystgelais.jrpg.inventory;

public final class Item {
    private final String id;
    private final String name;
    private final String description;
    private final boolean useFromMenu;
    private final boolean useInBattle;
    private final boolean requiresTarget;
    private final boolean usageConsumesItem;
    private final ItemAction action;

    @SuppressWarnings("checkstyle:parameternumber")
    private Item(final String id, final String name, final String description, final boolean useFromMenu,
                 final boolean useInBattle, final boolean requiresTarget, final boolean usageConsumesItem,
                 final ItemAction action) {
        this.id = id;
        this.name = name;
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

    public static final class Builder {
        private final String id;
        private final String name;
        private String description;
        private boolean useFromMenu = true;
        private boolean useInBattle = true;
        private boolean requiresTarget = false;
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

        public Builder seCantUseInBattle(final boolean useInBattle) {
            this.useInBattle = useInBattle;
            return this;
        }

        public Builder setRequiresTarget(final boolean requiresTarget) {
            this.requiresTarget = requiresTarget;
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
            return new Item(id, name, description, useFromMenu, useInBattle, requiresTarget, usageConsumesItem, action);
        }
    }
}
