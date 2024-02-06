package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Worlds {

    public static org.osbot.rs07.api.Worlds get() {
        return ExtraProviders.getContext().getWorlds();
    }

}
