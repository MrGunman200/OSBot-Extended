package experimental.api;

import experimental.provider.ExtraProvider;
import experimental.provider.ExtraProviders;
import experimental.provider.InteractionType;
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

}
