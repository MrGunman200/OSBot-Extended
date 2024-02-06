package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Tabs {

    public static org.osbot.rs07.api.Tabs get() {
        return ExtraProviders.getContext().getTabs();
    }

}
