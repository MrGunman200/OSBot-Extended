package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import java.util.Arrays;

public class EItem extends Item implements EIdentifiable {

    public EItem(MethodProvider methods, int rootId, int id, int amount) {
        super(methods, rootId, id, amount);
    }

    public EItem(RS2Widget owner, int id, int amount) {
        super(owner, id, amount);
    }

    @Override
    public boolean interact(String... actions) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.interact(actions);
        } else if (interactionType == InteractionType.INVOKE) {
            if (actions != null
                    && actions.length != 0
                    && Arrays.stream(actions).anyMatch(i -> i.equalsIgnoreCase("use"))) {
                if (ctx.getInventory().isItemSelected()) {
                    final Item item = ctx.getInventory().getItem(ctx.getInventory().getSelectedItemIndex());
                    return item != null && ctx.getHelpers().getInvokeHelper().invokeOn(item.getOwner(), this.getOwner());
                }

                return ctx.getHelpers().getInvokeHelper().invokeUse(this.getOwner());
            }

            return ctx.getHelpers().getInvokeHelper().invoke(this, getActionIndex(actions));
        }

        return false;
    }

    @Override
    public String[] getActions() {
        return getOwner().getInteractActions();
    }

    @Override
    public MethodProvider getMethods() {
        return getOwner().getMethods();
    }

}
