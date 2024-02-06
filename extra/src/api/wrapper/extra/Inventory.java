package api.wrapper.extra;

import api.provider.ExtraProvider;
import api.provider.ExtraProviders;
import api.provider.InteractionType;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Item;

public class Inventory {

    public static org.osbot.rs07.api.Inventory get() {
        return ExtraProviders.getContext().getInventory();
    }

    public static boolean interact(int index, int itemId) {
        final Item item = ExtraProviders.getContext().getInventory().getItem(itemId);
        return interact(index, item);
    }

    public static boolean interact(int index, String itemName) {
        final Item item = ExtraProviders.getContext().getInventory().getItem(itemName);
        return interact(index, item);
    }

    public static boolean interact(int index, Item item) {
        if (item == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(index, item);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(index, item);
        }

        return false;
    }

    public static boolean interact(String action, int itemId) {
        final Item item = ExtraProviders.getContext().getInventory().getItem(itemId);
        return interact(action, item);
    }

    public static boolean interact(String action, String itemName) {
        final Item item = ExtraProviders.getContext().getInventory().getItem(itemName);
        return interact(action, item);
    }

    public static boolean interact(String action, Item item) {
        if (item == null || action == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(action, item);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(action, item);
        }

        return false;
    }

    public static boolean interact(Item item, String... actions) {
        if (item == null || actions == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(item, actions);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(item, actions);
        }

        return false;
    }

    public static boolean invoke(int index, Item item) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getHelpers().getInvokeHelper().invoke(item, index);
    }

    public static boolean invoke(String action, Item item) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getHelpers().getInvokeHelper().invoke(item, action);
    }

    public static boolean invoke(Item item, String... actions) {
        final ExtraProvider ctx = ExtraProviders.getContext();

        for (String action : actions) {
            if (item.hasAction(actions)) {
                return ctx.getHelpers().getInvokeHelper().invoke(item, action);
            }
        }

        return false;
    }

    public static boolean osbotDefault(int index, Item item) {
        return item != null
                && item.getActions() != null
                && item.getActions().length > index
                && osbotDefault(item.getActions()[index], item);
    }

    public static boolean osbotDefault(String action, Item item) {
        return osbotDefault(item, action);
    }

    public static boolean osbotDefault(Item item, String... actions) {
        return ExtraProviders.getContext().getInventory().interact(item.getOwner().getThirdLevelId(), actions);
    }

    public static boolean useItemOnItem(String item1, String item2) {
        final ExtraProvider ctx = ExtraProviders.getContext();

        if (InteractionType.DEFAULT == ctx.getExtraBot().getInteractionType()) {
            return defaultOsbotUseItemOnItem(item1, item2);
        } else if (InteractionType.INVOKE == ctx.getExtraBot().getInteractionType()) {
            return invokeUseItemOnItem(item1, item2);
        }

        return false;
    }

    public static boolean invokeUseItemOnItem(String item1, String item2) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getHelpers().getInvokeHelper()
                .invokeOn(ctx.getInventory().getItem(item1).getOwner(), ctx.getInventory().getItem(item2).getOwner());
    }

    public static boolean defaultOsbotUseItemOnItem(String item1, String item2) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getInventory().deselectItem()
                && Inventory.interact("Use", item1)
                && Inventory.interact("Use", item2);
    }

    public static boolean useItemOnEntity(String item, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return useItemOnEntity(ctx.getInventory().getItem(item), entity);
    }

    public static boolean useItemOnEntity(Item item, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getInventory().deselectItem()
                && Inventory.interact("Use", item)
                && Interaction.interact("Use", entity);
    }

}
