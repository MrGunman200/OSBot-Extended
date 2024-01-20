package experimental.api;

import experimental.provider.ExtraProvider;
import experimental.provider.ExtraProviders;
import experimental.provider.InteractionType;
import org.osbot.rs07.api.ui.PrayerButton;
import org.osbot.rs07.api.ui.RS2Widget;

public class Prayer {

    public static org.osbot.rs07.api.Prayer get() {
        return ExtraProviders.getContext().getPrayer();
    }

    public static RS2Widget getWidgetForPrayer(PrayerButton prayerButton) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getPrayer().getWidgetForPrayer(prayerButton);
    }

    public static boolean open() {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getPrayer().open();
    }

    public static boolean hasLevelFor(PrayerButton prayerButton) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getPrayer().hasLevelFor(prayerButton);
    }

    public static boolean isActivated(PrayerButton prayerButton) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getPrayer().isActivated(prayerButton);
    }

    public static boolean deactivateAll() {
        for (PrayerButton value : PrayerButton.values()) {
            if (isActivated(value)) {
                if (!set(value, false)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean setQuickPrayer(boolean activate) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getPrayer().setQuickPrayer(activate);
    }

    public static boolean isQuickPrayerActive() {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getPrayer().isQuickPrayerActive();
    }

    public static boolean setAll(boolean activate, PrayerButton... prayerButton) {
        for (PrayerButton button : prayerButton) {
            if (!set(button, activate)) {
                return false;
            }
        }

        return true;
    }

    public static boolean set(PrayerButton prayerButton, boolean activate) {
        final ExtraProvider ctx = ExtraProviders.getContext();

        if (ctx.getExtraBot().getInteractionType() == InteractionType.DEFAULT) {
            return osbotDefaultSet(prayerButton, activate);
        } else if (ctx.getExtraBot().getInteractionType() == InteractionType.INVOKE) {
            return invokeSet(prayerButton, activate);
        }

        return false;
    }

    public static boolean invokeSet(PrayerButton prayerButton, boolean activate) {
        return isActivated(prayerButton) == activate || Widgets.interact(getWidgetForPrayer(prayerButton));
    }

    public static boolean osbotDefaultSet(PrayerButton prayerButton, boolean activate) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getPrayer().set(prayerButton, activate);
    }

}
