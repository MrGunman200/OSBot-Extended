package experimental.api;

import experimental.provider.ExtraProviders;

public class Quests {

    public static org.osbot.rs07.api.Quests get() {
        return ExtraProviders.getContext().getQuests();
    }

}
