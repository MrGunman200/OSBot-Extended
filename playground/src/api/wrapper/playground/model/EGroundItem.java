package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;

public class EGroundItem extends GroundItem implements EIdentifiable {

    private final GroundItem groundItem;

    public EGroundItem(GroundItem groundItem) {
        super(groundItem.accessor);
        this.groundItem = groundItem;
    }

    public EGroundItem(GroundItem groundItem, Position position) {
        super(groundItem.accessor, position);
        this.groundItem = groundItem;
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
    public boolean isVisible() {
        return groundItem.isVisible();
    }

}
