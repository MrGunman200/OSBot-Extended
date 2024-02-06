package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Maps {

    public static org.osbot.rs07.api.Map get() {
        return ExtraProviders.getContext().getMap();
    }

}
