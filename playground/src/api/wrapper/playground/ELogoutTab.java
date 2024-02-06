package api.wrapper.playground;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.Client;
import org.osbot.rs07.api.LogoutTab;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep2;

import java.util.function.Supplier;

public class ELogoutTab extends LogoutTab {

    private final Supplier<RS2Widget> LOGOUT_BUTTON_1 = ()-> getWidgets().get(182, 8);
    private final Supplier<RS2Widget> LOGOUT_BUTTON_2 = ()-> getWidgets().get(69, 25);

    public ELogoutTab(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public boolean logOut() {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.logOut();
        } else if (interactionType == InteractionType.INVOKE) {
            RS2Widget widget = LOGOUT_BUTTON_1.get();

            if (widget == null) {
                widget = LOGOUT_BUTTON_2.get();
            }

            return widget != null && widget.interact()
                    && ConditionalSleep2.sleep(3000, ()-> client.getGameState() == Client.GameState.LOGGED_OUT);
        }

        return false;
    }

    private ExtraProvider getExtraProvider() {
        return ((EObjects) objects).extraProvider;
    }

}
