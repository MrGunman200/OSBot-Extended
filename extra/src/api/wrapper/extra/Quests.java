package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Quests {

    public static org.osbot.rs07.api.Quests get() {
        return ExtraProviders.getContext().getQuests();
    }

}
