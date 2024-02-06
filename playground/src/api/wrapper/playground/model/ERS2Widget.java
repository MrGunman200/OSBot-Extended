package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.ui.RS2Widget;

public class ERS2Widget extends RS2Widget implements EIdentifiable {

    private final RS2Widget widget;

    public ERS2Widget(RS2Widget widget, int thirdLevelId) {
        super(widget.accessor, thirdLevelId);
        this.widget = widget;
    }

    @Override
    public boolean interact(String... actions) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT || hasNoActions()) {
            return super.interact(actions);
        } else if (interactionType == InteractionType.INVOKE) {
            return ctx.getHelpers().getInvokeHelper().invoke(this, getActionIndex(actions));
        }

        return false;
    }

    @Override
    public boolean interact(boolean ignoreIsHidden, String... actions) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT || hasNoActions()) {
            return super.interact(ignoreIsHidden, actions);
        } else if (interactionType == InteractionType.INVOKE) {
            return ctx.getHelpers().getInvokeHelper().invoke(this, getActionIndex(actions));
        }

        return false;
    }

    @Override
    public String[] getActions() {
        return getInteractActions();
    }

    @Override
    public boolean isVisible() {
        return widget.isVisible();
    }

    private boolean hasNoActions() {
        final String[] actions = getInteractActions();
        return actions == null || actions.length == 0;
    }

}
