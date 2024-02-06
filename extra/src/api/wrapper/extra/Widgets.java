package api.wrapper.extra;

import api.provider.ExtraProvider;
import api.provider.ExtraProviders;
import api.provider.InteractionType;
import org.osbot.rs07.api.ui.RS2Widget;

import java.util.function.Predicate;

public class Widgets {

    public static org.osbot.rs07.api.Widgets get() {
        return ExtraProviders.getContext().getWidgets();
    }

    public static boolean isVisible(RS2Widget widget) {
        return widget != null && widget.isVisible();
    }

    public static boolean interact(RS2Widget widget) {
        if (widget == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(widget);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(widget);
        }

        return false;
    }

    public static boolean interact(int index, RS2Widget widget) {
        if (widget == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(index, widget);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(index, widget);
        }

        return false;
    }

    public static boolean interact(String action, RS2Widget widget) {
        if (widget == null || action == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(action, widget);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(action, widget);
        }

        return false;
    }

    public static boolean invoke(RS2Widget widget) {
        return invoke(0, widget);
    }

    public static boolean invoke(int index, RS2Widget widget) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getHelpers().getInvokeHelper().invoke(widget, index);
    }

    public static boolean invoke(String action, RS2Widget widget) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getHelpers().getInvokeHelper().invoke(widget, action);
    }

    public static boolean osbotDefault(RS2Widget widget) {
        return osbotDefault(0, widget);
    }

    public static boolean osbotDefault(int index, RS2Widget widget) {
        return widget != null
                && widget.getInteractActions() != null
                && widget.getInteractActions().length > index
                && widget.interact(widget.getInteractActions()[index]);
    }

    public static boolean osbotDefault(String action, RS2Widget widget) {
        return widget != null && widget.interact(action);
    }

    public static RS2Widget get(Predicate<RS2Widget> predicate) {
        return get().getAll().stream()
                .filter(i -> i != null && predicate.test(i))
                .findFirst()
                .orElse(null);
    }

}
