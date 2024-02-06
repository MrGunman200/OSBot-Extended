package api.wrapper.playground;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.Tabs;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;

public class ETabs extends Tabs {

    public ETabs(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public boolean open(Tab tab) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.open(tab);
        } else if (interactionType == InteractionType.INVOKE) {
            final RS2Widget widget = getWidgets().getWidgetContainingAction(getActionForTab(tab));
            return !isOpen(tab) && widget != null && ctx.getHelpers().getInvokeHelper().invoke(widget, 0);
        }

        return false;
    }

    private String getActionForTab(Tab tab) {
        switch (tab) {
            case NONE: return "";
            case SETTINGS: return "Settings";
            case INVENTORY: return "Inventory";
            case MAGIC: return "Magic";
            case MUSIC: return "Music Player";
            case QUEST: return "Quest List";
            case ATTACK: return "Combat Options";
            case EMOTES: return "Emotes";
            case LOGOUT: return "Logout";
            case PRAYER: return "Prayer";
            case SKILLS: return "Skills";
            case FRIENDS: return "Friends List";
            case CLANCHAT: return "Chat-channel";
            case EQUIPMENT: return "Worn Equipment";
            case ACCOUNT_MANAGEMENT: return "Account Management";
        }

        return "";
    }

    private ExtraProvider getExtraProvider() {
        return ((EObjects) objects).extraProvider;
    }

}
