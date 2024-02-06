package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
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
            return ctx.getHelpers().getInvokeHelper().invoke(this, getActionIndex(actions));
        }

        return false;
    }

    @Override
    public boolean isVisible() {
        return player.isVisible();
    }

}
