package api.wrapper.playground;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import api.wrapper.playground.util.EItemContainer;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.script.MethodProvider;

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
            final Item item = getItemInSlot(slot);
            return item != null && item.interact(actions);
        }

        return false;
    }

}
