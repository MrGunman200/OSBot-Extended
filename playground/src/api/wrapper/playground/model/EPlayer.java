package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;

public class EPlayer extends Player implements EIdentifiable {

    private final Player player;

    public EPlayer(Player player) {
        super(player.accessor);
        this.player = player;
    }

    @Override
    public boolean interact(String... actions) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.interact(actions);
        } else if (interactionType == InteractionType.INVOKE) {
            if (ctx.getInventory().isItemSelected()) {
                final Item item = ctx.getInventory().getItemInSlot(ctx.getInventory().getSelectedItemIndex());
                return item != null && ctx.getHelpers().getInvokeHelper().invokeOn(item.getOwner(), this);
            }

            return ctx.getHelpers().getInvokeHelper().invoke(this, getActionIndex(actions));
        }

        return false;
    }

    @Override
    public boolean isVisible() {
        return player.isVisible();
    }

}
