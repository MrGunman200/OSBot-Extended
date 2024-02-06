package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

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
