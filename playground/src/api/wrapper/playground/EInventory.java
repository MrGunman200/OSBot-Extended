package api.wrapper.playground;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import api.wrapper.playground.util.EItemContainer;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.script.MethodProvider;

import java.util.Arrays;

public class EInventory extends Inventory implements EItemContainer {

    public EInventory(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public Item[] getItems() {
        return getItems(super.getItems());
    }

    @Override
    public boolean interact(int slot, String... actions) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.interact(slot, actions);
        } else if (interactionType == InteractionType.INVOKE) {
            final Item slotItem = getItemInSlot(slot);

            if (slotItem == null) {
                return false;
            }

            if ((actions == null || actions.length == 0) && ctx.getInventory().isItemSelected()) {
                final Item item = ctx.getInventory().getItem(ctx.getInventory().getSelectedItemIndex());
                return item != null && ctx.getHelpers().getInvokeHelper().invokeOn(item.getOwner(), slotItem.getOwner());
            }

            if (actions != null
                    && actions.length != 0
                    && Arrays.stream(actions).anyMatch(i -> i.equalsIgnoreCase("use")
                    || i.equalsIgnoreCase(""))) {
                if (ctx.getInventory().isItemSelected()) {
                    final Item item = ctx.getInventory().getItemInSlot(ctx.getInventory().getSelectedItemIndex());
                    return item != null && ctx.getHelpers().getInvokeHelper().invokeOn(item.getOwner(), slotItem.getOwner());
                }

                return ctx.getHelpers().getInvokeHelper().invokeUse(slotItem.getOwner());
            }

            return slotItem.interact(actions);
        }

        return false;
    }

}
