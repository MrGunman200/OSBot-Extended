package api.wrapper.playground;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.Prayer;
import org.osbot.rs07.api.ui.PrayerButton;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

public class EPrayer extends Prayer {

    public EPrayer(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public boolean set(PrayerButton prayer, boolean activate) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.set(prayer, activate);
        } else if (interactionType == InteractionType.INVOKE) {
            final RS2Widget widget = getPrayerWidget(prayer);
            return isActivated(prayer) == activate
                    || (widget != null && ctx.getHelpers().getInvokeHelper().invoke(widget, 0));
        }

        return false;
    }

    @Override
    public boolean setQuickPrayer(boolean activate) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.setQuickPrayer(activate);
        } else if (interactionType == InteractionType.INVOKE) {
            final RS2Widget widget = getQuickPrayerWidget();
            return isQuickPrayerActive() == activate
                    || (widget != null && ctx.getHelpers().getInvokeHelper().invoke(widget, 0));
        }

        return false;
    }

    private RS2Widget getPrayerWidget(PrayerButton prayer) {
        RS2Widget widget = getWidgets().getAll().stream()
                .filter(w -> w.getSpriteIndex1() == prayer.getSpriteId())
                .findFirst()
                .orElse(null);

        if (widget != null) {
            widget = getWidgets().get(widget.getRootId(), widget.getSecondLevelId());
        }

        return widget;
    }

    private RS2Widget getQuickPrayerWidget() {
        return getWidgets().getAll().stream()
                .filter(w -> w.getSpellName() != null && w.getSpellName().equals("Quick-prayers"))
                .findFirst()
                .orElse(null);
    }

    private ExtraProvider getExtraProvider() {
        return ((EObjects) objects).extraProvider;
    }

}
