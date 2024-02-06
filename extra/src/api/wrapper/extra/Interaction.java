package api.wrapper.extra;

import api.provider.ExtraProvider;
import api.provider.ExtraProviders;
import api.provider.InteractionType;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.event.InteractionEvent;

public class Interaction {

    public static boolean interact(int index, Entity entity) {
        if (entity == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(index, entity);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(index, entity);
        }

        return false;
    }

    public static boolean interact(String action, Entity entity) {
        if (entity == null || action == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(action, entity);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(action, entity);
        }

        return false;
    }

    public static boolean interact(Entity entity, String... actions) {
        if (entity == null || actions == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(entity, actions);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(entity, actions);
        }

        return false;
    }

    public static boolean invoke(int index, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getHelpers().getInvokeHelper().invoke(entity, index);
    }

    public static boolean invoke(String action, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getHelpers().getInvokeHelper().invoke(entity, action);
    }

    public static boolean invoke(Entity entity, String... actions) {
        final ExtraProvider ctx = ExtraProviders.getContext();

        for (String action : actions) {
            if (entity.hasAction(action)) {
                return ctx.getHelpers().getInvokeHelper().invoke(entity, action);
            }
        }

        return false;
    }

    public static boolean osbotDefault(int index, Entity entity) {
        return entity != null
                && entity.getActions() != null
                && entity.getActions().length > index
                && osbotDefault(entity.getActions()[index], entity);
    }

    public static boolean osbotDefault(String action, Entity entity) {
        return osbotDefault(entity, action);
    }

    public static boolean osbotDefault(Entity entity, String... actions) {
        if (entity == null) {
            return false;
        }

        final ExtraProvider ctx = ExtraProviders.getContext();
        final InteractionEvent event = build(new InteractionEvent(entity, actions));
        ctx.getCamera().toTop();

        return ctx.execute(event).hasFinished();
    }

    private static InteractionEvent build(InteractionEvent event) {
        event.setOperateCamera(false);
        event.setValidateMenuEntity(true);
        event.setWalkTo(true);

        return event;
    }

}
